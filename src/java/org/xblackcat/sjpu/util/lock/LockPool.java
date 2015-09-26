package org.xblackcat.sjpu.util.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * 26.09.2015 9:56
 *
 * @author xBlackCat
 */
public class LockPool<ID> implements ILockPool<ID> {
    protected final Function<ID, Lock> lockProvider;
    protected final Lock poolLock = new ReentrantLock();
    protected final Map<ID, LockHolder> lockPool = new HashMap<>();

    public LockPool() {
        this((Supplier<Lock>) ReentrantLock::new);
    }

    public LockPool(Supplier<Lock> lockProvider) {
        this(id -> lockProvider.get());
    }

    public LockPool(Function<ID, Lock> lockProvider) {
        this.lockProvider = lockProvider;
    }

    @Override
    public Lock getLock(ID key) {
        return new LockWrapper(key);
    }

    private LockHolder getLockFromPool(ID key) {
        poolLock.lock();
        try {
            LockHolder lockHolder = lockPool.get(key);
            if (lockHolder == null) {
                lockHolder = new LockHolder(lockProvider.apply(key));
                lockPool.put(key, lockHolder);
            }
            return lockHolder;
        } finally {
            poolLock.unlock();
        }
    }

    private LockHolder useLockFromPool(ID key) {
        LockHolder lockHolder = getLockFromPool(key);

        lockHolder.increment();
        return lockHolder;
    }

    private void removeLockFromPool(ID key) {
        poolLock.lock();
        try {
            lockPool.remove(key);
        } finally {
            poolLock.unlock();
        }
    }

    private class LockWrapper implements Lock {
        private final ID key;

        public LockWrapper(ID key) {
            this.key = key;
        }

        @Override
        public void lock() {
            LockHolder lockHolder = useLockFromPool(key);
            lockHolder.getLock().lock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            LockHolder lockHolder = useLockFromPool(key);
            lockHolder.getLock().lockInterruptibly();
        }

        @Override
        public boolean tryLock() {
            LockHolder lockHolder = useLockFromPool(key);
            return lockHolder.getLock().tryLock();
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            LockHolder lockHolder = useLockFromPool(key);
            return lockHolder.getLock().tryLock(time, unit);
        }

        @Override
        public void unlock() {
            LockHolder lockHolder = getLockFromPool(key);

            lockHolder.getLock().unlock();
            if (lockHolder.decrement()) {
                removeLockFromPool(key);
            }
        }

        @Override
        public Condition newCondition() {
            LockHolder lockHolder = getLockFromPool(key);
            return lockHolder.getLock().newCondition();
        }
    }
}
