package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Consumer implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;
    private final int count;
    private final int frequency;

    public Consumer(BlockingQueue<Integer> blockingQueue, int count, int frequency) {
        this.blockingQueue = blockingQueue;
        this.count = count;
        this.frequency = frequency;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                Thread.sleep(frequency);
                Integer item = blockingQueue.get();
                log.info("-Get: {}", item);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
