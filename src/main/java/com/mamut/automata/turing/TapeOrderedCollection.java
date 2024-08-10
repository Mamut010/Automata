/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Tape;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class TapeOrderedCollection implements MultiTapeCollection {
    protected final List<Tape> tapes;
    
    private int inputTapeIndex;
    private int outputTapeIndex;
    
    public TapeOrderedCollection() {
        tapes = new ArrayList<>();
        inputTapeIndex = -1;
        outputTapeIndex = -1;
    }
    
    @Override
    public int getTapeCount() {
        return tapes.size();
    }
    
    @Override
    public int getInputTapeIndex() {
        return inputTapeIndex != -1 ? inputTapeIndex : 0;
    }
    
    @Override
    public int getOutputTapeIndex() {
        return outputTapeIndex != -1 ? outputTapeIndex : tapes.size() - 1;
    }
    
    public void setInputTapeIndex(int index) {
        inputTapeIndex = ensureValidIndex(index);
    }
    
    public void setOutputTapeIndex(int index) {
        outputTapeIndex = ensureValidIndex(index);
    }
    
    @Override
    public Tape getTape(int index) {
        ensureValidIndex(index);
        return tapes.get(index);
    }
    
    protected int ensureValidIndex(int index) {
        if (index < 0 || index >= tapes.size()) {
            throw new IllegalArgumentException();
        }
        return index;
    }
}
