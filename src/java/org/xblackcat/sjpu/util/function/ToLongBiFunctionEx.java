package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToLongBiFunction;

/**
 * Represents a function that accepts two arguments and produces a long-valued
 * result.  This is the {@code long}-producing primitive specialization for
 * {@link BiFunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiFunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToLongBiFunctionEx<T, U, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    long applyAsLong(T t, U u) throws E;

    default ToLongFunctionEx<T, E> fixRight(U right) {
        return left -> applyAsLong(left, right);
    }

    default ToLongFunctionEx<U, E> fixLeft(T left) {
        return right -> applyAsLong(left, right);
    }

    default LongSupplierEx<E> fix(T left, U right) {
        return () -> applyAsLong(left, right);
    }

    default <C extends Throwable> ToLongBiFunctionEx<T, U, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> ToLongBiFunctionEx<T, U, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> ToLongBiFunctionEx<T, U, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> ToLongBiFunctionEx<T, U, C> cover(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, C> cover
    ) {
        return (t, u) -> {
            try {
                return applyAsLong(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default ToLongBiFunction<T, U> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default ToLongBiFunction<T, U> unchecked() {
        return unchecked(RuntimeException::new);
    }

    default ToLongBiFunction<T, U> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default ToLongBiFunction<T, U> unchecked(
            Supplier<String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return unchecked(e -> text.get(), cover);
    }

    default ToLongBiFunction<T, U> unchecked(
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
