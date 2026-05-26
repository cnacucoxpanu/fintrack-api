package com.fintrack.api.service;

import com.fintrack.api.dto.CategoryDto;
import com.fintrack.api.entity.Category;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.CategoryMapper;
import com.fintrack.api.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @Mock
    private CategoryRepository repository;

    @Mock
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    private Category category;
    private CategoryDto dto;

    @BeforeEach
    void setUp() {
        category = Category.builder()
                .id(1L)
                .name("Food")
                .type("EXPENSE")
                .transactions(new ArrayList<>())
                .build();

        dto = CategoryDto.builder()
                .id(1L)
                .name("Food")
                .type("EXPENSE")
                .build();
    }

    @Test
    void findAll_withNameFilter_shouldReturnFilteredCategories() {
        when(repository.findByNameContainingIgnoreCase("Food")).thenReturn(List.of(category));
        when(mapper.toDto(category)).thenReturn(dto);

        List<CategoryDto> result = service.findAll("Food");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findByNameContainingIgnoreCase("Food");
    }

    @Test
    void findAll_withoutNameFilter_shouldReturnAllCategories() {
        when(repository.findAll()).thenReturn(List.of(category));
        when(mapper.toDto(category)).thenReturn(dto);

        List<CategoryDto> result = service.findAll(null);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findAll_emptyNameFilter_shouldReturnAllCategories() {
        when(repository.findAll()).thenReturn(List.of(category));
        when(mapper.toDto(category)).thenReturn(dto);

        List<CategoryDto> result = service.findAll("");

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findById_shouldReturnCategory() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(mapper.toDto(category)).thenReturn(dto);

        CategoryDto result = service.findById(1L);

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
    void create_shouldSaveCategory() {
        when(mapper.toEntity(dto)).thenReturn(category);
        when(repository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(dto);

        CategoryDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(category);
    }

    @Test
    void update_shouldUpdateCategory() {
        CategoryDto updateDto = CategoryDto.builder()
                .id(1L)
                .name("Updated Food")
                .type("EXPENSE")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(category));
        when(repository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(dto);

        CategoryDto result = service.update(1L, updateDto);

        assertNotNull(result);
        verify(repository).findById(1L);
        verify(repository).save(category);
        assertEquals("Updated Food", category.getName());
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void delete_shouldDeleteCategory() {
        when(repository.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(repository).deleteById(1L);

        service.delete(1L);

        verify(repository).findById(1L);
        verify(repository).deleteById(1L);
    }

    @Test
    void delete_notFound_shouldThrowException() {
        when(repository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.delete(999L));
    }

    @Test
    void create_mapperToEntityCalled_shouldSaveCategory() {
        when(mapper.toEntity(dto)).thenReturn(category);
        when(repository.save(category)).thenReturn(category);
        when(mapper.toDto(category)).thenReturn(dto);

        CategoryDto result = service.create(dto);

        verify(mapper).toEntity(dto);
        verify(mapper).toDto(category);
        assertNotNull(result);
    }

    @Test
    void findAll_multipleCategories_shouldReturnAll() {
        Category category2 = Category.builder()
                .id(2L)
                .name("Transport")
                .type("EXPENSE")
                .transactions(new ArrayList<>())
                .build();

        CategoryDto dto2 = CategoryDto.builder()
                .id(2L)
                .name("Transport")
                .type("EXPENSE")
                .build();

        when(repository.findAll()).thenReturn(List.of(category, category2));
        when(mapper.toDto(category)).thenReturn(dto);
        when(mapper.toDto(category2)).thenReturn(dto2);

        List<CategoryDto> result = service.findAll(null);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void findAll_withNameFilter_multipleCategories_shouldReturnFiltered() {
        Category category2 = Category.builder()
                .id(2L)
                .name("Fast Food")
                .type("EXPENSE")
                .transactions(new ArrayList<>())
                .build();

        CategoryDto dto2 = CategoryDto.builder()
                .id(2L)
                .name("Fast Food")
                .type("EXPENSE")
                .build();

        when(repository.findByNameContainingIgnoreCase("Food")).thenReturn(List.of(category, category2));
        when(mapper.toDto(category)).thenReturn(dto);
        when(mapper.toDto(category2)).thenReturn(dto2);

        List<CategoryDto> result = service.findAll("Food");

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}