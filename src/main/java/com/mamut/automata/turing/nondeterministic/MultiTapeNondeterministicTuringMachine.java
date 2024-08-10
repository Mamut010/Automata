/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.turing.AbstractMultiTapeTuringMachine;
import com.mamut.automata.turing.Configuration;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeNondeterministicState;
import com.mamut.automata.turing.TuringTransition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import com.mamut.automata.contracts.TapeHeadIndexedCollection;

/**
 *
 * @author Pc
 * @param <T> The State type
 */
public class MultiTapeNondeterministicTuringMachine<T extends MultiTapeNondeterministicState> extends AbstractMultiTapeTuringMachine<T> {
    public MultiTapeNondeterministicTuringMachine(TapeHeadIndexedCollection tapeHeads, ControlUnit<T> controlUnit) {
        super(tapeHeads, controlUnit);
    }

    @Override
    protected boolean testImpl() {
        return testImpl(new HashSet<>());
    }
    
    private boolean testImpl(Set<List<Configuration<T>>> visited) {
        List<Configuration<T>> configs = getCurrentConfigurations();
        if (!visited.add(configs)) {
            return false;
        }
        
        T state = controlUnit.getInternalState();
        List<Character> symbols = getHeadStream().map(ReadWriteHead::read).toList();
        List<Integer> offsets = getHeadStream().map(ReadWriteHead::getOffset).toList();
        Set<List<TuringTransition<T>>> possibleTransitions = state.transitions(symbols);
        
        for (List<TuringTransition<T>> transitions : possibleTransitions) {
            if (testTransitions(visited, transitions)) {
                return true;
            }
            revertChanges(symbols, offsets, state);
        }
        
        return controlUnit.isAccepted();
    }
    
    private boolean testTransitions(Set<List<Configuration<T>>> visited, List<TuringTransition<T>> transitions) {
        for (int i = 0; i < tapeHeads.getTapeCount(); i++) {
            ReadWriteHead head = tapeHeads.getHead(i);
            TuringTransition<T> transition = transitions.get(i);
            Character replacingSymbol = transition.replacingSymbol();
            Movement movement = transition.movement();

            int offset = head.getOffset();
            int nextOffset = movement.move(offset);

            head.write(replacingSymbol);
            head.setOffset(nextOffset);
        }
        
        T state = transitions.get(0).nextState();
        controlUnit.setInternalState(state);
        
        return testImpl(visited);
    }
    
    private void revertChanges(List<Character> previousSymbols, List<Integer> previousOffsets, T previousState) {
        controlUnit.setInternalState(previousState);
        
        for (int i = tapeHeads.getTapeCount() - 1; i >= 0; i--) {
            ReadWriteHead head = tapeHeads.getHead(i);
            Character previousSymbol = previousSymbols.get(i);
            int previousOffset = previousOffsets.get(i);
            
            head.setOffset(previousOffset);
            head.write(previousSymbol);
        }
    }
}
