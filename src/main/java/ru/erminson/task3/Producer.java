package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;
    private final int count;
    private final int frequency;

    public Producer(BlockingQueue<Integer> blockingQueue, int count, int frequency) {
        this.blockingQueue = blockingQueue;
        this.count = count;
        this.frequency = frequency;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                Thread.sleep(frequency);
                blockingQueue.put(i);
                log.info("+Put: {}", i);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
