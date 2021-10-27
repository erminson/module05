package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
public class BlockingQueueLock<T> implements BlockingQueue<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();

    private final Lock locker = new ReentrantLock();
    private final Condition fullCondition = locker.newCondition();
    private final Condition emptyCondition = locker.newCondition();

    public BlockingQueueLock(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public void put(T item) {
        locker.lock();
        try {
            while (items.size() >= capacity) {
                try {
                    fullCondition.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }

            items.add(item);
            log.debug("Put: {}, Queue: {}", item, this);
            emptyCondition.signalAll();
        } finally {
            locker.unlock();
        }
    }

    @Override
    public T get() {
        locker.lock();
        try {
            while (items.isEmpty()) {
                try {
                    emptyCondition.await();
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
            T item = items.poll();
            log.debug("Get: {}, Queue: {}", item, this);
            fullCondition.signal();
            return item;
        } finally {
            locker.unlock();
        }
    }

    @Override
    public int size() {
        return items.size();
    }

    @Override
    public String toString() {
        return items.toString();
    }
}
