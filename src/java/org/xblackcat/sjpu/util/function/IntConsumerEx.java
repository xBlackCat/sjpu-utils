package org.xblackcat.sjpu.util.function;

import java.util.Objects;

/**
 * Represents an operation that accepts a single {@code int}-valued argument and
 * returns no result.  This is the primitive type specialization of
 * {@link ConsumerEx} for {@code int}.  Unlike most other functional interfaces,
 * {@code IntConsumer} is expected to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(int)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see ConsumerEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntConsumerEx<E extends Throwable> {

    /**
     * Performs this operation on the given argument.
     *
     * @param value the input argument
     */
    void accept(int value) throws E;

    /**
     * Returns a composed {@code IntConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code IntConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default IntConsumerEx<E> andThen(IntConsumerEx<E> after) {
        Objects.requireNonNull(after);
        return (int t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
