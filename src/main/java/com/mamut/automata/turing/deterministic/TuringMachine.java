/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.contracts.Transducer;
import com.mamut.automata.turing.Configuration;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.Transition;
import com.mamut.automata.util.Validators;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class TuringMachine implements Accepter, Transducer {
    private final Tape tape;
    private final ReadWriteHead readWriteHead;
    private final ControlUnit<DtmState> controlUnit;
    
    public TuringMachine(Tape tape, ReadWriteHead readWriteHead, ControlUnit<DtmState> controlUnit) {
        Validators.ensureNonNull(tape, readWriteHead, controlUnit);
        
        this.tape = tape;
        this.readWriteHead = readWriteHead;
        this.controlUnit = controlUnit;
    }
    
    @Override
    public boolean test(String inputFile) {
        if (!tape.loadInput(inputFile)) {
            return false;
        }
        readWriteHead.initialize(tape);
        controlUnit.initialize();
        
        DtmState state = controlUnit.getInternalState();
        Character symbol = readWriteHead.read();
        Transition<DtmState> transition = state.transition(symbol);
        Set<Configuration<DtmState>> visited = new HashSet<>();
        
        while (transition !=  null) {
            Configuration<DtmState> currentConfiguration = getCurrentConfiguration();
            if (!visited.add(currentConfiguration)) {
                return false;
            }
            
            state = transition.nextState();
            Character replacingSymbol = transition.replacingSymbol();
            Movement movement = transition.movement();
            int offset = readWriteHead.getOffset();
            int nextOffset = movement.move(offset);
            
            readWriteHead.write(replacingSymbol);
            readWriteHead.setOffset(nextOffset);
            controlUnit.setInternalState(state);
            
            symbol = readWriteHead.read();
            transition = state.transition(symbol);
        }
        
        return controlUnit.isAccepted();
    }
    
    @Override
    public Character[] transduce(String inputFile) {
        if (test(inputFile)) {
            return tape.getContent();
        }
        else {
            return null;
        }
    }
    
    private Configuration<DtmState> getCurrentConfiguration() {
        DtmState currentState = controlUnit.getInternalState();
        int offset = readWriteHead.getOffset();
        String tapeSnapshot = tape.snapshot();
        return new Configuration(currentState, offset, tapeSnapshot);
    }
}
