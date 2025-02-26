/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 *
 * @author Pc
 */
public final class Validators {
    private Validators() {}
    
    public static void ensureNonNull(Object... objects) {
        ensureAllNonNull(objects);
    }
    
    public static void ensureAllNonNull(Object[] objects) {
        if (Arrays.stream(objects).anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void ensureAllNonNull(Collection<?> objects) {
        if (objects.stream().anyMatch(Objects::isNull)) {
            throw new IllegalArgumentException();
        }
    }
}
