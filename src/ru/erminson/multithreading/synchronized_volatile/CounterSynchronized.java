package ru.erminson.multithreading.synchronized_volatile;

public class CounterSynchronized {
    private int value = 0;

    public synchronized void increment() {
        value++;
    }

    public synchronized void decrement() {
        value--;
    }

    public int getValue() {
        return value;
    }

}
