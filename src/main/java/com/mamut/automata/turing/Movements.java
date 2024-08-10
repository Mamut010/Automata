/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.turing.movements.*;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author Pc
 */
public final class Movements {
    private static final Map<String, WeakReference<Movement>> cache = new HashMap<>();
    
    private Movements() {}
    
    public static Movement left() {
        final String LEFT_KEY = "L";
        return getOrCacheNew(LEFT_KEY, LeftMovement::new);
    }    
    
    public static Movement right() {
        final String RIGHT_KEY = "R";
        return getOrCacheNew(RIGHT_KEY, RightMovement::new);
    }
    
    public static Movement stay() {
        final String STAY_KEY = "S";
        return getOrCacheNew(STAY_KEY, StayMovement::new);
    }
    
    private static Movement getOrCacheNew(String key, Supplier<Movement> creator) {
        WeakReference<Movement> reference = cache.get(key);
        Movement obj;
        if (reference == null || reference.get() == null) {
            obj = creator.get();
            reference = new WeakReference(obj);
            cache.put(key, reference);
        }
        return reference.get();
    }
}
