package me.jweissen.aeticket.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {
    @Value("${token.secret}")
    private String secret;
    @Value("${token.validForHours}")
    private Long tokenValidForHours;

    private final JWTVerifier jwtVerifier;
    private final Algorithm algorithm;
    private final String userIdClaimKey = "userId";
    private final Long tokenValidForMillis;

    public JwtService() {
        tokenValidForMillis = 1000L * 3600 * tokenValidForHours;
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).build();
    }

    public String generateToken(Long userId) {
        return JWT.create()
            .withSubject("aeticket user token")
            .withClaim(userIdClaimKey , userId)
            .withIssuedAt(new Date())
            .withExpiresAt(new Date(System.currentTimeMillis() + tokenValidForMillis))
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
        // token expired
        if (decodedJWT.getExpiresAt().before(new Date())) {
            return Optional.empty();
        }
        Claim claim = decodedJWT.getClaim(userIdClaimKey);
        return Optional.of(claim.asLong());
    }
}
