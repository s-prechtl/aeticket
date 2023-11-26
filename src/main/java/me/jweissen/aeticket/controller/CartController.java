package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.dto.response.CartEntryResponseDto;
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

    /*@GetMapping("/list")
    public ResponseEntity<List<CartEntryResponseDto>> getCartEntries() {
        return cartService.get
    }*/

    @PostMapping("/add")
    public ResponseEntity<Void> addEntry() {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
