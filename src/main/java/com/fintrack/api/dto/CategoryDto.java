package com.fintrack.api.dto;

import lombok.Data;

/**
 * Data trasfer object for category
 */
@Data
public class CategoryDto {
    private Long id;
    private String name;
    private String type;
}
