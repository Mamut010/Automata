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
    private final Stack<Integer> positionBuffer;
    
    public PositionBufferedStringInputMechanism() {
        super();
        positionBuffer = new DequeStack<>();
    }
    
    @Override
    public boolean loadInputFile(String inputFile) {
        positionBuffer.clear();
        return super.loadInputFile(inputFile);
    }
    
    @Override
    public void markPosition() {
        int currentPosition = getPosition();
        positionBuffer.push(currentPosition);
    }

    @Override
    public void returnToLastMarkedPosition() {
        if (!positionBuffer.isEmpty()) {
            int lastPosition = positionBuffer.pop();
            this.setPosition(lastPosition);
        }
    }
}
