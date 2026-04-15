package com.fintrack.api.dto;

public record TaskStatusDto(
        String taskId,
        String status,
        String result,
        long startTime,
        Long endTime,
        Long durationMs
) {
}
