/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Supplier;
import com.mamut.automata.contracts.TapeIndexedCollection;
import com.mamut.automata.contracts.TapeHeadIndexedCollection;

/**
 * Adapter class internally used by Multi-track Turing Machine classes
 * @author Pc
 */
public final class MultiTrackCollection implements TapeHeadIndexedCollection {
    private final TapeIndexedCollection tapes;
    private final Supplier<ReadWriteHead> headCreator;
    private final Map<Integer, ReadWriteHead> heads;
    
    public MultiTrackCollection(TapeIndexedCollection tapes, Supplier<ReadWriteHead> headCreator) {
        Validators.ensureNonNull(tapes, headCreator);
        this.tapes = tapes;
        this.headCreator = headCreator;
        this.heads = new TreeMap<>();
    }
    
    @Override
    public ReadWriteHead getHead(int index) {
        return CollectionUtils.getOrPutNew(heads, index, headCreator);
    }

    @Override
    public int getTapeCount() {
        return tapes.getTapeCount();
    }

    @Override
    public int getInputTapeIndex() {
        return tapes.getInputTapeIndex();
    }

    @Override
    public int getOutputTapeIndex() {
        return tapes.getOutputTapeIndex();
    }

    @Override
    public Tape getTape(int index) {
        return tapes.getTape(index);
    }
}
