package ru.erminson.multithreading.atomic;

import java.util.concurrent.atomic.AtomicInteger;

public class CounterAtomic {
    private AtomicInteger value = new AtomicInteger(0);

    public void increment() {
        value.incrementAndGet();
    }

    public void decrement() {
        value.decrementAndGet();
    }

    public int getValue() {
        return value.intValue();
    }
}
