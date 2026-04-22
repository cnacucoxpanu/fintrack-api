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

@RestController
@RequestMapping("/api/async")
@Tag(name = "Async Operations", description = "Asynchronous operations and task management")
public class AsyncTaskController {

    private final AsyncTaskService asyncTaskService;

    public AsyncTaskController(AsyncTaskService asyncTaskService) {
        this.asyncTaskService = asyncTaskService;
    }

    @PostMapping("/report")
    @Operation(summary = "Start report generation asynchronously")
    public ResponseEntity<Map<String, String>> startReport(@RequestParam(defaultValue = "3") int months) {
        String taskId = asyncTaskService.startReportGeneration(months);
        return ResponseEntity.accepted().body(Map.of(
                "taskId", taskId,
                "message", "Task started. Check status at /api/async/status/{taskId}"
        ));
    }

    @GetMapping("/status/{taskId}")
    @Operation(summary = "Check asynchronous task status")
    public ResponseEntity<TaskStatusDto> getTaskStatus(@PathVariable String taskId) {
        TaskStatusDto status = asyncTaskService.getTaskStatus(taskId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/counter")
    @Operation(summary = "Get total number of started tasks (Atomic counter)")
    public ResponseEntity<Map<String, Long>> getTotalTasks() {
        return ResponseEntity.ok(Map.of("totalTasksStarted", asyncTaskService.getTotalTasksStarted()));
    }
}
