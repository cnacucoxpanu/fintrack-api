package com.fintrack.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RaceConditionDemoService {

    private static final Logger log = LoggerFactory.getLogger(RaceConditionDemoService.class);
    private static final int THREAD_COUNT = 50;
    private static final int INCREMENTS_PER_THREAD = 1000;
    private static final int EXPECTED_TOTAL = THREAD_COUNT * INCREMENTS_PER_THREAD; // 50,000

    // --- ПотокоНЕбезопасный счётчик (демонстрация проблемы) ---
    private long unsafeCounter = 0;

    // --- Потокобезопасный счётчик через synchronized ---
    private long synchronizedCounter = 0;
    private final Object syncLock = new Object();

    // --- Потокобезопасный счётчик через Atomic ---
    private final AtomicLong atomicCounter = new AtomicLong(0);

    // --- Потокобезопасный счётчик через ReentrantLock ---
    private long lockCounter = 0;
    private final ReentrantLock lock = new ReentrantLock();

    /**
     * Демонстрирует race condition: несколько потоков увеличивают обычную переменную.
     * Ожидаемый результат: 50,000. Фактический: меньше из-за гонки потоков.
     */
    public Map<String, Object> demonstrateRaceCondition() {
        unsafeCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        log.warn("Starting RACE CONDITION demo with {} threads, {} increments each", THREAD_COUNT, INCREMENTS_PER_THREAD);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementUnsafe));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long actualResult = unsafeCounter;
        long lostUpdates = EXPECTED_TOTAL - actualResult;

        log.warn("Race condition result: {} (expected {}). Lost updates: {}", actualResult, EXPECTED_TOTAL, lostUpdates);

        return Map.of(
                "description", "Race Condition (без синхронизации)",
                "threads", THREAD_COUNT,
                "incrementsPerThread", INCREMENTS_PER_THREAD,
                "expectedTotal", EXPECTED_TOTAL,
                "actualResult", actualResult,
                "lostUpdates", lostUpdates,
                "hasRaceCondition", lostUpdates > 0,
                "timeMs", elapsed
        );
    }

    /**
     * Решение через synchronized.
     * Ожидаемый результат: ровно 50,000.
     */
    public Map<String, Object> demonstrateSynchronizedSolution() {
        synchronizedCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        log.info("Starting SYNCHRONIZED solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementSynchronized));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = synchronizedCounter;
        log.info("Synchronized result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                "description", "Solution with synchronized",
                "threads", THREAD_COUNT,
                "incrementsPerThread", INCREMENTS_PER_THREAD,
                "expectedTotal", EXPECTED_TOTAL,
                "actualResult", result,
                "lostUpdates", EXPECTED_TOTAL - result,
                "hasRaceCondition", false,
                "timeMs", elapsed
        );
    }

    /**
     * Решение через AtomicLong.
     * Ожидаемый результат: ровно 50,000.
     */
    public Map<String, Object> demonstrateAtomicSolution() {
        atomicCounter.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        log.info("Starting ATOMIC solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementAtomic));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = atomicCounter.get();
        log.info("Atomic result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                "description", "Solution with AtomicLong",
                "threads", THREAD_COUNT,
                "incrementsPerThread", INCREMENTS_PER_THREAD,
                "expectedTotal", EXPECTED_TOTAL,
                "actualResult", result,
                "lostUpdates", EXPECTED_TOTAL - result,
                "hasRaceCondition", false,
                "timeMs", elapsed
        );
    }

    /**
     * Решение через ReentrantLock.
     * Ожидаемый результат: ровно 50,000.
     */
    public Map<String, Object> demonstrateLockSolution() {
        lockCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        log.info("Starting REENTRANT LOCK solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementWithLock));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = lockCounter;
        log.info("ReentrantLock result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                "description", "Solution with ReentrantLock",
                "threads", THREAD_COUNT,
                "incrementsPerThread", INCREMENTS_PER_THREAD,
                "expectedTotal", EXPECTED_TOTAL,
                "actualResult", result,
                "lostUpdates", EXPECTED_TOTAL - result,
                "hasRaceCondition", false,
                "timeMs", elapsed
        );
    }

    /**
     * Комплексный тест: все 4 подхода в одном запросе.
     */
    public Map<String, Object> runFullComparison() {
        Map<String, Object> unsafe = demonstrateRaceCondition();
        Map<String, Object> sync = demonstrateSynchronizedSolution();
        Map<String, Object> atomic = demonstrateAtomicSolution();
        Map<String, Object> lock = demonstrateLockSolution();

        return Map.of(
                "unsafe", unsafe,
                "synchronized", sync,
                "atomic", atomic,
                "lock", lock,
                "summary", Map.of(
                        "raceConditionDemonstrated", (Boolean) unsafe.get("hasRaceCondition"),
                        "allSolutionsCorrect", !(Boolean) sync.get("hasRaceCondition")
                                && !(Boolean) atomic.get("hasRaceCondition")
                                && !(Boolean) lock.get("hasRaceCondition")
                )
        );
    }

    // --- Методы инкремента ---

    private void incrementUnsafe() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            long current = unsafeCounter;
            // Имитация race condition: небольшая задержка между чтением и записью
            unsafeCounter = current + 1;
        }
    }

    private void incrementSynchronized() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            synchronized (syncLock) {
                synchronizedCounter++;
            }
        }
    }

    private void incrementAtomic() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            atomicCounter.incrementAndGet();
        }
    }

    private void incrementWithLock() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            lock.lock();
            try {
                lockCounter++;
            } finally {
                lock.unlock();
            }
        }
    }

    private void waitForCompletion(List<Future<?>> futures, ExecutorService executor) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                log.error("Task failed", e);
                Thread.currentThread().interrupt();
            }
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
