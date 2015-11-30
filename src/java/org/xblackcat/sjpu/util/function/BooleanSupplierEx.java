package org.xblackcat.sjpu.util.function;


import java.util.function.BiFunction;
import java.util.function.BooleanSupplier;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a supplier of {@code boolean}-valued results.  This is the
 * {@code boolean}-producing primitive specialization of {@link SupplierEx}.
 * <p>
 * <p>There is no requirement that a new or distinct result be returned each
 * time the supplier is invoked.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #getAsBoolean()}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see SupplierEx
 * @since 1.8
 */
@FunctionalInterface
public interface BooleanSupplierEx<E extends Throwable> {
    /**
     * Gets a result.
     *
     * @return a result
     */
    boolean getAsBoolean() throws E;

    default <C extends Throwable> BooleanSupplierEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> BooleanSupplierEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> BooleanSupplierEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> BooleanSupplierEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return () -> {
            try {
                return getAsBoolean();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default BooleanSupplier unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default BooleanSupplier unchecked() {
        return unchecked(CoveringException::new);
    }

    default BooleanSupplier unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default BooleanSupplier unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default BooleanSupplier unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return () -> {
            try {
                return getAsBoolean();
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
