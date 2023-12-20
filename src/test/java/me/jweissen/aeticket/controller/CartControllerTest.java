package me.jweissen.aeticket.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.jweissen.aeticket.dto.request.CartAddRequestDto;
import me.jweissen.aeticket.dto.request.CartEntryRequestDto;
import me.jweissen.aeticket.dto.response.CheckoutResponseDto;
import me.jweissen.aeticket.model.Cart;
import me.jweissen.aeticket.model.Role;
import me.jweissen.aeticket.model.User;
import me.jweissen.aeticket.service.AuthService;
import me.jweissen.aeticket.service.CartService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CartController.class)
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CartService cartService;
    @MockBean
    private AuthService authService;

    @Test
    void addEntry() throws Exception {
        CartAddRequestDto dto = new CartAddRequestDto(
                999L,
                Arrays.asList(
                        new CartEntryRequestDto(1L, 10),
                        new CartEntryRequestDto(2L, 10)
                )
        );
        Cart cart = new Cart();
        User user = User.builder()
                .id(1L)
                .firstname("jonas")
                .lastname("weissensteiner")
                .email("my@mail.com")
                .password("geheim")
                .currentCart(cart)
                .role(Role.ADMIN).build();
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        Mockito.when(cartService.add(dto, cart)).thenReturn(true);
        final ObjectMapper mapper = new ObjectMapper();
        final String jsonContent = mapper.writeValueAsString(dto);
        mockMvc.perform(
                post("/cart/add").contentType("application/json").content(jsonContent)).andExpect(status().isNoContent()
        );
    }

    @Test
    void checkout() throws Exception {
        Cart cart = new Cart();
        User user = User.builder()
                .id(1L)
                .firstname("jonas")
                .lastname("weissensteiner")
                .email("my@mail.com")
                .password("geheim")
                .currentCart(cart)
                .role(Role.ADMIN).build();
        Mockito.when(authService.getCurrentUser()).thenReturn(user);
        Mockito.when(cartService.checkout(cart)).thenReturn(new CheckoutResponseDto(169.99));
        mockMvc.perform(
                get("/cart/checkout")).andExpect(jsonPath("$.price").value(169.99)
        );
    }
}
