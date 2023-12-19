package me.jweissen.aeticket.service;

import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public AuthService(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    public Optional<Role> getRole(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user.getRole());
    }


}
