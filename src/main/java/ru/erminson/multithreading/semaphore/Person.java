package ru.erminson.multithreading.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

@Slf4j
public class Person implements Runnable {
    private static final int MIN_TIME = 1000;
    private static final int MAX_TIME = 2000;

    public final String name;
    public final Semaphore callBox;

    public Person(String name, Semaphore callBox) {
        this.name = name;
        this.callBox = callBox;
    }

    @Override
    public void run() {
        try {
            log.info("{} near the call box", name);
            callBox.acquire();
            log.info("{} started the call", name);
            Thread.sleep((int) ((Math.random() * (MAX_TIME - MIN_TIME)) + MIN_TIME));
            log.info("{} ended the call", name);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            callBox.release();
        }
    }
}
