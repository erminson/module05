package ru.erminson.multithreading.synchronized_volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppAtmWithJoin {
    public static void main(String[] args) {
        AtmWithoutSynchronized atm = new AtmWithoutSynchronized(1000);

        Thread client1 = new Thread(() -> atm.withdraw("Client1", 300));
        Thread client2 = new Thread(() -> {
            try {
                client1.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
            atm.withdraw("Client2", 500);
        });
        Thread client3 = new Thread(() -> {
            try {
                client2.join();
            } catch (InterruptedException e) {
                log.error(e.getMessage());
                Thread.currentThread().interrupt();
            }
            atm.withdraw("Client3", 400);
        });

        client1.start();
        client2.start();
        client3.start();

        try {
            client1.join();
            client2.join();
            client3.join();
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        log.info("Left in the ATM: " + atm.getAmount());
    }
}
