package org.xblackcat.sjpu.util.function;

/**
 * Represents an operation upon two {@code double}-valued operands and producing a
 * {@code double}-valued result.   This is the primitive type specialization of
 * {@link BinaryOperatorEx} for {@code double}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(double, double)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see BinaryOperatorEx
 * @see DoubleUnaryOperatorEx
 * @since 1.8
 */
@FunctionalInterface
public interface DoubleBinaryOperatorEx<E extends Throwable> {
    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    double applyAsDouble(double left, double right) throws E;
}
