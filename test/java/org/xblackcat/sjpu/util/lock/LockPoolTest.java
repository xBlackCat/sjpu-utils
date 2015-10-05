package org.xblackcat.sjpu.util.lock;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.locks.Lock;

/**
 * 28.09.2015 11:20
 *
 * @author xBlackCat
 */
public class LockPoolTest {
    @Test
    public void simpleTest() {
        LockPool<Integer> pool = new LockPool<>();

        Assert.assertEquals(0, pool.lockPool.size());

        final Lock lock = pool.getLock(10);
        Assert.assertEquals(0, pool.lockPool.size());

        lock.lock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(10).requested.get());

        lock.unlock();
        Assert.assertEquals(0, pool.lockPool.size());
    }

    @Test
    public void extendedTest() {
        LockPool<Integer> pool = new LockPool<>();

        Assert.assertEquals(0, pool.lockPool.size());

        final Lock lock = pool.getLock(10);
        Assert.assertEquals(0, pool.lockPool.size());

        lock.lock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(10).requested.get());

        lock.lock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(2, pool.lockPool.get(10).requested.get());

        lock.unlock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(10).requested.get());

        lock.unlock();
        Assert.assertEquals(0, pool.lockPool.size());
    }

    @Test
    public void multiLockTest() {
        LockPool<Integer> pool = new LockPool<>();

        Assert.assertEquals(0, pool.lockPool.size());

        final Lock lock1 = pool.getLock(10);
        final Lock lock2 = pool.getLock(20);
        Assert.assertEquals(0, pool.lockPool.size());

        lock1.lock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(10).requested.get());

        lock2.lock();
        Assert.assertEquals(2, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(10).requested.get());
        Assert.assertEquals(1, pool.lockPool.get(20).requested.get());

        lock1.unlock();
        Assert.assertEquals(1, pool.lockPool.size());
        Assert.assertEquals(1, pool.lockPool.get(20).requested.get());

        lock2.unlock();
        Assert.assertEquals(0, pool.lockPool.size());
    }

    @Test
    public void multiThreadTest() {

    }

    private final class TestThread implements Runnable {
        @Override
        public void run() {

        }
    }
}
