package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a function that accepts an int-valued argument and produces a
 * long-valued result.  This is the {@code int}-to-{@code long} primitive
 * specialization for {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #applyAsLong(int)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntToLongFunctionEx<E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    long applyAsLong(int value) throws E;

    default LongSupplierEx<E> fix(int value) {
        return () -> applyAsLong(value);
    }

    default <C extends Throwable> IntToLongFunctionEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> IntToLongFunctionEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> IntToLongFunctionEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> IntToLongFunctionEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return applyAsLong(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
