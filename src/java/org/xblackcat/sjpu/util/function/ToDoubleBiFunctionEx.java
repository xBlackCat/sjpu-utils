package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleBiFunction;

/**
 * Represents a function that accepts two arguments and produces a double-valued
 * result.  This is the {@code double}-producing primitive specialization for
 * {@link BiFunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiFunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToDoubleBiFunctionEx<T, U, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    double applyAsDouble(T t, U u) throws E;

    default ToDoubleFunctionEx<T, E> fixRight(U u) {
        return t -> applyAsDouble(t, u);
    }

    default ToDoubleFunctionEx<U, E> fixLeft(T t) {
        return u -> applyAsDouble(t, u);
    }

    default DoubleSupplierEx<E> fix(T t, U u) {
        return () -> applyAsDouble(t, u);
    }

    default <C extends Throwable> ToDoubleBiFunctionEx<T, U, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> ToDoubleBiFunctionEx<T, U, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> ToDoubleBiFunctionEx<T, U, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> ToDoubleBiFunctionEx<T, U, C> cover(
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

    default ToDoubleBiFunction<T, U> unchecked(
            String exceptionText,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return unchecked(() -> exceptionText, cover);
    }

    default ToDoubleBiFunction<T, U> unchecked() {
        return unchecked(CoveringException::new);
    }

    default ToDoubleBiFunction<T, U> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default ToDoubleBiFunction<T, U> unchecked(
            Supplier<String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return unchecked(e -> text.get(), cover);
    }

    default ToDoubleBiFunction<T, U> unchecked(
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
