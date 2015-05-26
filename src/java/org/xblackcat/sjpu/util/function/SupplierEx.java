package org.xblackcat.sjpu.util.function;

/**
 * Represents a supplier of results.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception could be thrown while performing operation
 * @since 1.8
 */
@FunctionalInterface
public interface SupplierEx<T, E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws E;
}
