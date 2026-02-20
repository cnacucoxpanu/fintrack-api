package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.model.Category;
import com.fintrack.api.repository.CategoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * service for managing categories
 */
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    /**
     * creates a new category
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
     *finds a category by its ID
     *
     * @param id the category
     * @return the found category
     */
    public CategoryDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new RuntimeException("Category not found with id: "));
    }

    /**
     * retrieves all categories, optionally filtered by name
     *
     * @param name the name filter (optional)
     * @return list of categories
     */
    public List<CategoryDto> getAll(String name) {
        List<Category> list = repository.findAll();

        if (name != null && !name.isEmpty()) {
            return list.stream()
                    .filter(c -> c.getName().toLowerCase().contains(name.toLowerCase()))
                    .map(mapper::toDto)
                    .collect(Collectors.toList());
        }
        return list.stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
