package ru.erminson.multithreading.threads;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MyRunnable implements Runnable {
    private final String title;
    private final int count;

    public MyRunnable(String title, int count) {
        this.title = title;
        this.count = count;
    }

    @Override
    public void run() {
        log.info(title);
        for (int i = 0; i < count; i++) {
            log.info("{}", i);
        }
    }
}
