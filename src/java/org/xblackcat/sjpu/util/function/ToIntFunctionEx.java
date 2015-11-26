package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a function that produces an int-valued result.  This is the
 * {@code int}-producing primitive specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(Object)}.
 *
 * @param <T> the type of the input to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToIntFunctionEx<T, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    int applyAsInt(T value) throws E;

    default IntSupplierEx<E> fix(T value) {
        return () -> applyAsInt(value);
    }

    default <C extends Throwable> ToIntFunctionEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> ToIntFunctionEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> ToIntFunctionEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> ToIntFunctionEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return applyAsInt(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
