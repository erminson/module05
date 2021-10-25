package ru.erminson.multithreading.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class PersonCountDownLatch implements Runnable {
    private final String name;
    private final CountDownLatch countDownLatch;

    public PersonCountDownLatch(String name, CountDownLatch countDownLatch) {
        this.name = name;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public void run() {
        try {
            log.info("{} is waiting for the opening of the locks", name);
            countDownLatch.await();
            log.info("{} came in", name);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
