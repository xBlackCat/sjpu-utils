/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.xblackcat.sjpu.util.function;

import java.util.Objects;

/**
 * Represents an operation that accepts a single {@code long}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link ConsumerEx} for {@code long}.  Unlike most other functional interfaces,
 * {@code LongConsumer} is expected to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(long)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see ConsumerEx
 * @since 1.8
 */
@FunctionalInterface
public interface LongConsumerEx<E extends Throwable> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(long value) throws E;

    /**
     * Returns a composed {@code LongConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code LongConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default LongConsumerEx andThen(LongConsumerEx after) {
        Objects.requireNonNull(after);
        return (long t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
