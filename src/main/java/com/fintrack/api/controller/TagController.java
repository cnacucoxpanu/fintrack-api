package com.fintrack.api.controller;

import com.fintrack.api.dto.TagDto;
import com.fintrack.api.service.TagService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService service;

    @GetMapping
    public List<TagDto> getAllTags() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public TagDto getTagById(@PathVariable Long id) {
        return service.findById(id);
    }

    @PostMapping
    public TagDto createTag(@RequestBody TagDto dto) {
        return service.create(dto);
    }

    @PutMapping("/{id}")
    public TagDto updateTag(@PathVariable Long id, @RequestBody TagDto dto) {
        return service.update(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deleteTag(@PathVariable Long id) {
        service.delete(id);
    }
}