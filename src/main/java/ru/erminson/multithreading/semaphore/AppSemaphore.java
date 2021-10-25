package ru.erminson.multithreading.semaphore;

import java.util.concurrent.Semaphore;

public class AppSemaphore {
    public static void main(String[] args) {
        Semaphore callBox = new Semaphore(2);

        new Thread(new Person("Person1", callBox)).start();
        new Thread(new Person("Person2", callBox)).start();
        new Thread(new Person("Person3", callBox)).start();
        new Thread(new Person("Person4", callBox)).start();
        new Thread(new Person("Person5", callBox)).start();
        new Thread(new Person("Person6", callBox)).start();
    }
}
