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
import com.mamut.automata.turing.TapeHeadCollection;
import com.mamut.automata.turing.Transition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class MultiTapeTuringMachine extends AbstractMultiTapeTuringMachine<MultiTapeDeterministicState> {
    public MultiTapeTuringMachine(TapeHeadCollection tapeHeads, ControlUnit<MultiTapeDeterministicState> controlUnit) {
        super(tapeHeads, controlUnit);
    }
    
    @Override
    public boolean testImpl() {
        int tapeCount = tapeHeads.getTapeCount();
        
        MultiTapeDeterministicState state = controlUnit.getInternalState();
        List<Character> symbols = tapeHeads.getHeads().stream().map(ReadWriteHead::read).toList();
        List<Transition<MultiTapeDeterministicState>> transitions = state.transitions(symbols);
        Set<List<Configuration<MultiTapeDeterministicState>>> visited = new HashSet<>();
        
        while (!transitions.isEmpty()) {
            List<Configuration<MultiTapeDeterministicState>> configs = getCurrentConfigurations();
            if (!visited.add(configs)) {
                return false;
            }
            
            for (int i = 0; i < tapeCount; i++) {
                ReadWriteHead head = tapeHeads.getHead(i);
                Transition<MultiTapeDeterministicState> transition = transitions.get(i);
                Character replacingSymbol = transition.replacingSymbol();
                Movement movement = transition.movement();
                
                int offset = head.getOffset();
                int nextOffset = movement.move(offset);
                
                head.write(replacingSymbol);
                head.setOffset(nextOffset);
            }
            
            state = transitions.get(0).nextState();
            
            controlUnit.setInternalState(state);
            symbols = tapeHeads.getHeads().stream().map(ReadWriteHead::read).toList();
            transitions = state.transitions(symbols);
        }
        
        return controlUnit.isAccepted();
    }
}
