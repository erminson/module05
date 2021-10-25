package ru.erminson.multithreading.mutex;

public class SequenceGeneratorSynchronized extends SequenceGenerator {
    private final Object mutex = new Object();

    @Override
    public int getNewSequence() {
        synchronized (mutex) {
            return super.getNewSequence();
        }
    }
}
