package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.CartAddRequestDto;
import me.jweissen.aeticket.dto.request.CartEntryRequestDto;
import me.jweissen.aeticket.model.*;
import me.jweissen.aeticket.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
public class CartServiceTest {
    @MockBean
    private CategoryRepository categoryRepository;

    @Autowired
    private CartService cartService;

    @Test
    public void add() {
        CartAddRequestDto dto = new CartAddRequestDto(
                1L,
                Arrays.asList(
                        new CartEntryRequestDto(1L, 10),
                        new CartEntryRequestDto(2L, 20)
                )
        );
        Cart cart = new Cart();
        cart.setCartEntries(new ArrayList<>());
        Category category1 = Category.builder()
                .id(1L).name("Cat 1").price(200).stock(100).build();
        Category category2 = Category.builder()
                .id(2L).name("Cat 2").price(200).stock(100).build();
        Mockito.when(categoryRepository.findById(1L)).thenReturn(Optional.of(category1));
        Mockito.when(categoryRepository.findById(2L)).thenReturn(Optional.of(category2));
        Mockito.when(categoryRepository.unavailableTickets(category1)).thenReturn(0);
        Mockito.when(categoryRepository.unavailableTickets(category2)).thenReturn(0);

        Assertions.assertTrue(cartService.add(dto, cart));
    }
}
