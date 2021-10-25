package ru.erminson.multithreading.wait_notify;

public class AppWaitNotify {
    public static void main(String[] args) {
        Market market = new Market();

        Producer producer = new Producer(10, market);
        Consumer consumer = new Consumer(10, market);

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        producerThread.start();
        consumerThread.start();
    }
}
