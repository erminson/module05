package ru.erminson.multithreading.synchronized_volatile;

public class AppVolatile {
    public static void main(String[] args) {
        TimerVolatile timer = new TimerVolatile();
        timer.start();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Timer value: " + timer.getValue());
        timer.stopTimer();
        try {
            timer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }

        System.out.println("Timer value: " + timer.getValue());
    }
}
