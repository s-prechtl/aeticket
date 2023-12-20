package me.jweissen.aeticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.jweissen.aeticket.aspect.UserOnly;
import me.jweissen.aeticket.dto.request.CartAddRequestDto;
import me.jweissen.aeticket.dto.response.CartEventResponseDto;
import me.jweissen.aeticket.dto.response.CheckoutResponseDto;
import me.jweissen.aeticket.service.AuthService;
import me.jweissen.aeticket.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
    private final AuthService authService;

    public CartController(CartService cartService, AuthService authService) {
        this.cartService = cartService;
        this.authService = authService;
    }


    @Operation(
            summary = "Add tickets to your cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "You either provided non-existent category ids or wanted more tickets than available, or your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            )
    })
    @PostMapping("/add")
    @UserOnly
    public ResponseEntity<Void> addEntry(@RequestBody CartAddRequestDto dto) {
        if (!cartService.add(dto, authService.getCurrentUser().getCurrentCart())) {
            // user gave invalid category id(s) or wanted more tickets than available
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "View your cart"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CartEventResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            )
    })
    @GetMapping("/list")
    @UserOnly
    public ResponseEntity<List<CartEventResponseDto>> getCartEntries() {
        return new ResponseEntity<>(cartService.toDto(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }

    @Operation(
            summary = "Checkout your cart and view the price"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CheckoutResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            )
    })
    @GetMapping("/checkout")
    @UserOnly
    public ResponseEntity<CheckoutResponseDto> checkout() {
        return new ResponseEntity<>(cartService.checkout(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }
}
