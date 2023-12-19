package me.jweissen.aeticket.service;

import lombok.Getter;
import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final Long tokenValidForMillis;
    @Getter
    private User currentUser;

    public AuthService(
            JwtService jwtService,
            UserRepository userRepository,
            @Value("${token.validForHours}") Long tokenValidForHours) {
        this.tokenValidForMillis = 1000L * 3600 * tokenValidForHours;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public void extendToken(User user) {
        user.setTokenValidUntil(new Date(System.currentTimeMillis() + tokenValidForMillis));
        userRepository.save(user);
    }

    public Optional<User> authenticate(String token) {
        Optional<Long> userIdOptional = jwtService.getUserId(token);
        // token not valid or userId
        if (userIdOptional.isEmpty()) {
            return Optional.empty();
        }
        Optional<User> userOptional = userRepository.findById(userIdOptional.get());
        if (userOptional.isEmpty()) {
            // user with id not found
            return Optional.empty();
        }
        User user = userOptional.get();
        if (user.getTokenValidUntil().before(new Date())) {
            // token expired
            return Optional.empty();
        }
        // success
        extendToken(user);
        currentUser = user;
        return Optional.of(user);
    }
}
