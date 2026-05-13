package com.fintrack.api.controller;

import com.fintrack.api.dto.CounterResponse;
import com.fintrack.api.service.RaceConditionDemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/counts")
@RequiredArgsConstructor
public class RaceConditionDemoController {
    private final RaceConditionDemoService counterService;

    @PostMapping("/rc")
    public ResponseEntity<CounterResponse> testRaceCondition() throws InterruptedException {
        return ResponseEntity.ok(counterService.raceConditionTest());
    }
}