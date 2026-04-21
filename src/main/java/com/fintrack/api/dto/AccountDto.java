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

    @NotBlank(message = "Account name is required")
    private String name;

    @NotNull(message = "Balance must be specified")
    @PositiveOrZero(message = "Balance cannot be negative")
    private BigDecimal balance;

    @NotNull(message = "User ID is required")
    private Long userId;
}
