package ru.erminson.multithreading.thread_states;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppStates {
    public static void main(String[] args) {
        // State: NEW
        Thread thread = new Thread(() ->
                log.info("State: {}", Thread.currentThread().getState().name())
        );
        log.info("State: {}", thread.getState().name());

        // State: RUNNABLE
        thread.start();
        log.info("State: {}", thread.getState().name());
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }
        log.info("State: {}", thread.getState().name());
    }
}


