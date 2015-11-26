package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;

/**
 * Represents a function that produces a double-valued result.  This is the
 * {@code double}-producing primitive specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsDouble(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToDoubleFunctionEx<T, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    double applyAsDouble(T value) throws E;

    default DoubleSupplierEx<E> fix(T t) {
        return () -> applyAsDouble(t);
    }

    default <C extends Throwable> ToDoubleFunctionEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> ToDoubleFunctionEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> ToDoubleFunctionEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> ToDoubleFunctionEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return applyAsDouble(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default ToDoubleFunction<T> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default ToDoubleFunction<T> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default ToDoubleFunction<T> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default ToDoubleFunction<T> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return t -> {
            try {
                return applyAsDouble(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }


}
