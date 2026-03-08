package com.fintrack.api.dto;

import lombok.Builder;
import lombok.Value;
import java.time.OffsetDateTime;
import java.util.List;

@Value
@Builder
public class ErrorResponse {
    String message;
    String code;
    OffsetDateTime timestamp;
    List<String> details;
}