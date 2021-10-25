package ru.erminson.multithreading.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class CounterLock {
    private final Lock lock = new ReentrantLock();
    private int value = 0;

    public void increment() {
        lock.lock();
        try {
            Thread.sleep(50);
            value++;
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void decrement() {
        lock.lock();
        try {
            value--;
            Thread.sleep(50);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public int getValue() {
        return value;
    }
}
