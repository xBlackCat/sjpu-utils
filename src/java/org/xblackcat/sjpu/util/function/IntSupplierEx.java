package org.xblackcat.sjpu.util.function;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

/**
 * Represents a supplier of {@code int}-valued results.  This is the
 * {@code int}-producing primitive specialization of {@link SupplierEx}.
 * <p>
 * <p>There is no requirement that a distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsInt()}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see SupplierEx
 * @since 1.8
 */
@FunctionalInterface
public interface IntSupplierEx<E extends Throwable> {

    /**
     * Gets a result.
     *
     * @return a result
     */
    int getAsInt() throws E;

    default <C extends Throwable> IntSupplierEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> IntSupplierEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> IntSupplierEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> IntSupplierEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return () -> {
            try {
                return getAsInt();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default IntSupplier unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default IntSupplier unchecked() {
        return unchecked(RuntimeException::new);
    }

    default IntSupplier unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default IntSupplier unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default IntSupplier unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return () -> {
            try {
                return getAsInt();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
