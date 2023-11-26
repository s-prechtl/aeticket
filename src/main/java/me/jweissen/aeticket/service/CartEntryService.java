package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.response.CartEntryResponseDto;
import me.jweissen.aeticket.model.CartEntry;
import me.jweissen.aeticket.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartEntryService {
    public static CartEntryResponseDto toDto(CartEntry cartEntry) {
        Category category = cartEntry.getCategory();
        return new CartEntryResponseDto(
            category.getId(),
            category.getName(),
            CategoryService.centsToEuros(category.getPrice()),
            cartEntry.getAmount()
        );
    }

    public static List<CartEntryResponseDto> toDtos(List<CartEntry> cartEntries) {
        return cartEntries.stream().map(CartEntryService::toDto).toList();
    }
}
