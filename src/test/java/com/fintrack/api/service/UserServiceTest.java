package com.fintrack.api.service;

import com.fintrack.api.dto.UserDto;
import com.fintrack.api.entity.User;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.UserMapper;
import com.fintrack.api.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository repository;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    private User user;
    private UserDto dto;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();

        dto = UserDto.builder()
                .id(1L)
                .name("Test User")
                .email("test@example.com")
                .build();
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        when(repository.findAllWithAccounts()).thenReturn(List.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        List<UserDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAllWithAccounts();
    }

    @Test
    void findAll_emptyList_shouldReturnEmptyList() {
        when(repository.findAllWithAccounts()).thenReturn(List.of());

        List<UserDto> result = service.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findAll_multipleUsers_shouldReturnAll() {
        User user2 = User.builder()
                .id(2L)
                .name("Second User")
                .email("second@example.com")
                .build();

        UserDto dto2 = UserDto.builder()
                .id(2L)
                .name("Second User")
                .email("second@example.com")
                .build();

        when(repository.findAllWithAccounts()).thenReturn(List.of(user, user2));
        when(mapper.toDto(user)).thenReturn(dto);
        when(mapper.toDto(user2)).thenReturn(dto2);

        List<UserDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findById_shouldReturnUser() {
        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDto result = service.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).findById(1L);
    }

    @Test
    void findById_notFound_shouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.findById(999L));
    }

    @Test
    void create_shouldSaveUser() {
        when(mapper.toEntity(dto)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(dto);

        UserDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(user);
    }

    @Test
    void create_mapperCalled_shouldSaveUser() {
        when(mapper.toEntity(dto)).thenReturn(user);
        when(repository.save(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(dto);

        UserDto result = service.create(dto);

        verify(mapper).toEntity(dto);
        verify(mapper).toDto(user);
        assertNotNull(result);
    }

    @Test
    void update_shouldUpdateUser() {
        UserDto updateDto = UserDto.builder()
                .id(1L)
                .name("Updated User")
                .email("updated@example.com")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.toDto(user)).thenReturn(dto);

        UserDto result = service.update(1L, updateDto);

        assertNotNull(result);
        verify(repository).findById(1L);
        assertEquals("Updated User", user.getName());
        assertEquals("updated@example.com", user.getEmail());
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void delete_shouldDeleteUser() {
        when(repository.existsById(1L)).thenReturn(true);
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).existsById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(repository.existsById(999L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> service.delete(999L));
    }
}
