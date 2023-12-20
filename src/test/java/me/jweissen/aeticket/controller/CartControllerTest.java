package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.model.Cart;
import me.jweissen.aeticket.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;

    /*
    @Test
    void addEntry() {
        CartAddRequestDto dto = new CartAddRequestDto(
            999L,
            Arrays.asList(
                new CartEntryRequestDto(1L, 10),
                new CartEntryRequestDto(2L, 10)
            )
        );
        Mockito.when(cartService.add()).thenReturn(true);
    }
     */

    @Test
    void getCartEntries() {

    }

    @Test
    void checkout() {
        Cart cart = new Cart();
        Mockito.when(cartService.checkout(cart));
    }
}
