package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Producer implements Runnable {
    private final BlockingQueue<Integer> blockingQueue;
    private final int count;
    private final int time;

    public Producer(BlockingQueue<Integer> blockingQueue, int count, int time) {
        this.blockingQueue = blockingQueue;
        this.count = count;
        this.time = time;
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                blockingQueue.put(i);
                log.info("+Put: {}", i);
                Thread.sleep(time);
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
    }
}
