package com.fintrack.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto {
    private Long id;
    private String name;
    private Double balance;
    private Long userId;
    private String currencyCode;
    private String type;
    private String icon;
    private Double creditLimit;
    private Boolean archived;
}