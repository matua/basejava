package com.matuageorge.webapp;

public class Deadlock {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        Thread thread1 = new Thread(() -> {
            synchronized (a) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {

                }
            }
        });

        Thread thread2 = new Thread(() -> {
            synchronized (b) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {

                }
            }
        });

        thread1.start();
        thread2.start();
    }

    static class A {
    }

    static class B {
    }
}




