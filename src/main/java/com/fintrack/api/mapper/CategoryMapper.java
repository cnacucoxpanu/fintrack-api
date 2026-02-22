package com.fintrack.api.mapper;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Category entity and CategoryDto.
 */
@Component
public class CategoryMapper {

    /**
     * Converts Entity to DTO.
     *
     * @param category the entity
     * @return the dto
     */
    public CategoryDto toDto(Category category) {
        if (category == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setType(category.getType());
        return dto;
    }

    /**
     * Converts DTO to Entity.
     *
     * @param dto the dto
     * @return the entity
     */
    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category category = new Category();
        category.setId(dto.getId());
        category.setName(dto.getName());
        category.setType(dto.getType());
        return category;
    }
}