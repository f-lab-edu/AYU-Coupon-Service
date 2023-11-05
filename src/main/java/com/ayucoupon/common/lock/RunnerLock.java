package com.ayucoupon.common.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RunnerLock {

    private final Lock lock;
    private int count;

    public RunnerLock() {
        this.lock = new ReentrantLock(true);
        this.count = 0;
    }

    public boolean tryLock() {
        return lock.tryLock();
    }

    public boolean tryLock(Long time, TimeUnit unit) throws InterruptedException {
        return lock.tryLock(time, unit);
    }

    public void unLock() {
        lock.unlock();
    }

    public int increase() {
        return ++count;
    }

    public int decrease() {
        return --count;
    }

}
