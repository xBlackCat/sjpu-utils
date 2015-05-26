package org.xblackcat.sjpu.util.function;

/**
 * Represents a function that accepts two arguments and produces a long-valued
 * result.  This is the {@code long}-producing primitive specialization for
 * {@link BiFunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiFunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToLongBiFunctionEx<T, U, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLong(T t, U u) throws E;
}
