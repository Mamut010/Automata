/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.turing.movements.*;
import com.mamut.automata.util.CollectionUtils;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pc
 */
public final class Movements {
    private static final Map<String, Movement> cache = new HashMap<>();
    private static final String LEFT_KEY = "L";
    private static final String RIGHT_KEY = "R";
    private static final String STAY_KEY = "S";
    
    private Movements() {}
    
    public static Movement left() {
        return CollectionUtils.getOrPutNew(cache, LEFT_KEY, LeftMovement::new);
    }    
    
    public static Movement right() {
        return CollectionUtils.getOrPutNew(cache, RIGHT_KEY, RightMovement::new);
    }
    
    public static Movement stay() {
        return CollectionUtils.getOrPutNew(cache, STAY_KEY, StayMovement::new);
    }
}
