package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Represents a predicate (boolean-valued function) of one {@code double}-valued
 * argument. This is the {@code double}-consuming primitive type specialization
 * of {@link PredicateEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(double)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see PredicateEx
 * @since 1.8
 */
@FunctionalInterface
public interface DoublePredicateEx<E extends Throwable> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(double value) throws E;

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
    default DoublePredicateEx<E> and(DoublePredicateEx<E> other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) && other.test(value);
    }

    /**
     * Returns a predicate that represents the logical negation of this
     * predicate.
     *
     * @return a predicate that represents the logical negation of this
     * predicate
     */
    default DoublePredicateEx<E> negate() {
        return (value) -> !test(value);
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
    default DoublePredicateEx<E> or(DoublePredicateEx<E> other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) || other.test(value);
    }

    default BooleanSupplierEx<E> fix(double value) {
        return () -> test(value);
    }

    default <C extends Throwable> DoublePredicateEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> DoublePredicateEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> DoublePredicateEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> DoublePredicateEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return test(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

}
