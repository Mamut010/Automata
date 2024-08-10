/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.MultiTapeHeadCollection;
import com.mamut.automata.contracts.MultiTapeState;
import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.contracts.Transducer;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author Pc
 * @param <T> The State type
 */
public abstract class AbstractMultiTapeTuringMachine<T extends MultiTapeState> implements Accepter, Transducer {
    protected final MultiTapeHeadCollection tapeHeads;
    protected final ControlUnit<T> controlUnit;
    
    public AbstractMultiTapeTuringMachine(MultiTapeHeadCollection tapeHeads, ControlUnit<T> controlUnit) {
        Validators.ensureNonNull(tapeHeads, controlUnit);
        
        this.tapeHeads = tapeHeads;
        this.controlUnit = controlUnit;
    }
    
    @Override
    public boolean test(String inputFile) {
        controlUnit.initialize();
        ensureCompatibleTapeCount();
        
        int inputTapeIndex = tapeHeads.getInputTapeIndex();
        Tape inputTape = tapeHeads.getTape(inputTapeIndex);
        if (!inputTape.loadInput(inputFile)) {
            return false;
        }
        
        final String EMPTY = "";
        for (int i = 0; i < tapeHeads.getTapeCount(); i++) {
            Tape tape = tapeHeads.getTape(i);
            ReadWriteHead head = tapeHeads.getHead(i);
            
            if (i != inputTapeIndex) {
                tape.loadInput(EMPTY);
            }
            head.initialize(tape);
        }
        
        return testImpl();
    }
    
    @Override
    public Character[] transduce(String inputFile) {
        if (test(inputFile)) {
            int outputIndex = tapeHeads.getOutputTapeIndex();
            Tape outputTape = tapeHeads.getTape(outputIndex);
            return outputTape.getContent();
        }
        else {
            return null;
        }
    }
    
    protected abstract boolean testImpl();
    
    protected List<Configuration<T>> getCurrentConfigurations() {
        T currentState = controlUnit.getInternalState();
        List<Configuration<T>> configs = new ArrayList<>();
        for (int i = 0; i < tapeHeads.getTapeCount(); i++) {
            Tape tape = tapeHeads.getTape(i);
            ReadWriteHead head = tapeHeads.getHead(i);
            int offset = head.getOffset();
            String tapeSnapshot = tape.snapshot();
            configs.add(new Configuration(currentState, offset, tapeSnapshot));
        }
        return configs;
    }
    
    protected Stream<ReadWriteHead> getHeadStream() {
        return IntStream
                .range(0, tapeHeads.getTapeCount())
                .mapToObj(i -> tapeHeads.getHead(i));
    }
    
    private void ensureCompatibleTapeCount() {
        if (tapeHeads.getTapeCount() == 0 || controlUnit.getInternalState().getTapeCount() != tapeHeads.getTapeCount()) {
            throw new IllegalStateException();
        }
    }
}
