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
@RequestMapping("/eventcategory")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<Void> create(@RequestBody CategoryRequestDto dto) {
        // TODO admin only
        categoryService.create(dto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<Void> update(@RequestBody CategoryUpdateRequestDto dto) {
        // TODO admin only
        if (!categoryService.update(dto)) {
            return ResponseEntity.notFound().build();
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // TODO admin only
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return categoryService.getById(id)
            .map(categoryResponseDto -> new ResponseEntity<>(categoryResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/list")
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }

}
