package com.fintrack.api.mapper;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        dto.setDescription(category.getDescription());
        dto.setIcon(category.getIcon());
        dto.setMonthlyLimit(category.getMonthlyLimit());
        return dto;
    }

    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setType(dto.getType());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());
        category.setMonthlyLimit(dto.getMonthlyLimit());
        return category;
    }
}