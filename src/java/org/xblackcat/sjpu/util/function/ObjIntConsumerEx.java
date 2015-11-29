package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;

/**
 * Represents an operation that accepts an object-valued and a
 * {@code int}-valued argument, and returns no result.  This is the
 * {@code (reference, int)} specialization of {@link BiConsumerEx}.
 * Unlike most other functional interfaces, {@code ObjIntConsumer} is
 * expected to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, int)}.
 *
 * @param <T> the type of the object argument to the operation
 * @param <E> the type of exception could be thrown while performing operation
 * @see BiConsumerEx
 * @since 1.8
 */
@FunctionalInterface
public interface ObjIntConsumerEx<T, E extends Throwable> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t     the first input argument
     * @param value the second input argument
     */
    void accept(T t, int value) throws E;

    default ConsumerEx<T, E> fixRight(int value) {
        return t -> accept(t, value);
    }

    default IntConsumerEx<E> fixLeft(T t) {
        return value -> accept(t, value);
    }

    default <C extends Throwable> ObjIntConsumerEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> ObjIntConsumerEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> ObjIntConsumerEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> ObjIntConsumerEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return (t, u) -> {
            try {
                accept(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default ObjIntConsumer<T> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default ObjIntConsumer<T> unchecked() {
        return unchecked(RuntimeException::new);
    }

    default ObjIntConsumer<T> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default ObjIntConsumer<T> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default ObjIntConsumer<T> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return (t, u) -> {
            try {
                accept(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
