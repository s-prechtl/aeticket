package me.jweissen.aeticket.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import me.jweissen.aeticket.aspect.AdminOnly;
import me.jweissen.aeticket.aspect.UserOnly;
import me.jweissen.aeticket.dto.request.CategoryRequestDto;
import me.jweissen.aeticket.dto.request.CategoryUpdateRequestDto;
import me.jweissen.aeticket.dto.response.CategoryResponseDto;
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

    @Operation(
            summary = "Create a new category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @PostMapping("/create")
    @AdminOnly
    public ResponseEntity<Void> create(@RequestBody CategoryRequestDto dto) {
        categoryService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Update a category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Your request body was malformed/insufficient."
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @PutMapping("/update")
    @AdminOnly
    public ResponseEntity<Void> update(@RequestBody CategoryUpdateRequestDto dto) {
        if (!categoryService.update(dto)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(
            summary = "Delete a category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "204",
                    description = "Success"
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = "You're not authorized to perform this operation."
            ),
    })
    @DeleteMapping("/delete/{id}")
    @AdminOnly
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @Operation(
            summary = "Load a category"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(schema = @Schema(implementation = CategoryResponseDto.class))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No category found with the given id"
            ),
    })
    @GetMapping("/{id}")
    @UserOnly
    public ResponseEntity<CategoryResponseDto> getById(@PathVariable Long id) {
        return categoryService.getById(id)
            .map(categoryResponseDto -> new ResponseEntity<>(categoryResponseDto, HttpStatus.OK))
            .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(
            summary = "List all categories"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Success",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = CategoryResponseDto.class)))
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "You didn't provide proper authentication via a bearer token"
            ),
    })
    @GetMapping("/list")
    @UserOnly
    public ResponseEntity<List<CategoryResponseDto>> getAll() {
        return new ResponseEntity<>(categoryService.getAll(), HttpStatus.OK);
    }
}
