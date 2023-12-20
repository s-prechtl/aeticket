package me.jweissen.aeticket.controller;

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

    @PostMapping("/add")
    @UserOnly
    public ResponseEntity<Void> addEntry(@RequestBody CartAddRequestDto dto) {
        System.out.println(authService.getCurrentUser());
        if (!cartService.add(dto, authService.getCurrentUser().getCurrentCart())) {
            // user gave invalid category id(s) or wanted more tickets than available
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/list")
    @UserOnly
    public ResponseEntity<List<CartEventResponseDto>> getCartEntries() {
        return new ResponseEntity<>(cartService.toDto(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }

    @GetMapping("/checkout")
    @UserOnly
    public ResponseEntity<CheckoutResponseDto> checkout() {
        return new ResponseEntity<>(cartService.checkout(authService.getCurrentUser().getCurrentCart()), HttpStatus.OK);
    }
}
