package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.response.CartEntryResponseDto;
import me.jweissen.aeticket.dto.response.CartEventResponseDto;
import me.jweissen.aeticket.model.Cart;
import me.jweissen.aeticket.model.Event;
import me.jweissen.aeticket.repository.CartEntryRepository;
import me.jweissen.aeticket.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;

    public CartService(CartRepository cartRepository, CartEntryRepository cartEntryRepository) {
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
    }

    public List<CartEventResponseDto> toDto(Cart cart) {
        List<Event> distinctEvents = cart.getCartEntries().stream().map(
                entry -> entry.getCategory().getEvent()
        ).distinct().toList();
        return distinctEvents.stream().map(event ->
            new CartEventResponseDto(
                event.getId(),
                event.getTitle(),
                event.getStart(),
                event.getEnd(),
                event.getDescription(),
                CartEntryService.toDtos(cartEntryRepository.getByCartAndEvent(cart.getId(), event.getId()))
            )).toList();
    }

    //public List<CartEntryResponseDto> getCartByAuthToken() {

}
