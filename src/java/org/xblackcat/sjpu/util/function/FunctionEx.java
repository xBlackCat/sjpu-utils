package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a function that accepts one argument and produces a result.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception could be thrown while performing operation
 * @since 1.8
 */
@FunctionalInterface
public interface FunctionEx<T, R, E extends Throwable> {

    /**
     * Returns a function that always returns its input argument.
     *
     * @param <T> the type of the input and output objects to the function
     * @return a function that always returns its input argument
     */
    static <T, E extends Throwable> FunctionEx<T, T, E> identity() {
        return t -> t;
    }

    /**
     * Applies this function to the given argument.
     *
     * @param t the function argument
     * @return the function result
     */
    R apply(T t) throws E;

    /**
     * Returns a composed function that first applies the {@code before}
     * function to its input, and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>    the type of input to the {@code before} function, and to the
     *               composed function
     * @param before the function to apply before this function is applied
     * @return a composed function that first applies the {@code before}
     * function and then applies this function
     * @throws NullPointerException if before is null
     * @see #andThen(FunctionEx)
     */
    default <V> FunctionEx<V, R, E> compose(FunctionEx<? super V, ? extends T, E> before) {
        Objects.requireNonNull(before);
        return (V v) -> apply(before.apply(v));
    }

    /**
     * Returns a composed function that first applies this function to
     * its input, and then applies the {@code after} function to the result.
     * If evaluation of either function throws an exception, it is relayed to
     * the caller of the composed function.
     *
     * @param <V>   the type of output of the {@code after} function, and of the
     *              composed function
     * @param after the function to apply after this function is applied
     * @return a composed function that first applies this function and then
     * applies the {@code after} function
     * @throws NullPointerException if after is null
     * @see #compose(FunctionEx)
     */
    default <V> FunctionEx<T, V, E> andThen(FunctionEx<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t) -> after.apply(apply(t));
    }

    default SupplierEx<R, E> fix(T t) {
        return () -> apply(t);
    }

    default <C extends Throwable> FunctionEx<T, R, C> cover(String exceptionText, BiFunction<String, Throwable, C> coverage) {
        return cover(() -> exceptionText, coverage);
    }

    default <C extends Throwable> FunctionEx<T, R, C> cover(BiFunction<String, Throwable, C> coverage) {
        return cover(Throwable::getMessage, coverage);
    }

    default <C extends Throwable> FunctionEx<T, R, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> coverage) {
        return cover(e -> text.get(), coverage);
    }

    default <C extends Throwable> FunctionEx<T, R, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> coverage) {
        return t -> {
            try {
                return apply(t);
            } catch (Throwable e) {
                throw coverage.apply(text.apply(e), e);
            }
        };
    }

    default Function<T, R> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default Function<T, R> unchecked() {
        return unchecked(CoveringException::new);
    }

    default Function<T, R> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default Function<T, R> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default Function<T, R> unchecked(
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
