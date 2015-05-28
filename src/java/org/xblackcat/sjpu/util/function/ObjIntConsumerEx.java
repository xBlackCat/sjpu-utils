package org.xblackcat.sjpu.util.function;

/**
 * Represents an operation that accepts an object-valued and a
 * {@code int}-valued argument, and returns no result.  This is the
 * {@code (reference, int)} specialization of {@link BiConsumerEx}.
 * Unlike most other functional interfaces, {@code ObjIntConsumer} is
 * expected to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, int)}.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiConsumerEx
 * @since 1.8
 */
@FunctionalInterface
public interface ObjIntConsumerEx<T, E extends Throwable> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void accept(T t, int value) throws E;

    default ConsumerEx<T, E> fixRight(int value) {
        return t -> accept(t, value);
    }

    default IntConsumerEx<E> fixLeft(T t) {
        return value -> accept(t, value);
    }
}