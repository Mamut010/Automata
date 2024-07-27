/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 *
 * @author Pc
 */
public final class CollectionUtils {
    private CollectionUtils() {}
    
    public static <T> Set<T> flatMapToSet(Collection<T> items, Function<T, Collection<T>> mapper) {
        return items
                .stream()
                .flatMap(item -> mapper.apply(item).stream())
                .collect(Collectors.toSet());
    } 
}
