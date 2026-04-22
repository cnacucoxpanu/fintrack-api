package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import com.fintrack.api.exception.CategoryInUseException;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository repository;
    private final CategoryMapper mapper;

    @Transactional(readOnly = true)
    public List<CategoryDto> findAll(String name) {
        if (name != null && !name.isEmpty()) {
            return repository.findByNameContainingIgnoreCase(name).stream().map(mapper::toDto).toList();
        }
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public CategoryDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found")));
    }

    @Transactional
    public CategoryDto create(CategoryDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public CategoryDto update(Long id, CategoryDto dto) {
        Category category = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));
        category.setName(dto.getName());
        category.setType(dto.getType());
        return mapper.toDto(repository.save(category));
    }

    @Transactional
    public void delete(Long id) {
        Category category = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Category not found"));

        if (category.getTransactions() != null && !category.getTransactions().isEmpty()) {
            throw new CategoryInUseException(
                "Cannot delete category. It is used in " + category.getTransactions().size() + " transaction(s). " +
                "Please delete or reassign those transactions first."
            );
        }

        repository.deleteById(id);
    }
}
