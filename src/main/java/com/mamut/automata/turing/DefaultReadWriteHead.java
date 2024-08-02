/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import java.util.Objects;

/**
 *
 * @author Pc
 */
public class DefaultReadWriteHead implements ReadWriteHead {
    private int offset;
    private Tape tape;
    
    public DefaultReadWriteHead() {
        offset = 0;
        tape = null;
    }
    
    @Override
    public void initialize(Tape tape) {
        this.tape = Objects.requireNonNull(tape);
        offset = 0;
    }
    
    @Override
    public int getOffset() {
        return offset;
    }

    @Override
    public void setOffset(int offset) {
        this.offset = offset;
    }

    @Override
    public Character read() {
        return tape.getSymbol(offset);
    }

    @Override
    public void write(Character symbol) {
        offset = tape.setSymbol(offset, symbol);
    }
}
