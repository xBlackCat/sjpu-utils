package org.xblackcat.sjpu.util.function;


/**
 * Represents a supplier of {@code boolean}-valued results.  This is the
 * {@code boolean}-producing primitive specialization of {@link SupplierEx}.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsBoolean()}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see SupplierEx
 * @since 1.8
 */
@FunctionalInterface
public interface BooleanSupplierEx<E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    boolean getAsBoolean() throws E;
}
