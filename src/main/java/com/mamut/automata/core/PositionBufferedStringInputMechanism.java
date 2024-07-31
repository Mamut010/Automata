/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.core;

import com.mamut.automata.contracts.BacktrackableInputMechanism;
import com.mamut.automata.util.DequeStack;
import com.mamut.automata.util.Stack;

/**
 *
 * @author Pc
 */
public class PositionBufferedStringInputMechanism extends StringInputMechanism implements BacktrackableInputMechanism {
    private final Stack<Integer> markedPositions;
    
    public PositionBufferedStringInputMechanism() {
        super();
        markedPositions = new DequeStack<>();
    }
    
    @Override
    public void markPosition() {
        markedPositions.push(getPosition());
    }

    @Override
    public void returnToLastMarkedPosition() {
        if (!markedPositions.isEmpty()) {
            int lastIndex = markedPositions.pop();
            this.setPosition(lastIndex);
        }
    }
}
