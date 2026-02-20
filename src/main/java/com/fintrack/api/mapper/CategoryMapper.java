package com.fintrack.api.mapper;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.model.Category;
import org.springframework.stereotype.Component;

/**
 * Mapper for converting between Category entity and CategoryDto.
 */
@Component
public class CategoryMapper {

    /**
     * Converts Entity to DTO.
     *
     * @param entity the category entity
     * @return the category DTO
     */
    public CategoryDto toDto(Category entity) {
        if (entity == null) {
            return null;
        }
        CategoryDto dto = new CategoryDto();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setType(entity.getType());
        return dto;
    }

    /**
     * Converts DTO to Entity.
     *
     * @param dto the category DTO
     * @return the category entity
     */
    public Category toEntity(CategoryDto dto) {
        if (dto == null) {
            return null;
        }
        Category entity = new Category();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setType(dto.getType());
        return entity;
    }
}