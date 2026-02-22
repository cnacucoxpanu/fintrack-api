package com.fintrack.api.controller;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.service.CategoryService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for category resources.
 */
@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService service;

    /**
     * Gets all categories, optionally filtered by name.
     * Demonstrates usage of @RequestParam.
     *
     * @param name the name filter (optional)
     * @return list of categories
     */
    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(required = false) String name) {
        return service.getAll(name);
    }

    /**
     * Gets a category by its ID.
     * Demonstrates usage of @PathVariable.
     *
     * @param id the category ID
     * @return the found category
     */
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * Creates a new category.
     *
     * @param dto the category data
     * @return the created category
     */
    @PostMapping
    public CategoryDto create(@Valid @RequestBody CategoryDto dto) {
        return service.create(dto);
    }

    /**
     * Deletes a category.
     *
     * @param id the category ID
     */
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        service.delete(id);
    }
}