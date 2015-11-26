package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Represents a predicate (boolean-valued function) of one argument.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(Object)}.
 *
 * @param <T> the type of the input to the predicate
 * @param <E> the type of exception could be thrown while performing operation
 * @since 1.8
 */
@FunctionalInterface
public interface PredicateEx<T, E extends Throwable> {

    /**
     * Returns a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}.
     *
     * @param <T>       the type of arguments to the predicate
     * @param targetRef the object reference with which to compare for equality,
     *                  which may be {@code null}
     * @return a predicate that tests if two arguments are equal according
     * to {@link Objects#equals(Object, Object)}
     */
    static <T, E extends Throwable> PredicateEx<T, E> isEqual(Object targetRef) throws E {
        return (null == targetRef)
                ? Objects::isNull
                : targetRef::equals;
    }

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param t the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(T t) throws E;

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * AND of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code false}, then the {@code other}
     * predicate is not evaluated.
     * <p>
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ANDed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * AND of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default PredicateEx<T, E> and(PredicateEx<? super T, E> other) throws E {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default PredicateEx<T, E> negate() throws E {
        return (t) -> !test(t);
    }

    /**
     * Returns a composed predicate that represents a short-circuiting logical
     * OR of this predicate and another.  When evaluating the composed
     * predicate, if this predicate is {@code true}, then the {@code other}
     * predicate is not evaluated.
     * <p>
     * <p>Any exceptions thrown during evaluation of either predicate are relayed
     * to the caller; if evaluation of this predicate throws an exception, the
     * {@code other} predicate will not be evaluated.
     *
     * @param other a predicate that will be logically-ORed with this
     *              predicate
     * @return a composed predicate that represents the short-circuiting logical
     * OR of this predicate and the {@code other} predicate
     * @throws NullPointerException if other is null
     */
    default PredicateEx<T, E> or(PredicateEx<? super T, E> other) throws E {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }

    default BooleanSupplierEx<E> fix(T t) {
        return () -> test(t);
    }

    default <C extends Throwable> PredicateEx<T, C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> PredicateEx<T, C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> PredicateEx<T, C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> PredicateEx<T, C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return test(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default Predicate<T> unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default Predicate<T> unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default Predicate<T> unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default Predicate<T> unchecked(
            Function<Throwable, String> text,
            BiFunction<String, Throwable, ? extends RuntimeException> cover
    ) {
        return t -> {
            try {
                return test(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
