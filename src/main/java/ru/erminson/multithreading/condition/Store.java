package ru.erminson.multithreading.condition;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class Store {
    private static final int MAX_PRODUCTS = 3;
    private final Lock lock;
    private final Condition condition;
    private int products = 0;

    public Store() {
        lock = new ReentrantLock();
        condition = lock.newCondition();
    }

    public void get() {
        lock.lock();
        try {
            while (products < 1) {
                condition.await();
            }

            products--;
            log.info("Get 1 product");
            log.info("Products count: {}", products);

            condition.signalAll();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void put() {
        lock.lock();
        try {
            while (products >= MAX_PRODUCTS) {
                condition.await();
            }

            products++;
            log.info("Put 1 product");
            log.info("Products count: {}", products);

            condition.signalAll();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }
}
