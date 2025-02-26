/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.turing.AbstractMultiTapeTuringMachine;
import com.mamut.automata.turing.Configuration;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeDeterministicState;
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
public class MultiTapeTuringMachine<T extends MultiTapeDeterministicState> extends AbstractMultiTapeTuringMachine<T> {
    public MultiTapeTuringMachine(TapeHeadIndexedCollection tapeHeads, ControlUnit<T> controlUnit) {
        super(tapeHeads, controlUnit);
    }
    
    @Override
    public boolean testImpl() {
        int tapeCount = tapeHeads.getTapeCount();
        
        T state = controlUnit.getInternalState();
        List<Character> symbols = getHeadStream().map(ReadWriteHead::read).toList();
        List<TuringTransition<T>> transitions = state.transitions(symbols);
        Set<List<Configuration<T>>> visited = new HashSet<>();
        
        while (!transitions.isEmpty()) {
            List<Configuration<T>> configs = getCurrentConfigurations();
            if (!visited.add(configs)) {
                return false;
            }
            
            for (int i = 0; i < tapeCount; i++) {
                ReadWriteHead head = tapeHeads.getHead(i);
                TuringTransition<T> transition = transitions.get(i);
                Character replacingSymbol = transition.replacingSymbol();
                Movement movement = transition.movement();
                
                int offset = head.getOffset();
                int nextOffset = movement.move(offset);
                
                head.write(replacingSymbol);
                head.setOffset(nextOffset);
            }
            
            state = transitions.get(0).nextState();
            
            controlUnit.setInternalState(state);
            symbols = getHeadStream().map(ReadWriteHead::read).toList();
            transitions = state.transitions(symbols);
        }
        
        return controlUnit.isAccepted();
    }
}
