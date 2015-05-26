package org.xblackcat.sjpu.util.function;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * long-valued result.  This is the {@code int}-to-{@code long} primitive
 * specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(int)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntToLongFunctionEx<E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLong(int value) throws E;
}
