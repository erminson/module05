package ru.erminson.multithreading.wait_notify_sleep_join;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Market {
    private static final int MAX_COUNT_BREAD = 5;
    private int breadCount = 0;

    public synchronized void getBread() {
        while (breadCount < 1) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        breadCount--;
        log.info("Consumer get 1 bread");
        log.info("Bread count: {}", breadCount);
        notify();
    }

    public synchronized void putBread() {
        while (breadCount >= MAX_COUNT_BREAD) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        breadCount++;
        log.info("Producer put 1 bread");
        log.info("Bread count: {}", breadCount);
        notify();
    }
}
