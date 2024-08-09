/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

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
public class MultiTrackTuringMachine extends AbstractMultiTrackTuringMachine<MtdtmState> {
    public MultiTrackTuringMachine(TapeOrderedCollection tapes, MultiTrackReadWriteHead head, ControlUnit<MtdtmState> controlUnit) {
        super(tapes, head, controlUnit);
    }

    @Override
    protected boolean testImpl() {
        MtdtmState state = controlUnit.getInternalState();
        List<Character> symbols = head.read();
        MultiTrackTransition<MtdtmState> transition = state.transition(symbols);
        Set<MultiTrackConfiguration<MtdtmState>> visited = new HashSet<>();
        
        while (transition != null) {
            MultiTrackConfiguration<MtdtmState> config = getCurrentConfiguration();
            if (!visited.add(config)) {
                return false;
            }
            
            state = transition.nextState();
            List<Character> replacingSymbols = transition.replacingSymbols();
            Movement movement = transition.movement();
            int offset = head.getOffset();
            int nextOffset = movement.move(offset);
            
            head.write(replacingSymbols);
            head.setOffset(nextOffset);
            controlUnit.setInternalState(state);
            
            symbols = head.read();
            transition = state.transition(symbols);
        }
        
        return controlUnit.isAccepted();
    }
}
