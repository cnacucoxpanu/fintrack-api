package com.fintrack.api.service;

import com.fintrack.api.dto.TagDto;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.mapper.TagMapper;
import com.fintrack.api.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    public List<TagDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    public TagDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found")));
    }

    public TagDto create(TagDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    public TagDto update(Long id, TagDto dto) {
        Tag tag = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tag.setName(dto.getName());
        return mapper.toDto(repository.save(tag));
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}