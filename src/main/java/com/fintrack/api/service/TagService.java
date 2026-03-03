package com.fintrack.api.service;

import com.fintrack.api.entity.Tag;
import com.fintrack.api.repository.TagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository repository;

    public Tag create(Tag tag) {
        return repository.save(tag);
    }

    public List<Tag> getAll() {
        return repository.findAll();
    }

    public Tag getById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));
    }

    public Tag update(Long id, Tag tag) {
        Tag existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found: " + id));

        existing.setName(tag.getName());

        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
