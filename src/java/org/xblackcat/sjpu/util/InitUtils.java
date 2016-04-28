package org.xblackcat.sjpu.util;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper class for initialization read-only collections
 *
 * @author xBlackCat
 */
public class InitUtils {
    @SafeVarargs
    public static <E> List<E> asList(E... elements) {
        return Collections.unmodifiableList(Arrays.asList(elements));
    }

    @SafeVarargs
    public static <E> Set<E> asSet(E... elements) {
        return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(elements)));
    }

    @SafeVarargs
    public static <E extends Enum<E>> Set<E> asSet(E first, E... rest) {
        return Collections.unmodifiableSet(EnumSet.of(first, rest));
    }

    @SafeVarargs
    public static <K extends Enum<K>, V> Map<K, V> asMap(Class<K> enumClass, Map.Entry<? extends K, ? extends V>... entries) {
        return asMap(enumClass, throwingMerger(), entries);
    }

    @SafeVarargs
    public static <K extends Enum<K>, V> Map<K, V> asMap(
            Class<K> enumClass,
            BinaryOperator<V> merger,
            Map.Entry<? extends K, ? extends V>... entries
    ) {
        return asMap(() -> new EnumMap<>(enumClass), merger, entries);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> asMap(Map.Entry<? extends K, ? extends V>... entries) {
        return asMap(throwingMerger(), entries);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> asMap(BinaryOperator<V> merger, Map.Entry<? extends K, ? extends V>... entries) {
        return asMap(HashMap::new, merger, entries);
    }

    @SafeVarargs
    public static <K, V> Map<K, V> asMap(
            Supplier<Map<K, V>> mapSupplier,
            BinaryOperator<V> merger,
            Map.Entry<? extends K, ? extends V>... entries
    ) {
        return Collections.unmodifiableMap(
                Stream.of(entries).collect(
                        Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, merger, mapSupplier)
                )
        );
    }

    public static <K, V> Map.Entry<K, V> entry(K key, V value) {
        return new Entry<>(key, value);
    }

    private static final class Entry<EK, EV> implements Map.Entry<EK, EV> {
        private final EK key;
        private final EV value;

        private Entry(EK key, EV value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public EV getValue() {
            return value;
        }

        @Override
        public EK getKey() {
            return key;
        }

        @Override
        public EV setValue(EV value) {
            throw new UnsupportedOperationException();
        }
    }

    /**
     * Copy of {@linkplain Collectors#throwingMerger()} private function to be used in the class
     */
    public static <T> BinaryOperator<T> throwingMerger() {
        return (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        };
    }

}
