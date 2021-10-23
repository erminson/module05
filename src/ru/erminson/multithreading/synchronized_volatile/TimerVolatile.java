package ru.erminson.multithreading.synchronized_volatile;

public class TimerVolatile extends Thread {
    private volatile long value = 0;
    private boolean isStopped = false;

    @Override
    public void run() {
        while (!isStopped) {
            value += 1;
        }
    }

    public void stopTimer() {
        isStopped = true;
    }

    public long getValue() {
        return value;
    }
}
