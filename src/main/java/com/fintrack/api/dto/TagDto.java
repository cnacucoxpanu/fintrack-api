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
public class TagDto {

    private Long id;

    @NotBlank(message = "Название тега обязательно")
    @Size(max = 30, message = "Название тега не должно превышать 30 символов")
    private String name;
}