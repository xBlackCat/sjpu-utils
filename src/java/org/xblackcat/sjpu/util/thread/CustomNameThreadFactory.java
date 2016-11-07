package org.xblackcat.sjpu.util.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 29.09.2015 12:20
 *
 * @author xBlackCat
 */
public class CustomNameThreadFactory implements ThreadFactory {
    protected final String namePrefix;
    private final ThreadGroup group;
    private final AtomicInteger threadNumber = new AtomicInteger(1);

    public CustomNameThreadFactory(String namePrefix) {
        this(namePrefix, "Default");
    }

    public CustomNameThreadFactory(String namePrefix, String threadGroupName) {
        this(namePrefix, new ThreadGroup(threadGroupName));
    }

    public CustomNameThreadFactory(String namePrefix, ThreadGroup threadGroup) {
        this.namePrefix = namePrefix;
        group = threadGroup;
    }

    @Override
    public Thread newThread(Runnable r) {
        final Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
        if (t.isDaemon()) {
            t.setDaemon(false);
        }
        if (t.getPriority() != Thread.NORM_PRIORITY) {
            t.setPriority(Thread.NORM_PRIORITY);
        }
        return t;
    }
}
