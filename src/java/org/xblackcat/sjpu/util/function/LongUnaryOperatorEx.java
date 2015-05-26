package org.xblackcat.sjpu.util.function;

import java.util.Objects;

/**
 * Represents an operation on a single {@code long}-valued operand that produces
 * a {@code long}-valued result.  This is the primitive type specialization of
 * {@link UnaryOperatorEx} for {@code long}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(long)}.
 *
 * @see UnaryOperatorEx
 * @since 1.8
 */
@FunctionalInterface
public interface LongUnaryOperatorEx<E extends Throwable> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @return a unary operator that always returns its input argument
     */
    static <E extends Throwable> LongUnaryOperatorEx<E> identity() {
        return t -> t;
    }

    /**
     * Applies this operator to the given operand.
     *
     * @param operand the operand
     * @return the operator result
     */
    long applyAsLong(long operand) throws Exception;

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
     * @see #andThen(LongUnaryOperatorEx)
     */
    default LongUnaryOperatorEx<E> compose(LongUnaryOperatorEx<E> before) {
        Objects.requireNonNull(before);
        return (long v) -> applyAsLong(before.applyAsLong(v));
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
     * @see #compose(LongUnaryOperatorEx)
     */
    default LongUnaryOperatorEx<E> andThen(LongUnaryOperatorEx<E> after) {
        Objects.requireNonNull(after);
        return (long t) -> after.applyAsLong(applyAsLong(t));
    }
}
