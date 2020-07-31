package com.matuageorge.webapp;

public class Deadlock {
    public static void main(String[] args) {
        final Thread[] threads = new Thread[2];

        threads[0] = new Thread(() -> {
            try {
                threads[0].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads[1] = new Thread(() -> {
            try {
                threads[1].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        threads[0].start();
        threads[1].start();
    }
}
