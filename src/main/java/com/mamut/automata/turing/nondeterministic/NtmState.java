/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.contracts.State;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.Transition;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NtmState implements State {
    private final Map<Character, Set<Transition<NtmState>>> transitions;
    
    public NtmState() {
        transitions = new HashMap<>();
    }
    
    @Override
    public boolean isFinal() {
        return transitions.isEmpty();
    }
    
    public NtmState addSelfLoop(Character symbol, Character replacingSymbol, Movement movement) {
        return addTransition(this, symbol, replacingSymbol, movement);
    }
    
    public NtmState addTransition(NtmState state, Character symbol, Character replacingSymbol, Movement movement) {
        Validators.ensureNonNull(state, movement);
        
        Set<Transition<NtmState>> possibleTransitions = CollectionUtils.getOrPutNew(transitions, symbol, HashSet::new);
        Transition<NtmState> transition = new Transition<>(state, replacingSymbol, movement);
        possibleTransitions.add(transition);
        
        return this;
    }
    
    public Set<Transition<NtmState>> transitions(Character symbol) {
        return transitions.getOrDefault(symbol, Collections.EMPTY_SET);
    }
}
