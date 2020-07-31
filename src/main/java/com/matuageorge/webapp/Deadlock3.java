package com.matuageorge.webapp;

import static java.lang.Math.random;

public class Deadlock3 {
    public static void main(String[] args) throws InterruptedException {
        A a = new A();
        B b = new B();

        a.writeToA((int) (random() * 10));
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                try {
                    a.writeToA((int) (random() * 10));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                a.aWriteToB((int) (random() * 10), b);
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 100; i++) {
                b.bWriteToA((int) (random() * 10), a);
                b.writeToB((int) (random() * 10));
            }
        });

        thread1.start();
        thread2.start();

        System.out.println(a.a);
        System.out.println(b.b);

    }

    static class A {
        private int a;

        private synchronized void writeToA(int a) throws InterruptedException {
            Thread.sleep(500);
            this.a = a;
        }

        private synchronized void aWriteToB(int b, B bClass) {
            bClass.b = b;
        }


    }

    static class B {
        private int b;

        private synchronized void writeToB(int b) {
            this.b = b;
        }

        private synchronized void bWriteToA(int a, A aClass) {
            aClass.a = a;
        }
    }
}




