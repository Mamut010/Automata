/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.MultiTrackReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.contracts.Transducer;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 *
 * @author Pc
 * @param <T> The State type
 */
public abstract class AbstractMultiTrackTuringMachine<T extends MultiTapeState> implements Accepter, Transducer {
    protected final TapeOrderedCollection tapes;
    protected final MultiTrackReadWriteHead head;
    protected final ControlUnit<T> controlUnit;
    
    public AbstractMultiTrackTuringMachine(TapeOrderedCollection tapes, MultiTrackReadWriteHead head, ControlUnit<T> controlUnit) {
        Validators.ensureNonNull(tapes, head, controlUnit);
        
        this.tapes = tapes;
        this.head = head;
        this.controlUnit = controlUnit;
    }

    @Override
    public boolean test(String inputFile) {
        controlUnit.initialize();
        ensureCompatibleTapeCount();
        
        int inputTapeIndex = tapes.getInputTapeIndex();
        Tape inputTape = tapes.getTape(inputTapeIndex);
        if (!inputTape.loadInput(inputFile)) {
            return false;
        }
        
        final String EMPTY = "";
        List<Tape> tapeList = new ArrayList<>();
        for (int i = 0; i < tapes.getTapeCount(); i++) {
            Tape tape = tapes.getTape(i);
            if (i != inputTapeIndex) {
                tape.loadInput(EMPTY);
            }
            tapeList.add(tape);
        }
        
        head.initialize(tapeList);
        
        return testImpl();
    }

    @Override
    public Character[] transduce(String inputFile) {
        if (test(inputFile)) {
            int outputIndex = tapes.getOutputTapeIndex();
            Tape outputTape = tapes.getTape(outputIndex);
            return outputTape.getContent();
        }
        else {
            return null;
        }
    }
    
    protected abstract boolean testImpl();
    
    protected MultiTrackConfiguration<T> getCurrentConfiguration() {
        T currentState = controlUnit.getInternalState();
        int offset = head.getOffset();
        List<String> snapshots = IntStream
                .range(0, tapes.getTapeCount())
                .mapToObj(i -> tapes.getTape(i).snapshot())
                .toList();
        return new MultiTrackConfiguration<>(currentState, offset, snapshots);
    }
    
    private void ensureCompatibleTapeCount() {
        if (tapes.getTapeCount() == 0 || controlUnit.getInternalState().getTapeCount() != tapes.getTapeCount()) {
            throw new IllegalStateException();
        }
    }
}
