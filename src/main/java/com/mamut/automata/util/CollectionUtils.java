/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
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
    
    public static <K, T> T getOrPutNew(Map<K, T> map, K key, Supplier<T> creator) {
        T value;
        if (!map.containsKey(key)) {
            value = creator.get();
            map.put(key, value);
        }
        else {
            value = map.get(key);
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
