/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.Collection;
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
}
