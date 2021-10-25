package ru.erminson.multithreading.condition;

public class Producer implements Runnable {
    private final Store store;
    private final int count;

    public Producer(Store store, int count) {
        this.store = store;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            store.put();
        }
    }
}
