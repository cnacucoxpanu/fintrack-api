package com.fintrack.api.mapper;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        dto.setDefaultCurrencyCode(user.getDefaultCurrencyCode());
        return dto;
    }

    public User toEntity(UserDto dto) {
        User user = new User();
        user.setId(dto.getId());
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setDefaultCurrencyCode(dto.getDefaultCurrencyCode());
        return user;
    }
}