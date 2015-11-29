package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;

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

    default DoubleUnaryOperatorEx<E> fixRight(double right) {
        return left -> applyAsDouble(left, right);
    }

    default DoubleUnaryOperatorEx<E> fixLeft(double left) {
        return right -> applyAsDouble(left, right);
    }

    default DoubleSupplierEx<E> fix(double left, double right) {
        return () -> applyAsDouble(left, right);
    }

    default <C extends Throwable> DoubleBinaryOperatorEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> DoubleBinaryOperatorEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> DoubleBinaryOperatorEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> DoubleBinaryOperatorEx<C> cover(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, C> cover
    ) {
        return (t, u) -> {
            try {
                return applyAsDouble(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default DoubleBinaryOperator unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default DoubleBinaryOperator unchecked() {
        return unchecked(RuntimeException::new);
    }

    default DoubleBinaryOperator unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default DoubleBinaryOperator unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default DoubleBinaryOperator unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return (t, u) -> {
            try {
                return applyAsDouble(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }
}
