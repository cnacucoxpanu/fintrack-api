package com.fintrack.api.service;

import com.fintrack.api.dto.TaskStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsyncTaskService {

    private static final Logger log = LoggerFactory.getLogger(AsyncTaskService.class);

    private final Map<String, TaskInfo> taskRegistry = new ConcurrentHashMap<>();
    private final AtomicLong taskCounter = new AtomicLong(0);

    record TaskInfo(
            String taskId,
            String status,
            String result,
            long startTime,
            Long endTime
    ) {
    }

    public CompletableFuture<String> startReportGeneration(int months) {
        String taskId = "task-" + taskCounter.incrementAndGet();
        long startTime = System.currentTimeMillis();

        taskRegistry.put(taskId, new TaskInfo(taskId, "RUNNING", null, startTime, null));
        log.info("Task {} started: generate report for {} months", taskId, months);

        return generateReportAsync(taskId, months, startTime);
    }

    @Async
    private CompletableFuture<String> generateReportAsync(String taskId, int months, long startTime) {
        try {
            // Имитация длительной операции (100 мс для ускорения тестов)
            Thread.sleep(100L * months);

            String result = "Report generated for " + months + " months. Total transactions: " + (months * 150);
            taskRegistry.put(taskId, new TaskInfo(taskId, "COMPLETED", result, startTime, System.currentTimeMillis()));
            log.info("Task {} completed in {} ms", taskId, System.currentTimeMillis() - startTime);
            return CompletableFuture.completedFuture(taskId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            taskRegistry.put(taskId, new TaskInfo(taskId, "INTERRUPTED", null, startTime, System.currentTimeMillis()));
            return CompletableFuture.failedFuture(e);
        } catch (Exception e) {
            taskRegistry.put(taskId, new TaskInfo(taskId, "FAILED", e.getMessage(), startTime, System.currentTimeMillis()));
            log.error("Task {} failed", taskId, e);
            return CompletableFuture.failedFuture(e);
        }
    }

    /**
     * Проверяет статус задачи по ID.
     */
    public TaskStatusDto getTaskStatus(String taskId) {
        TaskInfo info = taskRegistry.get(taskId);
        if (info == null) {
            return new TaskStatusDto(taskId, "NOT_FOUND", null, 0, null, null);
        }

        Long durationMs = info.endTime() != null ? info.endTime() - info.startTime() : null;
        return new TaskStatusDto(
                info.taskId(),
                info.status(),
                info.result(),
                info.startTime(),
                info.endTime(),
                durationMs
        );
    }

    /**
     * Возвращает потокобезопасный счётчик запущенных задач (демонстрация Atomic).
     */
    public long getTotalTasksStarted() {
        return taskCounter.get();
    }
}
