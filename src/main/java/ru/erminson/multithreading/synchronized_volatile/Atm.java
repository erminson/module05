package ru.erminson.multithreading.synchronized_volatile;

public interface Atm {
    int getAmount();
    void withdraw(String name, int amount);
}
