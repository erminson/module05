package ru.erminson.multithreading.executors;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Slf4j
public class AppExecutors {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        try {
            for (int i = 0; i < 10; i++) {
                FakeRunnable runnable = new FakeRunnable("Fake #" + (i + 1));
                executorService.execute(runnable);
            }
        } finally {
            executorService.shutdown();
        }

        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        log.info("Main thread end.");
    }
}
