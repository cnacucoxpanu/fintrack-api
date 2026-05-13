package com.fintrack.api.service;

import com.fintrack.api.dto.TaskStatusDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class AsyncTaskService {

    private static final Logger LOG = LoggerFactory.getLogger(AsyncTaskService.class);
    private static final long SLEEP_TIME_PER_MONTH_MS = 1000L;
    private static final int TRANSACTIONS_PER_MONTH = 150;
    private static final int MAX_MONTHS = 24;

    private final Map<String, TaskInfo> taskRegistry = new ConcurrentHashMap<>();
    private final AtomicLong taskCounter = new AtomicLong(0);
    private final ApplicationContext applicationContext;

    public AsyncTaskService(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    record TaskInfo(
            String taskId,
            String status,
            String result,
            long startTime,
            Long endTime
    ) {
    }

    public String startReportGeneration(int months) {
        int validatedMonths = Math.clamp(months, 1, MAX_MONTHS);
        String taskId = "task-" + taskCounter.incrementAndGet();
        long startTime = System.currentTimeMillis();

        taskRegistry.put(taskId, new TaskInfo(taskId, "RUNNING", null, startTime, null));
        LOG.info("Task {} started: generate report for {} months", taskId, validatedMonths);

        applicationContext.getBean(AsyncTaskService.class)
                .generateReportAsync(taskId, validatedMonths, startTime);

        return taskId;
    }

    @Async
    CompletableFuture<String> generateReportAsync(String taskId, int months, long startTime) {
        try {
            Thread.sleep(SLEEP_TIME_PER_MONTH_MS * months);

            String result = "Report generated for " + months + " months. Total transactions: " + (months * TRANSACTIONS_PER_MONTH);
            taskRegistry.put(taskId, new TaskInfo(taskId, "COMPLETED", result, startTime, System.currentTimeMillis()));
            LOG.info("Task {} completed in {} ms", taskId, System.currentTimeMillis() - startTime);
            return CompletableFuture.completedFuture(taskId);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            taskRegistry.put(taskId, new TaskInfo(taskId, "INTERRUPTED", null, startTime, System.currentTimeMillis()));
            return CompletableFuture.failedFuture(e);
        } catch (Exception e) {
            taskRegistry.put(taskId, new TaskInfo(taskId, "FAILED", e.getMessage(), startTime,
                    System.currentTimeMillis()));
            LOG.error("Task {} failed", taskId, e);
            return CompletableFuture.failedFuture(e);
        }
    }

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

    public long getTotalTasksStarted() {
        return taskCounter.get();
    }
}
