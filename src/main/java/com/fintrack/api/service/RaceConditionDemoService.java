package com.fintrack.api.service;


import com.fintrack.api.dto.CounterResponse;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class RaceConditionDemoService {
    private static final int THREADS_COUNT = 50;
    private static final int ITERATIONS = 100000;

    public CounterResponse raceConditionTest() throws InterruptedException {
        AtomicLong safeCounter = new AtomicLong(0);
        long[] unsafeCounter = {0};
        try (ExecutorService executor = Executors.newFixedThreadPool(THREADS_COUNT)) {

            for (int i = 0; i < THREADS_COUNT; i++) {
                executor.submit(() -> {
                    for (int j = 0; j < ITERATIONS; j++) {
                        unsafeCounter[0]++;
                        safeCounter.incrementAndGet();
                    }
                });
            }
            executor.shutdown();
            executor.awaitTermination(15, TimeUnit.SECONDS);
            return new CounterResponse((long)THREADS_COUNT * ITERATIONS, unsafeCounter[0], safeCounter.get());
        }
    }
}
