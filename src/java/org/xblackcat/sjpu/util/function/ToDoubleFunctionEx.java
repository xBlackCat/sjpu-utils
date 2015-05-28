package org.xblackcat.sjpu.util.function;

/**
 * Represents a function that produces a double-valued result.  This is the
 * {@code double}-producing primitive specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToDoubleFunctionEx<T, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(T value) throws E;

    default DoubleSupplierEx<E> fix(T t) {
        return () -> applyAsDouble(t);
    }

}
