/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.MultiTrackReadWriteHead;
import com.mamut.automata.turing.AbstractMultiTrackTuringMachine;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTrackConfiguration;
import com.mamut.automata.turing.MultiTrackTransition;
import com.mamut.automata.turing.TapeOrderedCollection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class MultiTrackNondeterministicTuringMachine extends AbstractMultiTrackTuringMachine<MtntmState> {
    public MultiTrackNondeterministicTuringMachine(
            TapeOrderedCollection tapes, 
            MultiTrackReadWriteHead head,
            ControlUnit<MtntmState> controlUnit
    ) {
        super(tapes, head, controlUnit);
    }
    
    @Override
    protected boolean testImpl() {
        return testImpl(new HashSet<>());
    }
    
    private boolean testImpl(Set<MultiTrackConfiguration<MtntmState>> visited) {
        MultiTrackConfiguration<MtntmState> config = getCurrentConfiguration();
        if (!visited.add(config)) {
            return false;
        }
        
        MtntmState currentState = controlUnit.getInternalState();
        List<Character> currentSymbols = head.read();
        int currentOffset = head.getOffset();
        Set<MultiTrackTransition<MtntmState>> transitions = currentState.transitions(currentSymbols);
        
        for (MultiTrackTransition<MtntmState> transition : transitions) {
            MtntmState nextState = transition.nextState();
            List<Character> replacingSymbol = transition.replacingSymbols();
            Movement movement = transition.movement();
            int nextOffset = movement.move(currentOffset);
            
            head.write(replacingSymbol);
            head.setOffset(nextOffset);
            controlUnit.setInternalState(nextState);
            
            if (testImpl(visited)) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            head.setOffset(currentOffset);
            head.write(currentSymbols);
        }
        
        return controlUnit.isAccepted();
    }
}
