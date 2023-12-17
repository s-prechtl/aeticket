package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.response.LoginResponseDto;
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

    public Optional<LoginResponseDto> login(String username, String password) {
        User user = userRepository.findByEmail(username);
        // user not found or passwords don't match
        if (user == null || !user.getPassword().equals(password)) {
            return Optional.empty();
        }
        String token = jwtService.generateToken(user.getId());
        user.setToken(token);
        userRepository.save(user);
        return Optional.of(new LoginResponseDto(username));
    }

    public Optional<Role> getRole(String token) {
        User user = userRepository.findByToken(token);
        if (user == null) {
            return Optional.empty();
        }
        return Optional.of(user.getRole());
    }


}
