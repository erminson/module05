package ru.erminson.multithreading.mutex;

public class SequenceGenerator {
    private int currentValue = 0;

    public int getNewSequence() {
        return ++currentValue;
    }

    public int getValue() {
        return currentValue;
    }
}
