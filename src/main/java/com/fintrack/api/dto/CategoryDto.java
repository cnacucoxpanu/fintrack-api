package com.fintrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryDto {

    private Long id;

    @NotBlank(message = "Название категории обязательно")
    @Size(max = 50, message = "Название категории не должно превышать 50 символов")
    private String name;

    @Size(max = 20, message = "Тип категории не должен превышать 20 символов")
    private String type;
}