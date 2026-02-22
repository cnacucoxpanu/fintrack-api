package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import com.fintrack.api.exception.ResourceNotFoundException;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service for managing categories.
 * Contains business logic.
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    /**
     * Creates a new category.
     *
     * @param dto the category data
     * @return the created category
     */
    public CategoryDto create(CategoryDto dto) {
        Category category = mapper.toEntity(dto);
        Category saved = repository.save(category);
        return mapper.toDto(saved);
    }

    /**
     * Retrieves all categories, optionally filtered by name.
     *
     * @param name the name filter (optional)
     * @return list of categories
     */
    public List<CategoryDto> getAll(String name) {
        List<Category> list = repository.findAll();

        if (name != null && !name.isBlank()) {
            return list.stream()
                    .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                    .map(mapper::toDto)
                    .toList();
        }

        return list.stream()
                .map(mapper::toDto)
                .toList();
    }

    /**
     * Finds a category by its ID.
     *
     * @param id the category ID
     * @return the found category
     * @throws ResourceNotFoundException if category is not found
     */
    public CategoryDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    /**
     * Deletes a category by its ID.
     *
     * @param id the category ID
     * @throws ResourceNotFoundException if category is not found
     */
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }
}