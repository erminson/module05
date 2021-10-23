package ru.erminson.multithreading.synchronized_volatile;

public class AppSynchronized {
    public static void main(String[] args) {
        CounterSynchronized counter = new CounterSynchronized();
        int count = 1000;

        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
           for (int i = 0; i < count; i++) {
               counter.decrement();
           }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println("Counter value: " + counter.getValue());
    }
}
