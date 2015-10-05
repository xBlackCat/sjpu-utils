package org.xblackcat.sjpu.util.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
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

    private class LockWrapper implements Lock {
        private final ID key;

        public LockWrapper(ID key) {
            this.key = key;
        }

        @Override
        public void lock() {
            LockHolder lockHolder = useLockFromPool();
            lockHolder.getLock().lock();
        }

        @Override
        public void lockInterruptibly() throws InterruptedException {
            LockHolder lockHolder = useLockFromPool();
            lockHolder.getLock().lockInterruptibly();
        }

        @Override
        public boolean tryLock() {
            LockHolder lockHolder = useLockFromPool();
            return lockHolder.getLock().tryLock();
        }

        @Override
        public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
            LockHolder lockHolder = useLockFromPool();
            return lockHolder.getLock().tryLock(time, unit);
        }

        @Override
        public void unlock() {
            LockHolder lockHolder = getLockFromPool();

            lockHolder.getLock().unlock();
            if (lockHolder.decrement()) {
                removeLockFromPool();
            }
        }

        @Override
        public Condition newCondition() {
            LockHolder lockHolder = getLockFromPool();
            return lockHolder.getLock().newCondition();
        }

        private LockHolder getLockFromPool() {
            poolLock.lock();
            try {
                return lockPool.computeIfAbsent(key, id -> new LockHolder(lockProvider.apply(id)));
            } finally {
                poolLock.unlock();
            }
        }

        private LockHolder useLockFromPool() {
            LockHolder lockHolder = getLockFromPool();

            lockHolder.increment();
            return lockHolder;
        }

        private void removeLockFromPool() {
            poolLock.lock();
            try {
                if (lockPool.remove(key) == null) {
                    throw new IllegalStateException("Lock '" + key + "' was removed lately");
                }
            } finally {
                poolLock.unlock();
            }
        }

    }

    /**
     * 26.09.2015 10:09
     *
     * @author xBlackCat
     */
    protected static final class LockHolder {
        protected final AtomicInteger requested = new AtomicInteger(0);
        protected final Lock lock;

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
}
