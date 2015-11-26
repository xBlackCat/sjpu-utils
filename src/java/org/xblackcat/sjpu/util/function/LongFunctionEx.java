package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a function that accepts a long-valued argument and produces a
 * result.  This is the {@code long}-consuming primitive specialization for
 * {@link FunctionEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #apply(long)}.
 *
 * @param <R> the type of the result of the function
 * @param <E> the type of exception could be thrown while performing operation
 * @see FunctionEx
 * @since 1.8
 */
@FunctionalInterface
public interface LongFunctionEx<R, E extends Throwable> {

    /**
     * Applies this function to the given argument.
     *
     * @param value the function argument
     * @return the function result
     */
    R apply(long value) throws E;

    default SupplierEx<R, E> fix(long value) {
        return () -> apply(value);
    }

    default <C extends Throwable> LongFunctionEx<R, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> LongFunctionEx<R, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> LongFunctionEx<R, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> LongFunctionEx<R, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return apply(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
