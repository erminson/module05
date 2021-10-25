package ru.erminson.multithreading.lock;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppLock {
    public static void main(String[] args) {
        CounterLock counter = new CounterLock();
        int count = 10;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                counter.decrement();
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("Counter value: {}", counter.getValue());

    }
}
