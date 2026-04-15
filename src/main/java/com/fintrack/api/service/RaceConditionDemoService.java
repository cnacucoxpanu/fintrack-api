package com.fintrack.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class RaceConditionDemoService {

    private static final Logger LOG = LoggerFactory.getLogger(RaceConditionDemoService.class);
    private static final int THREAD_COUNT = 50;
    private static final int INCREMENTS_PER_THREAD = 1000;
    private static final int EXPECTED_TOTAL = THREAD_COUNT * INCREMENTS_PER_THREAD;

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_THREADS = "threads";
    private static final String KEY_INCREMENTS = "incrementsPerThread";
    private static final String KEY_EXPECTED = "expectedTotal";
    private static final String KEY_ACTUAL = "actualResult";
    private static final String KEY_LOST = "lostUpdates";
    private static final String KEY_RACE = "hasRaceCondition";
    private static final String KEY_TIME = "timeMs";

    private long unsafeCounter = 0;

    private long synchronizedCounter = 0;
    private final Object syncLock = new Object();

    private final AtomicLong atomicCounter = new AtomicLong(0);

    private long lockCounter = 0;
    private final ReentrantLock reentrantLock = new ReentrantLock();

    public Map<String, Object> demonstrateRaceCondition() {
        unsafeCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        LOG.warn("Starting RACE CONDITION demo with {} threads, {} increments each",
                THREAD_COUNT, INCREMENTS_PER_THREAD);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementUnsafe));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long actualResult = unsafeCounter;
        long lostUpdates = EXPECTED_TOTAL - actualResult;

        LOG.warn("Race condition result: {} (expected {}). Lost updates: {}",
                actualResult, EXPECTED_TOTAL, lostUpdates);

        return Map.of(
                KEY_DESCRIPTION, "Race Condition (без синхронизации)",
                KEY_THREADS, THREAD_COUNT,
                KEY_INCREMENTS, INCREMENTS_PER_THREAD,
                KEY_EXPECTED, EXPECTED_TOTAL,
                KEY_ACTUAL, actualResult,
                KEY_LOST, lostUpdates,
                KEY_RACE, lostUpdates > 0,
                KEY_TIME, elapsed
        );
    }

    public Map<String, Object> demonstrateSynchronizedSolution() {
        synchronizedCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        LOG.info("Starting SYNCHRONIZED solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementSynchronized));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = synchronizedCounter;
        LOG.info("Synchronized result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                KEY_DESCRIPTION, "Solution with synchronized",
                KEY_THREADS, THREAD_COUNT,
                KEY_INCREMENTS, INCREMENTS_PER_THREAD,
                KEY_EXPECTED, EXPECTED_TOTAL,
                KEY_ACTUAL, result,
                KEY_LOST, EXPECTED_TOTAL - result,
                KEY_RACE, false,
                KEY_TIME, elapsed
        );
    }

    public Map<String, Object> demonstrateAtomicSolution() {
        atomicCounter.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        LOG.info("Starting ATOMIC solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementAtomic));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = atomicCounter.get();
        LOG.info("Atomic result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                KEY_DESCRIPTION, "Solution with AtomicLong",
                KEY_THREADS, THREAD_COUNT,
                KEY_INCREMENTS, INCREMENTS_PER_THREAD,
                KEY_EXPECTED, EXPECTED_TOTAL,
                KEY_ACTUAL, result,
                KEY_LOST, EXPECTED_TOTAL - result,
                KEY_RACE, false,
                KEY_TIME, elapsed
        );
    }

    public Map<String, Object> demonstrateLockSolution() {
        lockCounter = 0;
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        List<Future<?>> futures = new ArrayList<>();

        long startTime = System.currentTimeMillis();
        LOG.info("Starting REENTRANT LOCK solution with {} threads", THREAD_COUNT);

        for (int i = 0; i < THREAD_COUNT; i++) {
            futures.add(executor.submit(this::incrementWithLock));
        }

        waitForCompletion(futures, executor);
        long elapsed = System.currentTimeMillis() - startTime;

        long result = lockCounter;
        LOG.info("ReentrantLock result: {} (expected {})", result, EXPECTED_TOTAL);

        return Map.of(
                KEY_DESCRIPTION, "Solution with ReentrantLock",
                KEY_THREADS, THREAD_COUNT,
                KEY_INCREMENTS, INCREMENTS_PER_THREAD,
                KEY_EXPECTED, EXPECTED_TOTAL,
                KEY_ACTUAL, result,
                KEY_LOST, EXPECTED_TOTAL - result,
                KEY_RACE, false,
                KEY_TIME, elapsed
        );
    }

    public Map<String, Object> runFullComparison() {
        Map<String, Object> unsafe = demonstrateRaceCondition();
        Map<String, Object> sync = demonstrateSynchronizedSolution();
        Map<String, Object> atomic = demonstrateAtomicSolution();
        Map<String, Object> lockResult = demonstrateLockSolution();

        boolean raceConditionDemonstrated = (Boolean) unsafe.get(KEY_RACE);
        boolean syncCorrect = !(Boolean) sync.get(KEY_RACE);
        boolean atomicCorrect = !(Boolean) atomic.get(KEY_RACE);
        boolean lockCorrect = !(Boolean) lockResult.get(KEY_RACE);

        return Map.of(
                "unsafe", unsafe,
                "synchronized", sync,
                "atomic", atomic,
                "lock", lockResult,
                "summary", Map.of(
                        "raceConditionDemonstrated", raceConditionDemonstrated,
                        "allSolutionsCorrect", syncCorrect && atomicCorrect && lockCorrect
                )
        );
    }

    private void incrementUnsafe() {
        for (int i = 0; i < INCREMENTS_PER_THREAD; i++) {
            long current = unsafeCounter;
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
            reentrantLock.lock();
            try {
                lockCounter++;
            } finally {
                reentrantLock.unlock();
            }
        }
    }

    private void waitForCompletion(List<Future<?>> futures, ExecutorService executor) {
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                LOG.error("Task failed", e);
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
