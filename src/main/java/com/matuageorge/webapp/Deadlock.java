package com.matuageorge.webapp;

public class Deadlock {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        new Thread(() -> {
            synchronized (a) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (b) {

                }
            }
        }).start();

        new Thread(() -> {
            synchronized (b) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (a) {

                }
            }
        }).start();
    }

    static class A {
    }

    static class B {
    }
}




