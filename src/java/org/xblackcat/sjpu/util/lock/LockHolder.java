package org.xblackcat.sjpu.util.lock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * 26.09.2015 10:09
 *
 * @author xBlackCat
 */
class LockHolder {
    private final AtomicInteger requested = new AtomicInteger(0);
    private final Lock lock;

    LockHolder(Lock lock) {
        this.lock = lock;
    }

    public Lock getLock() {
        return lock;
    }

    public void increment() {
        requested.incrementAndGet();
    }

    public boolean decrement() {
        return 0 == requested.decrementAndGet();
    }
}
