package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents an operation that accepts two input arguments and returns no
 * result.  This is the two-arity specialization of {@link ConsumerEx}.
 * Unlike most other functional interfaces, {@code BiConsumer} is expected
 * to operate via side-effects.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #accept(Object, Object)}.
 *
 * @param <T> the type of the first argument to the operation
 * @param <U> the type of the second argument to the operation
 * @param <E> the type of exception could be thrown while performing operation
 * @see ConsumerEx
 * @since 1.8
 */
@FunctionalInterface
public interface BiConsumerEx<T, U, E extends Throwable> {

    /**
     * Performs this operation on the given arguments.
     *
     * @param t the first input argument
     * @param u the second input argument
     */
    void accept(T t, U u) throws E;

    /**
     * Returns a composed {@code BiConsumer} that performs, in sequence, this
     * operation followed by the {@code after} operation. If performing either
     * operation throws an exception, it is relayed to the caller of the
     * composed operation.  If performing this operation throws an exception,
     * the {@code after} operation will not be performed.
     *
     * @param after the operation to perform after this operation
     * @return a composed {@code BiConsumer} that performs in sequence this
     * operation followed by the {@code after} operation
     * @throws NullPointerException if {@code after} is null
     */
    default BiConsumerEx<T, U, E> andThen(BiConsumerEx<? super T, ? super U, E> after) {
        Objects.requireNonNull(after);

        return (l, U) -> {
            accept(l, U);
            after.accept(l, U);
        };
    }

    default ConsumerEx<T, E> fixRight(U u) {
        return t -> accept(t, u);
    }

    default ConsumerEx<U, E> fixLeft(T t) {
        return u -> accept(t, u);
    }

    default <C extends Throwable> BiConsumerEx<T, U, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> BiConsumerEx<T, U, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> BiConsumerEx<T, U, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> BiConsumerEx<T, U, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return (t, u) -> {
            try {
                accept(t, u);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default BiConsumer<T, U> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default BiConsumer<T, U> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default BiConsumer<T, U> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default BiConsumer<T, U> unchecked(
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
