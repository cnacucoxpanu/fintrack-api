package com.fintrack.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private Long id;

    @NotBlank(message = "Название счета обязательно")
    private String name;

    @NotNull(message = "Баланс должен быть указан")
    @PositiveOrZero(message = "Баланс не может быть отрицательным")
    private BigDecimal balance;

    @NotNull(message = "ID пользователя обязателен")
    private Long userId;
}