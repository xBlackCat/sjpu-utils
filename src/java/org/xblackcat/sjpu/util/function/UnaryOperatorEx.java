package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Represents an operation on a single operand that produces a result of the
 * same type as its operand.  This is a specialization of {@code Function} for
 * the case where the operand and result are of the same type.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the operand and result of the operator
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface UnaryOperatorEx<T, E extends Throwable> extends FunctionEx<T, T, E> {

    /**
     * Returns a unary operator that always returns its input argument.
     *
     * @param <T> the type of the input and output of the operator
     * @return a unary operator that always returns its input argument
     */
    static <T, E extends Throwable> UnaryOperatorEx<T, E> identity() {
        return t -> t;
    }

    default <C extends Throwable> UnaryOperatorEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> UnaryOperatorEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> UnaryOperatorEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> UnaryOperatorEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return apply(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default UnaryOperator<T> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default UnaryOperator<T> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default UnaryOperator<T> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default UnaryOperator<T> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return t -> {
            try {
                return apply(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
