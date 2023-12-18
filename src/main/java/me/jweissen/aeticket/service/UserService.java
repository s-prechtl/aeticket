package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.SignupRequestDto;
import me.jweissen.aeticket.dto.response.SignupResponseDto;
import me.jweissen.aeticket.dto.response.UserResponseDto;
import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, JwtService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail()
        );
    }

    public static User fromDto(SignupRequestDto dto) {
        return new User(
            dto.firstname(),
            dto.lastname(),
            dto.email(),
            dto.password(),
            Role.USER
        );
    }

    public static List<UserResponseDto> toDtos(List<User> users) {
        return users.stream().map(UserService::toDto).toList();
    }

    public List<UserResponseDto> getAll() {
        return UserService.toDtos(userRepository.findAll());
    }

    public SignupResponseDto create(SignupRequestDto dto) {
        var user = UserService.fromDto(dto);
        user = userRepository.save(user);
        user.setToken(jwtService.generateToken(user.getId()));
        userRepository.save(user);
        return new SignupResponseDto(user.getToken());
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
