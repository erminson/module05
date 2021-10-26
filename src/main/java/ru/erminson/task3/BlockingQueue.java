package ru.erminson.task3;

public interface BlockingQueue<T> {
    void put(T item);

    T get();

    int size();
}
