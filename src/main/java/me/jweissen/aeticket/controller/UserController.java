package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.aspect.AdminOnly;
import me.jweissen.aeticket.dto.request.LoginRequestDto;
import me.jweissen.aeticket.dto.request.SignupRequestDto;
import me.jweissen.aeticket.dto.request.UserUpdateRequestDto;
import me.jweissen.aeticket.dto.response.TokenResponseDto;
import me.jweissen.aeticket.dto.response.UserResponseDto;
import me.jweissen.aeticket.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signUp(@RequestBody SignupRequestDto user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody LoginRequestDto user) {
        return userService.login(user)
            .map(tokenDto -> new ResponseEntity<>(tokenDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @PutMapping("/update")
    @AdminOnly
    public ResponseEntity<Void> update(@RequestBody UserUpdateRequestDto user) {
        if (!userService.update(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping("/delete/{id}")
    @AdminOnly
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    @AdminOnly
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/load/{id}")
    @AdminOnly
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return userService.getById(id)
            .map(userResponseDto -> new ResponseEntity<>(userResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
