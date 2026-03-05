package com.fintrack.api.service;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.UserMapper;
import com.fintrack.api.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public List<UserDto> findAllWithAccountsNPlusOne() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public List<UserDto> findAllWithAccountsOptimized() {
        return repository.findAllWithAccounts().stream().map(mapper::toDto).toList();
    }

    public UserDto findById(Long id) {
        return repository.findById(id)
                .map(mapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    public UserDto create(UserDto dto) {
        User user = mapper.toEntity(dto);
        return mapper.toDto(repository.save(user));
    }

    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return mapper.toDto(user);
    }

    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("User not found");
        }
        repository.deleteById(id);
    }

    @Transactional
    public void createUserWithAccountsTransactional() {
        User user = User.builder().name("Tx_User").email("tx@test.com").build();
        repository.save(user);
        throw new EntityNotFoundException("Rollback triggered");
    }

    public void createUserWithAccountsNonTransactional() {
        User user = User.builder().name("NonTx_User").email("notx@test.com").build();
        repository.save(user);
        throw new EntityNotFoundException("No rollback here");
    }
}