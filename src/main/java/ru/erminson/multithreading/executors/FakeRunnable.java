package ru.erminson.multithreading.executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FakeRunnable implements Runnable {
    private static final int MIN_TIME = 100;
    private static final int MAX_TIME = 900;

    private final String name;

    public FakeRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        log.info("'{}' start", name);
        try {
            Thread.sleep((int) ((Math.random() * (MAX_TIME - MIN_TIME)) + MIN_TIME));
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        log.info("'{}' end", name);
    }
}
