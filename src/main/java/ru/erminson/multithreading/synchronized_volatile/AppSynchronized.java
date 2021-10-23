package ru.erminson.multithreading.synchronized_volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppSynchronized {
    public static void main(String[] args) {
        CounterSynchronized counter = new CounterSynchronized();
        int count = 1000;

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
