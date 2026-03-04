package com.fintrack.api.mapper;

import com.fintrack.api.dto.AccountDto;
import com.fintrack.api.entity.Account;
import com.fintrack.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDto toDto(Account account) {
        if (account == null) {
            return null;
        }
        return AccountDto.builder()
                .id(account.getId())
                .name(account.getName())
                .balance(account.getBalance())
                .userId(account.getUser() != null ? account.getUser().getId() : null)
                .build();
    }

    public Account toEntity(AccountDto dto, User user) {
        if (dto == null) {
            return null;
        }
        return Account.builder()
                .id(dto.getId())
                .name(dto.getName())
                .balance(dto.getBalance())
                .user(user)
                .build();
    }
}