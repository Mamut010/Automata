/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.turing.AbstractSingleTapeTuringMachine;
import com.mamut.automata.turing.Configuration;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.TuringTransition;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class TuringMachine extends AbstractSingleTapeTuringMachine<DtmState> {
    public TuringMachine(Tape tape, ReadWriteHead readWriteHead, ControlUnit<DtmState> controlUnit) {
        super(tape, readWriteHead, controlUnit);
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
        TuringTransition<DtmState> transition = state.transition(symbol);
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
}
