/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.contracts.Transducer;
import com.mamut.automata.turing.SingleTapeTuringMachine;
import com.mamut.automata.turing.Configuration;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.Transition;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NondeterministicTuringMachine extends SingleTapeTuringMachine<NtmState> implements Accepter, Transducer {
    public NondeterministicTuringMachine(Tape tape, ReadWriteHead readWriteHead, ControlUnit<NtmState> controlUnit) {
        super(tape, readWriteHead, controlUnit);
    }

    @Override
    public boolean test(String inputFile) {
        if (!tape.loadInput(inputFile)) {
            return false;
        }
        
        readWriteHead.initialize(tape);
        controlUnit.initialize();
        
        return testImpl(new HashSet<>());
    }
    
    private boolean testImpl(Set<Configuration<NtmState>> visited) {
        Configuration<NtmState> currentConfiguration = getCurrentConfiguration();
        if (!visited.add(currentConfiguration)) {
            return false;
        }
        
        NtmState currentState = controlUnit.getInternalState();
        Character currentSymbol = readWriteHead.read();
        int currentOffset = readWriteHead.getOffset();
        Set<Transition<NtmState>> transitions = currentState.transitions(currentSymbol);
        
        for (Transition<NtmState> transition : transitions) {
            NtmState nextState = transition.nextState();
            Character replacingSymbol = transition.replacingSymbol();
            Movement movement = transition.movement();
            int nextOffset = movement.move(currentOffset);
            
            readWriteHead.write(replacingSymbol);
            readWriteHead.setOffset(nextOffset);
            controlUnit.setInternalState(nextState);
            
            if (testImpl(visited)) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            readWriteHead.setOffset(currentOffset);
            readWriteHead.write(currentSymbol);
        }
        
        return controlUnit.isAccepted();
    }
}
