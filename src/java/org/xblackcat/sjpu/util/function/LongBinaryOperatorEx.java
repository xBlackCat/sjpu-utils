package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents an operation upon two {@code long}-valued operands and producing a
 * {@code long}-valued result.   This is the primitive type specialization of
 * {@link BinaryOperatorEx} for {@code long}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(long, long)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see BinaryOperatorEx
 * @see LongUnaryOperatorEx
 * @since 1.8
 */
@FunctionalInterface
public interface LongBinaryOperatorEx<E extends Throwable> {

    /**
     * Applies this operator to the given operands.
     *
     * @param left  the first operand
     * @param right the second operand
     * @return the operator result
     */
    long applyAsLong(long left, long right) throws E;

    default LongUnaryOperatorEx<E> fixRight(long right) {
        return left -> applyAsLong(left, right);
    }

    default LongUnaryOperatorEx<E> fixLeft(long left) {
        return right -> applyAsLong(left, right);
    }

    default LongSupplierEx<E> fix(long left, long right) {
        return () -> applyAsLong(left, right);
    }

    default <C extends Throwable> LongBinaryOperatorEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> LongBinaryOperatorEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> LongBinaryOperatorEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> LongBinaryOperatorEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return (t, u) -> {
            try {
                return applyAsLong(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
