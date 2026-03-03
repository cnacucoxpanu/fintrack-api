package com.fintrack.api.mapper;

import com.fintrack.api.dto.AccountDto;
import com.fintrack.api.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        AccountDto dto = new AccountDto();
        dto.setId(account.getId());
        dto.setName(account.getName());
        dto.setBalance(account.getBalance());
        dto.setCurrencyCode(account.getCurrencyCode());
        dto.setType(account.getType());
        dto.setIcon(account.getIcon());
        dto.setCreditLimit(account.getCreditLimit());
        dto.setArchived(account.getIsArchived());

        if (account.getUser() != null) {
            dto.setUserId(account.getUser().getId());
        }

        return dto;
    }

    public Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setId(dto.getId());
        account.setName(dto.getName());
        account.setBalance(dto.getBalance());
        account.setCurrencyCode(dto.getCurrencyCode());
        account.setType(dto.getType());
        account.setIcon(dto.getIcon());
        account.setCreditLimit(dto.getCreditLimit());
        account.setIsArchived(Boolean.TRUE.equals(dto.getArchived()));
        return account;
    }
}