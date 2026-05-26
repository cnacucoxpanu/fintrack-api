package com.fintrack.api.service;

import com.fintrack.api.dto.TagDto;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.TagMapper;
import com.fintrack.api.repository.TagRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository repository;

    @Mock
    private TagMapper mapper;

    @InjectMocks
    private TagService service;

    private Tag tag;
    private TagDto dto;

    @BeforeEach
    void setUp() {
        tag = Tag.builder()
                .id(1L)
                .name("Groceries")
                .transactions(new HashSet<>())
                .build();

        dto = TagDto.builder()
                .id(1L)
                .name("Groceries")
                .build();
    }

    @Test
    void findAll_shouldReturnAllTags() {
        when(repository.findAll()).thenReturn(List.of(tag));
        when(mapper.toDto(tag)).thenReturn(dto);

        List<TagDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repository).findAll();
    }

    @Test
    void findAll_emptyList_shouldReturnEmptyList() {
        when(repository.findAll()).thenReturn(List.of());

        List<TagDto> result = service.findAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void findById_shouldReturnTag() {
        when(repository.findById(1L)).thenReturn(Optional.of(tag));
        when(mapper.toDto(tag)).thenReturn(dto);

        TagDto result = service.findById(1L);

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
    void create_shouldSaveTag() {
        when(mapper.toEntity(dto)).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toDto(tag)).thenReturn(dto);

        TagDto result = service.create(dto);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(tag);
    }

    @Test
    void create_mapperCalled_shouldSaveTag() {
        when(mapper.toEntity(dto)).thenReturn(tag);
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toDto(tag)).thenReturn(dto);

        TagDto result = service.create(dto);

        verify(mapper).toEntity(dto);
        verify(mapper).toDto(tag);
        assertNotNull(result);
    }

    @Test
    void update_shouldUpdateTag() {
        TagDto updateDto = TagDto.builder()
                .id(1L)
                .name("Updated Groceries")
                .build();

        when(repository.findById(1L)).thenReturn(Optional.of(tag));
        when(repository.save(tag)).thenReturn(tag);
        when(mapper.toDto(tag)).thenReturn(dto);

        TagDto result = service.update(1L, updateDto);

        assertNotNull(result);
        verify(repository).findById(1L);
        verify(repository).save(tag);
        assertEquals("Updated Groceries", tag.getName());
    }

    @Test
    void update_notFound_shouldThrowException() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.update(1L, dto));
    }

    @Test
    void delete_shouldDeleteTag() {
        when(repository.findById(1L)).thenReturn(Optional.of(tag));
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
    void findAll_multipleTags_shouldReturnAll() {
        Tag tag2 = Tag.builder()
                .id(2L)
                .name("Shopping")
                .transactions(new HashSet<>())
                .build();

        TagDto dto2 = TagDto.builder()
                .id(2L)
                .name("Shopping")
                .build();

        when(repository.findAll()).thenReturn(List.of(tag, tag2));
        when(mapper.toDto(tag)).thenReturn(dto);
        when(mapper.toDto(tag2)).thenReturn(dto2);

        List<TagDto> result = service.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
    }
}