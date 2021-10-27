package ru.erminson.task3;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
class BlockingQueueTest {
    private static final int COUNT = 10;
    private static final int AMOUNT_THREADS = 5;
    private static final int BLOCKING_QUEUE_CAPACITY = 3;

    @Test
    void testBlockingQueueLock() {
        Set<Integer> expectedSet = new LinkedHashSet<>(getIntegerList(COUNT));
        BlockingQueue<Integer> blockingQueue = new BlockingQueueLock<>(BLOCKING_QUEUE_CAPACITY);
        Set<Integer> actualSet = getUniqueSequences(blockingQueue);

        Assertions.assertEquals(expectedSet, actualSet);
    }

    @Test
    void testBlockingQueueWaitNotify() {
        Set<Integer> expectedSet = new LinkedHashSet<>(getIntegerList(COUNT));
        BlockingQueue<Integer> blockingQueue = new BlockingQueueWaitNotify<>(3);
        Set<Integer> actualSet = getUniqueSequences(blockingQueue);

        Assertions.assertEquals(expectedSet, actualSet);
    }

    private Set<Integer> getUniqueSequences(BlockingQueue<Integer> blockingQueue) {
        final ExecutorService producerExecutorService = Executors.newFixedThreadPool(BlockingQueueTest.AMOUNT_THREADS);
        final ExecutorService consumerExecutorService = Executors.newFixedThreadPool(BlockingQueueTest.AMOUNT_THREADS);

        List<Runnable> producerRunnableList = getProducerRunnableList(BlockingQueueTest.COUNT, 1000, blockingQueue);
        List<Callable<Integer>> consumerCallableList = getConsumerCallableList(BlockingQueueTest.COUNT, 3000, blockingQueue);

        List<Future<Integer>> futures = null;
        try {
            for (Runnable runnable : producerRunnableList) {
                producerExecutorService.execute(runnable);
            }
            futures = consumerExecutorService.invokeAll(consumerCallableList);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        producerExecutorService.shutdown();
        consumerExecutorService.shutdown();

        Set<Integer> uniqueSequences = new LinkedHashSet<>();
        try {
            assert futures != null;
            for (Future<Integer> future : futures) {
                uniqueSequences.add(future.get());
            }
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            log.error(e.getMessage());
        }

        log.debug(String.valueOf(uniqueSequences.size()));
        log.debug(uniqueSequences.toString());
        log.debug(blockingQueue.toString());

        try {
            producerExecutorService.awaitTermination(20, TimeUnit.SECONDS);
            consumerExecutorService.awaitTermination(20, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        return uniqueSequences;
    }

    private List<Integer> getIntegerList(int count) {
        return IntStream.range(0, count).boxed().collect(Collectors.toList());
    }

    private List<Runnable> getProducerRunnableList(int count, int delay, BlockingQueue<Integer> blockingQueue) {
        return getIntegerList(count).stream()
                .map(i -> (Runnable) () -> {
                    try {
                        Thread.sleep(delay);
                        blockingQueue.put(i);
                    } catch (InterruptedException e) {
                        log.error(e.getMessage());
                        Thread.currentThread().interrupt();
                    }
                })
                .collect(Collectors.toList());
    }

    private List<Callable<Integer>> getConsumerCallableList(int count, int delay, BlockingQueue<Integer> blockingQueue) {
        return getIntegerList(count).stream()
                .map(i -> (Callable<Integer>) () -> {
                    Thread.sleep(delay);
                    return blockingQueue.get();
                })
                .collect(Collectors.toList());
    }
}