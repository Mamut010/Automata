/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.core;

import com.mamut.automata.contracts.InputMechanism;

/**
 *
 * @author Pc
 */
public final class SimpleInputMechanism implements InputMechanism {
    private String inputFile;
    private int index;
    
    public SimpleInputMechanism() {
        inputFile = null;
        index = 0;
    }
    
    @Override
    public boolean loadInputFile(String loadInputFile) {
        inputFile = loadInputFile;
        index = 0;
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
}
