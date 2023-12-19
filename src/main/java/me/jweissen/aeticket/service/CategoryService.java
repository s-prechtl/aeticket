package me.jweissen.aeticket.service;

import me.jweissen.aeticket.dto.request.CategoryRequestDto;
import me.jweissen.aeticket.dto.request.CategoryUpdateRequestDto;
import me.jweissen.aeticket.dto.response.CategoryResponseDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.model.Category;
import me.jweissen.aeticket.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public static Category fromDto(CategoryRequestDto dto) {
        return new Category(
            dto.name(),
            eurosToCents(dto.price()),
            dto.stock()
        );
    }

    public static List<Category> fromDtos(List<CategoryRequestDto> dtos) {
        return dtos.stream().map(CategoryService::fromDto).toList();
    }

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

    public static Integer eurosToCents(Double euros) {
        return (Double.valueOf(euros * 100)).intValue();
    }

    public CategoryResponseDto create(CategoryRequestDto dto) {
        Category category = categoryRepository.save(fromDto(dto));
        return toDto(category);
    }

    public boolean update(CategoryUpdateRequestDto dto) {
        System.out.println(dto);
        return categoryRepository.findById(dto.id())
            .map(category -> {
                category.setName(dto.name());
                category.setPrice(eurosToCents(dto.price()));
                category.setStock(dto.stock());
                categoryRepository.save(category);
                return true;
            })
            .orElse(false);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }


    public Optional<CategoryResponseDto> getById(Long id) {
        return categoryRepository.findById(id).map(CategoryService::toDto);
    }

    public List<CategoryResponseDto> getAll() {
        return toDtos(categoryRepository.findAll());
    }
}
