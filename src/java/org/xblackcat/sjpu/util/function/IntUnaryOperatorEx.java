package org.xblackcat.sjpu.util.function;

import java.util.Objects;

/**
 * Represents an operation on a single {@code int}-valued operand that produces
 * an {@code int}-valued result.  This is the primitive type specialization of
 * {@link UnaryOperatorEx} for {@code int}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(int)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see UnaryOperatorEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntUnaryOperatorEx<E extends Throwable> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Throwable> IntUnaryOperatorEx<E> identity() {
        return t -> t;
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    int applyAsInt(int operand) throws E;

    /**
     * Returns a composed operator that first applies the {@code before}
     * operator to its input, and then applies this operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param before the operator to apply before this operator is applied
     * @return a composed operator that first applies the {@code before}
     * operator and then applies this operator
     * @throws NullPointerException if before is null
     * @see #andThen(IntUnaryOperatorEx)
     */
    default IntUnaryOperatorEx compose(IntUnaryOperatorEx before) {
        Objects.requireNonNull(before);
        return (int v) -> applyAsInt(before.applyAsInt(v));
    }

    /**
     * Returns a composed operator that first applies this operator to
     * its input, and then applies the {@code after} operator to the result.
     * If evaluation of either operator throws an exception, it is relayed to
     * the caller of the composed operator.
     *
     * @param after the operator to apply after this operator is applied
     * @return a composed operator that first applies this operator and then
     * applies the {@code after} operator
     * @throws NullPointerException if after is null
     * @see #compose(IntUnaryOperatorEx)
     */
    default IntUnaryOperatorEx andThen(IntUnaryOperatorEx after) {
        Objects.requireNonNull(after);
        return (int t) -> after.applyAsInt(applyAsInt(t));
    }

    default IntSupplierEx<E> fix(int value) {
        return () -> applyAsInt(value);
    }

}
