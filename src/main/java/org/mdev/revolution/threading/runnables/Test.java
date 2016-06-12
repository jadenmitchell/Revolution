package org.mdev.revolution.threading.runnables;

public class Test implements Runnable {
    @Override
    public void run() {
        System.out.println("Test currently running on thread: " + Thread.currentThread().getName());
    }
}
