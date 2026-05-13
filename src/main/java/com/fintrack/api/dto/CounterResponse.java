package com.fintrack.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CounterResponse {
    private long expected;
    private long actualUnsafe;
    private long actualSafe;
}