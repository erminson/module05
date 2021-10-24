package ru.erminson.multithreading.synchronized_volatile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AtmSynchronized implements Atm {
    private int amount;

    public AtmSynchronized(int amount) {
        this.amount = amount;
    }

    public synchronized int getAmount() {
        return amount;
    }

    public synchronized void withdraw(String name, int amount) {
        log.info(name + " went to the ATM");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
            Thread.currentThread().interrupt();
        }

        if (amount <= this.amount) {
            this.amount -= amount;
            log.info(name + " withdraw " + amount);
            log.info("After client " + name + " left in the ATM: " + this.amount);
        } else {
            log.warn("There is not enough money in the account for " + name);
        }
    }
}
