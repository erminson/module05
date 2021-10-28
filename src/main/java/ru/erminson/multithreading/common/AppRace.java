package ru.erminson.multithreading.common;

import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.*;

@Slf4j
public class AppRace {
    private static final int CARS_COUNT_IN_TUNNEL = 3;
    private static final int CARS_COUNT = 10;

    private static final ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);

    private static final Semaphore tunnelSemaphore = new Semaphore(CARS_COUNT_IN_TUNNEL);
    private static final CyclicBarrier prepareCyclicBarrier = new CyclicBarrier(CARS_COUNT);
    private static final CountDownLatch finishCountDownLatch = new CountDownLatch(CARS_COUNT);

    private static final Map<Integer, Long> score = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        for (int i = 0; i < CARS_COUNT; i++) {
            final int index = i;
            executorService.execute(() -> {
                prepare(index);
                try {
                    prepareCyclicBarrier.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    Thread.currentThread().interrupt();
                } catch (BrokenBarrierException e) {
                    log.error(e.getMessage());
                }

                long before = System.currentTimeMillis();
                roadFirst(index);
                tunnel(index);
                roadSecond(index);

                long after = System.currentTimeMillis();
                score.put(index, after - before);

                finishCountDownLatch.countDown();
            });
        }

        executorService.shutdown();

        try {
            finishCountDownLatch.await();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        Map.Entry<Integer, Long> maxEntry = Collections.max(
                score.entrySet(), (e1, e2) -> e2.getValue().compareTo(e1.getValue())
        );

        log.info("Winner: {}, Time: {}", maxEntry.getKey(), maxEntry.getValue());

        for (Map.Entry<Integer, Long> entry : score.entrySet()) {
            log.info("{} - {}", entry.getKey(), entry.getValue());
        }
    }

    private static void sleepRandomTime() {
        long millis = (long) (Math.random() * 5000 + 1000);
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private static void prepare(int index) {
        log.info("{} preparing started", index);
        sleepRandomTime();
        log.info("{} preparing finished", index);
    }

    private static void roadFirst(int index) {
        log.info("{} roadFirst started", index);
        sleepRandomTime();
        log.info("{} roadFirst finished", index);
    }

    private static void roadSecond(int index) {
        log.info("{} roadSecond started", index);
        sleepRandomTime();
        log.info("{} roadSecond finished", index);
    }

    private static void tunnel(int index) {
        try {
            tunnelSemaphore.acquire();
            log.info("{} tunnel started", index);
            sleepRandomTime();
            log.info("{} tunnel finished", index);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            tunnelSemaphore.release();
        }
    }
}
