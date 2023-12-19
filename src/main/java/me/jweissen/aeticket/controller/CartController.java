package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.dto.request.CartAddRequestDto;
import me.jweissen.aeticket.dto.response.CartEntryResponseDto;
import me.jweissen.aeticket.dto.response.CheckoutResponseDto;
import me.jweissen.aeticket.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping("/add")
    public ResponseEntity<Void> addEntry(CartAddRequestDto dto) {
        // TODO
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/list")
    public ResponseEntity<List<CartEntryResponseDto>> getCartEntries() {
        // TODO
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @GetMapping("/checkout")
    public ResponseEntity<CheckoutResponseDto> checkout() {
        // TODO
        return ResponseEntity.badRequest().build();
    }
}
