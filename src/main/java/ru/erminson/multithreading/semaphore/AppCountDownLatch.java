package ru.erminson.multithreading.semaphore;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;

@Slf4j
public class AppCountDownLatch {
    private static final int MIN_TIME = 1000;
    private static final int MAX_TIME = 3000;

    private final CountDownLatch countDownLatch;

    public AppCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void lock1() {
        try {
            log.info("Lock1 began to open");
            Thread.sleep(getRandomTime());
            countDownLatch.countDown();
            log.info("Lock1 opened. Count: {}", countDownLatch.getCount());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void lock2() {
        try {
            log.info("Lock2 began to open");
            Thread.sleep(getRandomTime());
            countDownLatch.countDown();
            log.info("Lock2 opened. Count: {}", countDownLatch.getCount());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    public void lock3() {
        try {
            log.info("Lock3 began to open");
            Thread.sleep(getRandomTime());
            countDownLatch.countDown();
            log.info("Lock3 opened. Count: {}", countDownLatch.getCount());
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }

    private int getRandomTime() {
        return (int) ((Math.random() * (MAX_TIME - MIN_TIME)) + MIN_TIME);
    }

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(3);
        AppCountDownLatch app = new AppCountDownLatch(countDownLatch);

        new Thread(new PersonCountDownLatch("Person1", countDownLatch)).start();
        new Thread(new PersonCountDownLatch("Person2", countDownLatch)).start();
        new Thread(new PersonCountDownLatch("Person3", countDownLatch)).start();
        new Thread(new PersonCountDownLatch("Person4", countDownLatch)).start();
        new Thread(new PersonCountDownLatch("Person5", countDownLatch)).start();

        app.lock1();
        app.lock2();
        app.lock3();
    }
}
