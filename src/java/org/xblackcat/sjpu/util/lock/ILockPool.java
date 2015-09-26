package org.xblackcat.sjpu.util.lock;

import java.util.concurrent.locks.Lock;

/**
 * 26.09.2015 10:54
 *
 * @author xBlackCat
 */
public interface ILockPool<ID> {
    Lock getLock(ID key);
}
