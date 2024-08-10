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
import com.mamut.automata.turing.MultiTapeHeadCollection;
import com.mamut.automata.turing.Transition;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class MultiTapeNondeterministicTuringMachine extends AbstractMultiTapeTuringMachine<MntmState> {
    public MultiTapeNondeterministicTuringMachine(MultiTapeHeadCollection tapeHeads, ControlUnit<MntmState> controlUnit) {
        super(tapeHeads, controlUnit);
    }

    @Override
    protected boolean testImpl() {
        return testImpl(new HashSet<>());
    }
    
    private boolean testImpl(Set<List<Configuration<MntmState>>> visited) {
        List<Configuration<MntmState>> configs = getCurrentConfigurations();
        if (!visited.add(configs)) {
            return false;
        }
        
        MntmState state = controlUnit.getInternalState();
        List<Character> symbols = getHeadStream().map(ReadWriteHead::read).toList();
        List<Integer> offsets = getHeadStream().map(ReadWriteHead::getOffset).toList();
        Set<List<Transition<MntmState>>> possibleTransitions = state.transitions(symbols);
        
        for (List<Transition<MntmState>> transitions : possibleTransitions) {
            if (testTransitions(visited, transitions)) {
                return true;
            }
            revertChanges(symbols, offsets, state);
        }
        
        return controlUnit.isAccepted();
    }
    
    private boolean testTransitions(Set<List<Configuration<MntmState>>> visited, List<Transition<MntmState>> transitions) {
        for (int i = 0; i < tapeHeads.getTapeCount(); i++) {
            ReadWriteHead head = tapeHeads.getHead(i);
            Transition<MntmState> transition = transitions.get(i);
            Character replacingSymbol = transition.replacingSymbol();
            Movement movement = transition.movement();

            int offset = head.getOffset();
            int nextOffset = movement.move(offset);

            head.write(replacingSymbol);
            head.setOffset(nextOffset);
        }
        
        MntmState state = transitions.get(0).nextState();
        controlUnit.setInternalState(state);
        
        return testImpl(visited);
    }
    
    private void revertChanges(List<Character> previousSymbols, List<Integer> previousOffsets, MntmState previousState) {
        controlUnit.setInternalState(previousState);
        
        for (int i = 0; i < tapeHeads.getTapeCount(); i++) {
            ReadWriteHead head = tapeHeads.getHead(i);
            Character previousSymbol = previousSymbols.get(i);
            int previousOffset = previousOffsets.get(i);
            
            head.setOffset(previousOffset);
            head.write(previousSymbol);
        }
    }
}
