package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import com.fintrack.api.exception.ResourceNotFoundException;
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

    public CategoryDto create(CategoryDto dto) {
        Category category = mapper.toEntity(dto);
        Category saved = repository.save(category);
        return mapper.toDto(saved);
    }

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

    public CategoryDto getById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    public CategoryDto update(Long id, CategoryDto dto) {
        Category existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));

        existing.setName(dto.getName());
        existing.setType(dto.getType());

        Category saved = repository.save(existing);
        return mapper.toDto(saved);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Category not found with id: " + id);
        }
        repository.deleteById(id);
    }
}