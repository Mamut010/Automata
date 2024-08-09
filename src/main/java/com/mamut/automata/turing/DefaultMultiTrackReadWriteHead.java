/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.MultiTrackReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pc
 */
public class DefaultMultiTrackReadWriteHead implements MultiTrackReadWriteHead {
    private int sentryOffset;
    private final List<Integer> offsets;
    private final List<Tape> tapes;
    
    public DefaultMultiTrackReadWriteHead() {
        sentryOffset = -1;
        offsets = new ArrayList<>();
        tapes = new ArrayList<>();
    }

    @Override
    public void initialize(List<Tape> tapes) {
        Validators.ensureNonNull(tapes);
        Validators.ensureAllNonNull(tapes);
        
        sentryOffset = 0;
        
        this.offsets.clear();
        this.tapes.clear();
        for (int i = 0; i < tapes.size(); i++) {
            Tape tape = tapes.get(i);
            this.tapes.add(tape);
            offsets.add(0);
        }
    }

    @Override
    public int getOffset() {
        return sentryOffset;
    }

    @Override
    public void setOffset(int offset) {
        int change = offset - sentryOffset;
        sentryOffset = offset;
        
        for (int i = 0; i < offsets.size(); i++) {
            int newOffset = offsets.get(i) + change;
            offsets.set(i, newOffset);
        }
    }

    @Override
    public List<Character> read() {
        return CollectionUtils
                .zip(tapes, offsets)
                .map(pair -> pair.first().getSymbol(pair.second()))
                .toList();
    }

    @Override
    public void write(List<Character> symbols) {
        for (int i = 0; i < tapes.size(); i++) {
            Character symbol = symbols.get(i);
            int offset = offsets.get(i);
            Tape tape = tapes.get(i);
            
            int newOffset = tape.setSymbol(offset, symbol);
            offsets.set(i, newOffset);
        }
    }
}
