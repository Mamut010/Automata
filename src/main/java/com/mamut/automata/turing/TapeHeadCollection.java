/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class TapeHeadCollection {
    private final List<Tape> tapes;
    private final List<ReadWriteHead> heads;
    
    private int inputTapeIndex;
    private int outputTapeIndex;
    
    public TapeHeadCollection() {
        tapes = new ArrayList<>();
        heads = new ArrayList<>();
        inputTapeIndex = -1;
        outputTapeIndex = -1;
    }
    
    public int getTapeCount() {
        return tapes.size();
    }
    
    public int getInputTapeIndex() {
        return inputTapeIndex != -1 ? inputTapeIndex : 0;
    }
    
    public int getOutputTapeIndex() {
        return outputTapeIndex != -1 ? outputTapeIndex : tapes.size() - 1;
    }
    
    public void setInputTapeIndex(int index) {
        inputTapeIndex = ensureValidIndex(index);
    }
    
    public void setOutputTapeIndex(int index) {
        outputTapeIndex = ensureValidIndex(index);
    }
    
    public void add(Tape tape, ReadWriteHead head) {
        Validators.ensureNonNull(tape, head);
        
        tapes.add(tape);
        heads.add(head);
    }
    
    public Tape getTape(int index) {
        ensureValidIndex(index);
        return tapes.get(index);
    }
    
    public ReadWriteHead getHead(int index) {
        ensureValidIndex(index);
        return heads.get(index);
    }
    
    public List<ReadWriteHead> getHeads() {
        return heads;
    }
    
    private int ensureValidIndex(int index) {
        if (index < 0 || index >= tapes.size()) {
            throw new IllegalArgumentException();
        }
        return index;
    }
}
