/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.contracts.State;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.TuringTransition;
import com.mamut.automata.util.Validators;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Pc
 */
public class DtmState implements State {
    private final Map<Character, TuringTransition<DtmState>> transitions;
    
    public DtmState() {
        transitions = new HashMap<>();
    }
    
    @Override
    public boolean isFinal() {
        return transitions.isEmpty();
    }
    
    public DtmState addSelfLoop(Character symbol, Character replacingSymbol, Movement movement) {
        return addTransition(this, symbol, replacingSymbol, movement);
    }
    
    public DtmState addTransition(DtmState state, Character symbol, Character replacingSymbol, Movement movement) {
        Validators.ensureNonNull(state, movement);
        
        TuringTransition<DtmState> transition = new TuringTransition<>(state, replacingSymbol, movement);
        transitions.put(symbol, transition);
        return this;
    }
    
    public TuringTransition<DtmState> transition(Character symbol) {
        return transitions.get(symbol);
    }
}
