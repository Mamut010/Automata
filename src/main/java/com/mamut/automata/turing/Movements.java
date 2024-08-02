/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.turing.movements.*;

/**
 *
 * @author Pc
 */
public final class Movements {
    private Movements() {}
    
    public static Movement left() {
        return new LeftMovement();
    }    
    
    public static Movement right() {
        return new RightMovement();
    }
    
    public static Movement stay() {
        return new StayMovement();
    }
}
