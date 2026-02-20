package com.fintrack.api.controller;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.service.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * REST controller for category recources
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService service;

    /**
     * creates a new category
     *
     * @param dto the category data
     * @return the created category
     */
    @PostMapping
    public CategoryDto create(@RequestBody CategoryDto dto) {
        return service.create(dto);
    }

    /**
     * gets a category by its ID
     *
     * @param id the category ID
     * @return the found category
     */
    @GetMapping("/{id}")
    public CategoryDto getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * gets all categories, optionally filtered by name
     *
     * @param name the name filter (optional)
     * @return list of categories
     */
    @GetMapping
    public List<CategoryDto> getAll(@RequestParam(required = false) String name) {
        return service.getAll(name);
    }
}
