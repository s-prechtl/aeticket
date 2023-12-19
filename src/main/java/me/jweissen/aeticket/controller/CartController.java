package me.jweissen.aeticket.controller;

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

    @PostMapping("/add")
    public ResponseEntity<Void> addEntry(@RequestBody CartAddRequestDto dto) {
        if (!cartService.add(dto, authService.getCurrentUser().getCurrentCart())) {
            // user gave invalid category id(s)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartEventResponseDto>> getCartEntries() {
        return new ResponseEntity<>(cartService.toDto(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkout() {
        return new ResponseEntity<>(cartService.checkout(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }
}
