package com.fintrack.api.controller;

import com.fintrack.api.dto.TaskStatusDto;
import com.fintrack.api.service.AsyncTaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/async")
@Tag(name = "Async Operations", description = "Асинхронные операции и управление задачами")
public class AsyncTaskController {

    private final AsyncTaskService asyncTaskService;

    public AsyncTaskController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @PostMapping("/report")
    @Operation(summary = "Запустить генерацию отчёта асинхронно")
    public ResponseEntity<Map<String, String>> startReport(@RequestParam(defaultValue = "3") int months) {
        CompletableFuture<String> future = asyncTaskService.startReportGeneration(months);
        // Возвращаем taskId немедленно, не дожидаясь завершения
        String taskId = future.join();
        return ResponseEntity.accepted().body(Map.of(
                "taskId", taskId,
                "message", "Task started. Check status at /api/async/status/{taskId}"
        ));
    }

    @GetMapping("/status/{taskId}")
    @Operation(summary = "Проверить статус асинхронной задачи")
    public ResponseEntity<TaskStatusDto> getTaskStatus(@PathVariable String taskId) {
        TaskStatusDto status = asyncTaskService.getTaskStatus(taskId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/counter")
    @Operation(summary = "Получить общее количество запущенных задач (Atomic счётчик)")
    public ResponseEntity<Map<String, Long>> getTotalTasks() {
        return ResponseEntity.ok(Map.of("totalTasksStarted", asyncTaskService.getTotalTasksStarted()));
    }
}
