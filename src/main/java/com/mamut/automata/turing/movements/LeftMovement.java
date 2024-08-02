/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.movements;

import com.mamut.automata.turing.Movement;

/**
 *
 * @author Pc
 */
public class LeftMovement implements Movement {
    @Override
    public int move(int offset) {
        return offset - 1;
    }

    @Override
    public int revert(int offset) {
        return offset + 1;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() != obj.getClass();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }
    
    @Override
    public String toString() {
        return "L";
    }
}
