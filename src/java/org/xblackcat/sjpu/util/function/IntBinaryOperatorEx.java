package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents an operation upon two {@code int}-valued operands and producing an
 * {@code int}-valued result.   This is the primitive type specialization of
 * {@link BinaryOperatorEx} for {@code int}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(int, int)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see BinaryOperatorEx
 * @see IntUnaryOperatorEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntBinaryOperatorEx<E extends Throwable> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    int applyAsInt(int left, int right) throws E;

    default IntUnaryOperatorEx<E> fixRight(int right) {
        return left -> applyAsInt(left, right);
    }

    default IntUnaryOperatorEx<E> fixLeft(int left) {
        return right -> applyAsInt(left, right);
    }

    default IntSupplierEx<E> fix(int left, int right) {
        return () -> applyAsInt(left, right);
    }

    default <C extends Throwable> IntBinaryOperatorEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> IntBinaryOperatorEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> IntBinaryOperatorEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> IntBinaryOperatorEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return (t, u) -> {
            try {
                return applyAsInt(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
