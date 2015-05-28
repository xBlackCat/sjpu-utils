package org.xblackcat.sjpu.util.function;

/**
 * Represents a function that accepts a double-valued argument and produces an
 * int-valued result.  This is the {@code double}-to-{@code int} primitive
 * specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(double)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface DoubleToIntFunctionEx<E extends Throwable> {
    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(double value) throws E;

    default IntSupplierEx<E> fix(double value) {
        return () -> applyAsInt(value);
    }
}
