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
public class PositionBufferedInputMechanism implements BacktrackableInputMechanism {
    private String inputFile;
    private int index;
    private final Stack<Integer> markedPositions;
    
    public PositionBufferedInputMechanism() {
        inputFile = null;
        index = 0;
        markedPositions = new DequeStack<>();
    }
    
    @Override
    public boolean loadInputFile(String loadInputFile) {
        inputFile = loadInputFile;
        index = 0;
        markedPositions.clear();
        return true;
    }
    
    @Override
    public boolean isEOF() {
        return inputFile == null || index >= inputFile.length();
    }
    
    @Override
    public Character advance() {
        if (isEOF()) {
            return null;
        }
        else {
            return inputFile.charAt(index++);
        }
    }
    
    @Override
    public int getPosition() {
        return index;
    }
    
    @Override
    public void markPosition() {
        markedPositions.push(index);
    }

    @Override
    public void returnToLastMarkedPosition() {
        if (!markedPositions.isEmpty()) {
            index = markedPositions.pop();
        }
    }
}
