package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    public List<CategoryDto> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name).stream().map(mapper::toDto).toList();
        }
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public CategoryDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found")));
    }

    public CategoryDto create(CategoryDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        category.setName(dto.getName());
        return mapper.toDto(repository.save(category));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}