package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a function that accepts two arguments and produces a result.
 * This is the two-arity specialization of {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <R> the type of the result of the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface BiFunctionEx<T, U, R, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    R apply(T t, U u) throws E;

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
     */
    default <V> BiFunctionEx<T, U, V, E> andThen(FunctionEx<? super R, ? extends V, E> after) {
        Objects.requireNonNull(after);
        return (T t, U u) -> after.apply(apply(t, u));
    }

    /**
     * Build and return {@linkplain FunctionEx} interface obtained from the bi-function by fixing second parameter with specified constant value.
     *
     * @param u constant value for fixing second parameter
     * @return reduced bi-function by fixing second parameter
     */
    default FunctionEx<T, R, E> fixRight(U u) {
        return t -> apply(t, u);
    }

    /**
     * Build and return {@linkplain FunctionEx} interface obtained from the bi-function by fixing first parameter with specified constant value.
     *
     * @param t constant value for fixing first parameter
     * @return reduced bi-function by fixing first parameter
     */
    default FunctionEx<U, R, E> fixLeft(T t) {
        return u -> apply(t, u);
    }

    default SupplierEx<R, E> fix(T t, U u) {
        return () -> apply(t, u);
    }

    default <C extends Throwable> BiFunctionEx<T, U, R, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> BiFunctionEx<T, U, R, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> BiFunctionEx<T, U, R, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> BiFunctionEx<T, U, R, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return (t, u) -> {
            try {
                return apply(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default BiFunction<T, U, R> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default BiFunction<T, U, R> unchecked() {
        return unchecked(CoveringException::new);
    }

    default BiFunction<T, U, R> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default BiFunction<T, U, R> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default BiFunction<T, U, R> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return (t, u) -> {
            try {
                return apply(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }
}
