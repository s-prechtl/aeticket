package me.jweissen.aeticket.controller;

import me.jweissen.aeticket.dto.request.CategoryRequestDto;
import me.jweissen.aeticket.dto.request.CategoryUpdateRequestDto;
import me.jweissen.aeticket.dto.request.EventRequestDto;
import me.jweissen.aeticket.dto.request.EventUpdateRequestDto;
import me.jweissen.aeticket.dto.response.CategoryResponseDto;
import me.jweissen.aeticket.dto.response.EventResponseDto;
import me.jweissen.aeticket.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CategoryRequestDto dto) {
        // TODO admin only
        categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody CategoryUpdateRequestDto dto) {
        // TODO admin only
        System.out.println(dto);
        if (!categoryService.update(dto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO admin only
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return categoryService.getById(id)
            .map(categoryResponseDto -> new ResponseEntity<>(categoryResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }
}
