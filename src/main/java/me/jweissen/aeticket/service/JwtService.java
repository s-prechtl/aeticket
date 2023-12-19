package me.jweissen.aeticket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    private final JWTVerifier jwtVerifier;
    private final Algorithm algorithm;
    private final String userIdClaimKey = "userId";

    public JwtService(@Value("${token.secret}") String secret) {
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).build();
    }

    public String generateToken(Long userId) {
        return JWT.create()
            .withSubject("aeticket user token")
            .withClaim(userIdClaimKey, userId)
            .sign(algorithm);
    }

    public Optional<Long> getUserId(String token) {
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(token);
        } catch (JWTVerificationException e) {
            // token not valid
            return Optional.empty();
        }
        Claim userIdClaim = decodedJWT.getClaim(userIdClaimKey);
        if (userIdClaim.isNull()) {
            // userId claim not present
            return Optional.empty();
        }
        Long userId = userIdClaim.asLong();
        if (userId == null) {
            // userId claim not a long
            return Optional.empty();
        }
        return Optional.of(userId);
    }
}
