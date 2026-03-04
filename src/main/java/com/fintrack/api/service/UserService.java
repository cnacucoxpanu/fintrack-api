package com.fintrack.api.service;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.UserMapper;
import com.fintrack.api.repository.UserRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    public List<UserDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<UserDto> findAllWithAccountsNPlusOne() {
        // Обычный findAll провоцирует N+1 при обращении к accounts в маппере
        return repository.findAll().stream()
                .map(mapper::toDto)
                .toList();
    }

    public List<UserDto> findAllWithAccountsOptimized() {
        return repository.findAllWithAccounts().stream() // Вызов правильного метода
                .map(mapper::toDto)
                .toList();
    }

    public UserDto findById(Long id) {
        return mapper.toDto(repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id)));
    }

    public UserDto create(UserDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public UserDto update(Long id, UserDto dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        return mapper.toDto(repository.save(user));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void createUserWithAccountsTransactional() {
        User user = User.builder()
                .name("TestTx")
                .email("tx@mail.com")
                .build();
        repository.save(user);
        throw new IllegalStateException("Rollback demo: transaction will be rolled back");
    }

    public void createUserWithAccountsNonTransactional() {
        User user = User.builder()
                .name("TestNoTx")
                .email("notx@mail.com")
                .build();
        repository.save(user);
        throw new IllegalStateException("Partial save demo: data remains in DB");
    }
}