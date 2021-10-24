package ru.erminson.multithreading.synchronized_volatile;

import lombok.extern.slf4j.Slf4j;

/**
 * Volatile - запрещает кэшировать переменную.
 * Переменная не сохраняется в локальную память потока.
 */

@Slf4j
public class AppVolatile {
    public static void main(String[] args) {
        TimerVolatile timer = new TimerVolatile();
        timer.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("Timer value: {}", timer.getValue());
        timer.stopTimer();
        try {
            timer.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("Timer value: {}", timer.getValue());
    }
}
