package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.response.CategoryResponseDto;
import me.jweissen.aeticket.model.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    public static CategoryResponseDto toDto(Category category) {
        return new CategoryResponseDto(
            category.getId(),
            category.getName(),
            centsToEuros(category.getPrice()),
            category.getStock()
        );
    }

    public static List<CategoryResponseDto> toDtos(List<Category> categories) {
        return categories.stream().map(CategoryService::toDto).toList();
    }

    public static Double centsToEuros(int cents) {
        return cents / 100.0;
    }
}
