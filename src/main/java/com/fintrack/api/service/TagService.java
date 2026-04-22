package com.fintrack.api.service;

import com.fintrack.api.dto.TagDto;
import com.fintrack.api.entity.Tag;
import com.fintrack.api.exception.EntityNotFoundException;
import com.fintrack.api.exception.TagInUseException;
import com.fintrack.api.mapper.TagMapper;
import com.fintrack.api.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;
    private final TagMapper mapper;

    @Transactional(readOnly = true)
    public List<TagDto> findAll() {
        return repository.findAll().stream().map(mapper::toDto).toList();
    }

    @Transactional(readOnly = true)
    public TagDto findById(Long id) {
        return mapper.toDto(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found")));
    }

    @Transactional
    public TagDto create(TagDto dto) {
        return mapper.toDto(repository.save(mapper.toEntity(dto)));
    }

    @Transactional
    public TagDto update(Long id, TagDto dto) {
        Tag tag = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tag not found"));
        tag.setName(dto.getName());
        return mapper.toDto(repository.save(tag));
    }

    @Transactional
    public void delete(Long id) {
        Tag tag = repository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Tag not found"));

        if (tag.getTransactions() != null && !tag.getTransactions().isEmpty()) {
            throw new TagInUseException(
                "Cannot delete tag. It is used in " + tag.getTransactions().size() + " transaction(s). " +
                "Please remove this tag from those transactions first."
            );
        }

        repository.deleteById(id);
    }
}