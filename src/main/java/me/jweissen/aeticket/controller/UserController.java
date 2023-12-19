package me.jweissen.aeticket.controller;

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
import java.util.Optional;

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
    public ResponseEntity<Void> update(@RequestBody UserUpdateRequestDto user) {
        // TODO admin only
        if (!userService.update(user)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        // TODO admin only
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<UserResponseDto>> getAll() {
        // TODO admin only
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/load/{id}")
    public ResponseEntity<UserResponseDto> getById(@PathVariable Integer id) {
        // TODO admin only
        return userService.getById(id)
            .map(userResponseDto -> new ResponseEntity<>(userResponseDto, HttpStatus.OK))
            .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
