package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.LoginRequestDto;
import me.jweissen.aeticket.dto.request.SignupRequestDto;
import me.jweissen.aeticket.dto.request.UserUpdateRequestDto;
import me.jweissen.aeticket.dto.response.TokenResponseDto;
import me.jweissen.aeticket.dto.response.UserResponseDto;
import me.jweissen.aeticket.model.Cart;
import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.CartRepository;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthService authService;
    private final CartRepository cartRepository;

    public UserService(UserRepository userRepository, JwtService jwtService, AuthService authService,
                       CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
        this.authService = authService;
        this.cartRepository = cartRepository;
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

    public String generateToken(User user) {
        user.setToken(jwtService.generateToken(user.getId()));
        authService.extendToken(user);
        userRepository.save(user);
        return user.getToken();
    }

    public TokenResponseDto create(SignupRequestDto dto) {
        var user = UserService.fromDto(dto);
        user = userRepository.save(user);
        Cart newCart = new Cart();
        newCart.setUser(user);
        cartRepository.save(newCart);
        user.setCurrentCart(newCart);
        userRepository.save(user);
        return new TokenResponseDto(generateToken(user));
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<TokenResponseDto> login(LoginRequestDto dto) {
        var user = userRepository.findByEmail(dto.email());
        if (!user.getPassword().equals(dto.password())) {
            return Optional.empty();
        }
        return Optional.of(new TokenResponseDto(generateToken(user)));
    }

    public boolean update(UserUpdateRequestDto dto) {
        var userOptional = userRepository.findById(dto.id());
        if (userOptional.isEmpty()) {
            return false;
        }
        var user = userOptional.get();
        user.setEmail(dto.email());
        user.setFirstname(dto.firstname());
        user.setLastname(dto.lastname());
        userRepository.save(user);
        return true;
    }

    public Optional<UserResponseDto> getById(Long id) {
        return userRepository.findById(id).map(UserService::toDto);
    }
}
