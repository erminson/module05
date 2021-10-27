package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.Queue;

@Slf4j
public class BlockingQueueWaitNotify<T> implements BlockingQueue<T> {
    private final int capacity;
    private final Queue<T> items = new LinkedList<>();

    public BlockingQueueWaitNotify(int capacity) {
        this.capacity = capacity;
    }

    @Override
    public synchronized void put(T item) {
        while (items.size() >= capacity) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        items.add(item);
        log.debug("Put: {}, Queue: {}", item, this);
        notifyAll();
    }

    @Override
    public synchronized T get() {
        while (items.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        T item = items.poll();
        log.debug("Get: {}, Queue: {}", item, this);
        notifyAll();
        return item;
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
