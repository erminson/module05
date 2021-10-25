package ru.erminson.multithreading.mutex;

import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

@Slf4j
public class AppMutex {
    public static void main(String[] args) {
        SequenceGenerator sequenceGenerator = new SequenceGenerator();
        SequenceGenerator sequenceGeneratorSynchronized = new SequenceGeneratorSynchronized();

        Set<Integer> set1 = new HashSet<>();
        Set<Integer> set2 = new HashSet<>();

        int count = 100;
        Thread thread1 = new Thread(() -> {
           for (int i = 0; i < count; i++) {
               try {
                   Thread.sleep(20);
               } catch (InterruptedException e) {
                   log.error(e.getMessage());
                   Thread.currentThread().interrupt();
               }
               set1.add(sequenceGenerator.getNewSequence());
               set2.add(sequenceGeneratorSynchronized.getNewSequence());
           }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < count; i++) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    log.error(e.getMessage());
                    Thread.currentThread().interrupt();
                }
                set1.add(sequenceGenerator.getNewSequence());
                set2.add(sequenceGeneratorSynchronized.getNewSequence());
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("List1 count: {}", set1.size());
        log.info("List1 count: {}", set2.size());
    }
}
