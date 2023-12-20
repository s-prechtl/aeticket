package me.jweissen.aeticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Sign up as a new user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            )
    })
    @PostMapping("/signup")
    public ResponseEntity<TokenResponseDto> signUp(@RequestBody SignupRequestDto user) {
        return new ResponseEntity<>(userService.create(user), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Sign in as an existing user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = TokenResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            )
    })
    @PostMapping("/signin")
    public ResponseEntity<TokenResponseDto> signIn(@RequestBody LoginRequestDto user) {
        return userService.login(user)
            .map(tokenDto -> new ResponseEntity<>(tokenDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(
            summary = "Update a user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @PutMapping("/update")
    @AdminOnly
    public ResponseEntity<Void> update(@RequestBody UserUpdateRequestDto user) {
        if (!userService.update(user)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "Delete a user"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @DeleteMapping("/delete/{id}")
    @AdminOnly
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation(
            summary = "List all users"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @GetMapping("/list")
    @AdminOnly
    public ResponseEntity<List<UserResponseDto>> getAll() {
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Load a user by id"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = UserResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No user found with the given id"
            ),
    })
    @GetMapping("/load/{id}")
    @AdminOnly
    public ResponseEntity<UserResponseDto> getById(@PathVariable Long id) {
        return userService.getById(id)
            .map(userResponseDto -> new ResponseEntity<>(userResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
}
