package com.matuageorge.webapp;

public class Deadlock {
    public static void main(String[] args) {
        A a = new A();
        B b = new B();

        lockingThread(a, b);

        lockingThread(b, a);
    }

    private static void lockingThread(Object a, Object b) {
        new Thread(() -> {
            synchronized (a) {
                System.out.format("%s locking %s%n", Thread.currentThread().getName(), a.getClass().getSimpleName());
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.format("%s waiting to lock %s%n", Thread.currentThread().getName(), b.getClass().getSimpleName());
                synchronized (b) {
                    System.out.format("%s locking %s and %s%n", Thread.currentThread().getName(), a.getClass().getSimpleName(), b.getClass().getSimpleName());
                }
            }
        }).start();
    }

    static class A {
    }

    static class B {
    }
}




