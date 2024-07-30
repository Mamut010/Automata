/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Pc
 */
public final class CollectionUtils {
    private CollectionUtils() {}
    
    public static <T> Set<T> flatMapToSet(Collection<T> items, Function<T, Collection<T>> mapper) {
        final int parallelThreshold = 1 << 20;
        Stream<T> itemStream = items.size() < parallelThreshold ? items.stream() : items.parallelStream();
        return itemStream
                .flatMap(item -> mapper.apply(item).stream())
                .collect(Collectors.toSet());
    }
    
    public static <K, T> Set<T> getOrCreateSet(Map<K, Set<T>> map, K key) {
        Set<T> value = map.get(key);
        if (value == null) {
            value = new HashSet<>();
            map.put(key, value);
        }
        return value;
    }
    
    public static <K, U, V> Map<U, V> getOrCreateMap(Map<K, Map<U, V>> map, K key) {
        Map<U, V> value = map.get(key);
        if (value == null) {
            value = new HashMap<>();
            map.put(key, value);
        }
        return value;
    }
    
    public static <T> Set<T> union(Set<T>... sets) {
        Set<T> result = new HashSet<>();
        for (Set<T> set : sets) {
            result.addAll(set);
        }
        return result;
    }
}
