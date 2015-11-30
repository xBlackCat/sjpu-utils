package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongBinaryOperator;
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

    default LongBinaryOperator unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default LongBinaryOperator unchecked() {
        return unchecked(CoveringException::new);
    }

    default LongBinaryOperator unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default LongBinaryOperator unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default LongBinaryOperator unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return (t, u) -> {
            try {
                return applyAsLong(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
