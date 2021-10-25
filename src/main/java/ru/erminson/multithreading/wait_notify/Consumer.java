package ru.erminson.multithreading.wait_notify;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer implements Runnable {
    private final int count;
    private final Market market;

    public Consumer(int count, Market market) {
        this.count = count;
        this.market = market;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
            market.getBread();
        }
    }
}
