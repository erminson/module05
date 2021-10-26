package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppBQWaitNotify {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new BlockingQueueWaitNotify<>(5);

        int count = 10;
        Producer producer = new Producer(blockingQueue, count, 1000);
        Consumer consumer = new Consumer(blockingQueue, count, 3000);

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

        log.info("items: {}", blockingQueue);
    }
}
