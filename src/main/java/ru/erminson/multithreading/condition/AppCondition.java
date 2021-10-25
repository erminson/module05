package ru.erminson.multithreading.condition;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppCondition {
    public static void main(String[] args) {
        Store store = new Store();
        int count = 6;

        Producer producer = new Producer(store, count);
        Consumer consumer = new Consumer(store, count);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("Main thread end.");
    }
}
