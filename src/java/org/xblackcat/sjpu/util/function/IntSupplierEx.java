package org.xblackcat.sjpu.util.function;

/**
 * Represents a supplier of {@code int}-valued results.  This is the
 * {@code int}-producing primitive specialization of {@link SupplierEx}.
 * <p>
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsInt()}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see SupplierEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntSupplierEx<E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    int getAsInt() throws E;
}
