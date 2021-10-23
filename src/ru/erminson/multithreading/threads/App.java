package ru.erminson.multithreading.threads;

/**
 * Как создать новый поток
 *
* */
public class App {
    public static void main(String[] args) {
        // #1
        Thread myThread1 = new MyThread("MyThread1", 100);
        myThread1.start();

        // #2
        MyRunnable myRunnable = new MyRunnable("MyThread2", 100);
        Thread myThread2 = new Thread(myRunnable);
        myThread2.start();
    }
}
