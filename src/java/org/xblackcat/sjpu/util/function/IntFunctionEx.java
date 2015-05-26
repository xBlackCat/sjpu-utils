package org.xblackcat.sjpu.util.function;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * result.  This is the {@code int}-consuming primitive specialization for
 * {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(int)}.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntFunctionEx<R, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(int value) throws E;
}
