package me.jweissen.aeticket.service;

import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthService authService;

    @Test
    void authenticate() {
        String token = jwtService.generateToken(1L);
        User user = User.builder()
                .id(1L)
                .firstname("jonas")
                .lastname("weissensteiner")
                .email("my@mail.com")
                .password("geheim")
                .token(token)
                .tokenValidUntil(new Date(System.currentTimeMillis() + 1000L * 60 * 60))
                .role(Role.ADMIN).build();
        Optional<User> userOptional = Optional.of(user);
        Mockito.when(userRepository.findById(1L)).thenReturn(userOptional);

        Assertions.assertEquals(user, authService.authenticate(token).get());
    }
}
