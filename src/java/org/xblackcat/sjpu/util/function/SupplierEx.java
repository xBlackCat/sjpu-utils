package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a supplier of results.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #get()}.
 *
 * @param <T> the type of results supplied by this supplier
 * @param <E> the type of exception could be thrown while performing operation
 * @since 1.8
 */
@FunctionalInterface
public interface SupplierEx<T, E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    T get() throws E;

    default <C extends Throwable> SupplierEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> SupplierEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> SupplierEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> SupplierEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return () -> {
            try {
                return get();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default Supplier<T> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default Supplier<T> unchecked() {
        return unchecked(CoveringException::new);
    }

    default Supplier<T> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default Supplier<T> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default Supplier<T> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return () -> {
            try {
                return get();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
