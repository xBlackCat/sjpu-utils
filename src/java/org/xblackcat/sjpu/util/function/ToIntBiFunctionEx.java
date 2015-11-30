package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntBiFunction;

/**
 * Represents a function that accepts two arguments and produces an int-valued
 * result.  This is the {@code int}-producing primitive specialization for
 * {@link BiFunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsInt(Object, Object)}.
 *
 * @param <T> the type of the first argument to the function
 * @param <U> the type of the second argument to the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiFunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface ToIntBiFunctionEx<T, U, E extends Throwable> {

    /**
     * Applies this function to the given arguments.
     *
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     */
    int applyAsInt(T t, U u) throws E;

    default ToIntFunctionEx<T, E> fixRight(U u) {
        return t -> applyAsInt(t, u);
    }

    default ToIntFunctionEx<U, E> fixLeft(T t) {
        return u -> applyAsInt(t, u);
    }

    default IntSupplierEx<E> fix(T t, U u) {
        return () -> applyAsInt(t, u);
    }

    default ToIntBiFunction<T, U> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default ToIntBiFunction<T, U> unchecked() {
        return unchecked(CoveringException::new);
    }

    default ToIntBiFunction<T, U> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default ToIntBiFunction<T, U> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default ToIntBiFunction<T, U> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return (t, u) -> {
            try {
                return applyAsInt(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
