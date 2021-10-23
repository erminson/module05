package ru.erminson.multithreading.threads;

public class MyThread extends Thread {
    private final String title;
    private final int count;

    public MyThread(String title, int count) {
        this.title = title;
        this.count = count;
    }

    @Override
    public void run() {
        System.out.println(title);
        for (int i = 0; i < count; i++) {
            System.out.print(i + " ");
        }
    }
}
