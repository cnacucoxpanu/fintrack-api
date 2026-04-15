package com.fintrack.api.controller;

import com.fintrack.api.service.RaceConditionDemoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/concurrency")
@Tag(name = "Concurrency Demo", description = "Демонстрация race condition и решений")
public class RaceConditionDemoController {

    private final RaceConditionDemoService raceConditionDemoService;

    public RaceConditionDemoController(RaceConditionDemoService raceConditionDemoService) {
        this.raceConditionDemoService = raceConditionDemoService;
    }

    @PostMapping("/race-condition")
    @Operation(summary = "Продемонстрировать race condition (50 потоков)")
    public ResponseEntity<Map<String, Object>> demonstrateRaceCondition() {
        return ResponseEntity.ok(raceConditionDemoService.demonstrateRaceCondition());
    }

    @PostMapping("/synchronized")
    @Operation(summary = "Решение через synchronized")
    public ResponseEntity<Map<String, Object>> demonstrateSynchronized() {
        return ResponseEntity.ok(raceConditionDemoService.demonstrateSynchronizedSolution());
    }

    @PostMapping("/atomic")
    @Operation(summary = "Решение через AtomicLong")
    public ResponseEntity<Map<String, Object>> demonstrateAtomic() {
        return ResponseEntity.ok(raceConditionDemoService.demonstrateAtomicSolution());
    }

    @PostMapping("/lock")
    @Operation(summary = "Решение через ReentrantLock")
    public ResponseEntity<Map<String, Object>> demonstrateLock() {
        return ResponseEntity.ok(raceConditionDemoService.demonstrateLockSolution());
    }

    @PostMapping("/full-comparison")
    @Operation(summary = "Полное сравнение всех 4 подходов")
    public ResponseEntity<Map<String, Object>> fullComparison() {
        return ResponseEntity.ok(raceConditionDemoService.runFullComparison());
    }
}
