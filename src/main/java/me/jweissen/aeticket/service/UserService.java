package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.response.UserResponseDto;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(
            user.getId(),
            user.getFirstname(),
            user.getLastname(),
            user.getEmail()
        );
    }

    public static List<UserResponseDto> toDtos(List<User> users) {
        return users.stream().map(UserService::toDto).toList();
    }

    public List<UserResponseDto> getAll() {
        return UserService.toDtos(userRepository.findAll());
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
