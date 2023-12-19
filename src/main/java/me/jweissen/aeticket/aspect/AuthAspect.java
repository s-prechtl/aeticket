package me.jweissen.aeticket.aspect;

import jakarta.servlet.http.HttpServletRequest;
import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.service.AuthService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
public class AuthAspect {
    private final HttpServletRequest request;
    private final AuthService authService;

    public AuthAspect(HttpServletRequest request, AuthService authService) {
        this.request = request;
        this.authService = authService;
    }

    @Pointcut("@annotation(UserOnly)")
    public void userOnly() { }

    @Pointcut("@annotation(AdminOnly)")
    public void adminOnly() { }

    public Optional<User> authenticate() {
        String tokenPrefix = "Bearer ";
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith(tokenPrefix)) {
            return Optional.empty();
        }
        String token = authHeader.substring(tokenPrefix.length());
        return authService.authenticate(token);
    }

    @Around("userOnly()")
    public Object checkUser(ProceedingJoinPoint pjp) throws Throwable {
        Optional<User> user = authenticate();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return pjp.proceed();
    }

    @Around("adminOnly()")
    public Object checkAdmin(ProceedingJoinPoint pjp) throws Throwable {
        Optional<User> user = authenticate();
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else if (user.get().getRole() != Role.ADMIN) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        return pjp.proceed();
    }
}
