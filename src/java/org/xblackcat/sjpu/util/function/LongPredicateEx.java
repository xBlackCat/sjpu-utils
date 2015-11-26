/*
 * Copyright (c) 2010, 2013, Oracle and/or its affiliates. All rights reserved.
 * ORACLE PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 *
 */
package org.xblackcat.sjpu.util.function;

import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.LongPredicate;
import java.util.function.Supplier;

/**
 * Represents a predicate (boolean-valued function) of one {@code long}-valued
 * argument. This is the {@code long}-consuming primitive type specialization of
 * {@link PredicateEx}.
 * <p>
 * <p>This is a <a href="package-summary.html">functional interface</a>
 * whose functional method is {@link #test(long)}.
 *
 * @param <E> the type of exception could be thrown while performing operation
 * @see PredicateEx
 * @since 1.8
 */
@FunctionalInterface
public interface LongPredicateEx<E extends Throwable> {

    /**
     * Evaluates this predicate on the given argument.
     *
     * @param value the input argument
     * @return {@code true} if the input argument matches the predicate,
     * otherwise {@code false}
     */
    boolean test(long value) throws E;

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
    default LongPredicateEx and(LongPredicateEx other) {
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
    default LongPredicateEx negate() {
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
    default LongPredicateEx or(LongPredicateEx other) {
        Objects.requireNonNull(other);
        return (value) -> test(value) || other.test(value);
    }

    default BooleanSupplierEx<E> fix(long value) {
        return () -> test(value);
    }

    default <C extends Throwable> LongPredicateEx<C> cover(String exceptionText, BiFunction<String, Throwable, C> cover) {
        return cover(() -> exceptionText, cover);
    }

    default <C extends Throwable> LongPredicateEx<C> cover(BiFunction<String, Throwable, C> cover) {
        return cover(Throwable::getMessage, cover);
    }

    default <C extends Throwable> LongPredicateEx<C> cover(Supplier<String> text, BiFunction<String, Throwable, C> cover) {
        return cover(e -> text.get(), cover);
    }

    default <C extends Throwable> LongPredicateEx<C> cover(Function<Throwable, String> text, BiFunction<String, Throwable, C> cover) {
        return t -> {
            try {
                return test(t);
            } catch (Throwable e) {
                throw cover.apply(text.apply(e), e);
            }
        };
    }

    default LongPredicate unchecked(String exceptionText, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(() -> exceptionText, cover);
    }

    default LongPredicate unchecked(BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(Throwable::getMessage, cover);
    }

    default LongPredicate unchecked(Supplier<String> text, BiFunction<String, Throwable, ? extends RuntimeException> cover) {
        return unchecked(e -> text.get(), cover);
    }

    default LongPredicate unchecked(
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
