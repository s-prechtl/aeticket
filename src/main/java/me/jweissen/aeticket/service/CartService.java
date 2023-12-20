package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.CartAddRequestDto;
import me.jweissen.aeticket.dto.request.CartEntryRequestDto;
import me.jweissen.aeticket.dto.response.CartEventResponseDto;
import me.jweissen.aeticket.dto.response.CheckoutResponseDto;
import me.jweissen.aeticket.model.Cart;
import me.jweissen.aeticket.model.CartEntry;
import me.jweissen.aeticket.model.Category;
import me.jweissen.aeticket.model.Event;
import me.jweissen.aeticket.repository.CartEntryRepository;
import me.jweissen.aeticket.repository.CartRepository;
import me.jweissen.aeticket.repository.CategoryRepository;
import me.jweissen.aeticket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartEntryRepository cartEntryRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository, CartEntryRepository cartEntryRepository,
                       CategoryRepository categoryRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartEntryRepository = cartEntryRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }

    public List<CartEventResponseDto> toDto(Cart cart) {
        List<Event> distinctEvents = cart.getCartEntries().stream().map(
                entry -> entry.getCategory().getEvent()
        ).distinct().toList();
        return distinctEvents.stream().map(event ->
            new CartEventResponseDto(
                event.getId(),
                event.getName(),
                event.getStart(),
                event.getEnd(),
                event.getDescription(),
                CartEntryService.toDtos(cartEntryRepository.getByCartAndEvent(cart.getId(), event.getId()))
            )).toList();
    }

    public boolean add(CartAddRequestDto dto, Cart cart) {
        // posting the eventIds is redundant so, those are ignored
        for (CartEntryRequestDto cartEntryDto: dto.cartEntries()) {
            Optional<Category> categoryOptional = categoryRepository.findById(cartEntryDto.id());
            if (categoryOptional.isEmpty()) {
                // category id not found
                return false;
            }
            Category category = categoryOptional.get();
            if (cartEntryDto.amount() > (category.getStock() - categoryRepository.unavailableTickets(category))) {
                // wants to order more tickets than available
                return false;
            }
            CartEntry cartEntry = new CartEntry(cart, category, cartEntryDto.amount());
            cartEntry = cartEntryRepository.save(cartEntry);
            cart.getCartEntries().add(cartEntry);
        }
        cartRepository.save(cart);
        return true;
    }

    public CheckoutResponseDto checkout(Cart cart) {
        cart.setCheckedOut(true);
        cartRepository.save(cart);
        // reset current cart
        Cart newCart = new Cart();
        newCart.setUser(cart.getUser());
        newCart = cartRepository.save(newCart);
        cart.getUser().setCurrentCart(newCart);
        userRepository.save(cart.getUser());
        return new CheckoutResponseDto(
            CategoryService.centsToEuros(cartRepository.getCheckoutPrice(cart))
        );
    }
}
