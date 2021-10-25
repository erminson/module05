package ru.erminson.multithreading.wait_notify_sleep_join;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppWaitNotify {
    public static void main(String[] args) {
        Market market = new Market();

        Producer producer = new Producer(10, market);
        Consumer consumer = new Consumer(10, market);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();

        try {
            producerThread.join();
            consumerThread.join();
            log.info("Threads worked. Main thread sleep for 2 seconds.");
            Thread.sleep(2000);
            log.info("Main thread wake up.");
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("End of main thread");
    }
}
