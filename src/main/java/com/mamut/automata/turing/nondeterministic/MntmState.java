/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeNondeterministicState;
import com.mamut.automata.turing.Transition;
import com.mamut.automata.turing.TuringTransitionConfig;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class MntmState implements MultiTapeNondeterministicState<MntmState> {
    private final Map<List<Character>, Set<List<Transition<MntmState>>>> transitionMap;
    private int tapeCount;
    
    public MntmState() {
        tapeCount = -1;
        transitionMap = new HashMap<>();
    }
    
    @Override
    public boolean isFinal() {
        return transitionMap.isEmpty();
    }
    
    @Override
    public int getTapeCount() {
        return tapeCount != -1 ? tapeCount : 0;
    }
    
    public MntmState addSelfLoop(TuringTransitionConfig... configs) {
        return addTransition(this, configs);
    }
    
    public MntmState addTransition(MntmState state, TuringTransitionConfig... configs) {
        guardTransition(state, configs);
        
        List<Character> symbols = new ArrayList<>();
        List<Transition<MntmState>> transitions = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++) {
            TuringTransitionConfig config = configs[i];
            Character symbol = config.symbol();
            Character replacingSymbol = config.replacingSymbol();
            Movement movement = config.movement();
            
            Transition<MntmState> transition = new Transition<>(state, replacingSymbol, movement);
            
            symbols.add(symbol);
            transitions.add(transition);
        }
        
        Set<List<Transition<MntmState>>> possibleTransitions = CollectionUtils.getOrPutNew(transitionMap, symbols, HashSet::new);
        possibleTransitions.add(transitions);
        
        return this;
    }
    
    @Override
    public Set<List<Transition<MntmState>>> transitions(List<Character> symbols) {
        return transitionMap.getOrDefault(symbols, Collections.EMPTY_SET);
    }
    
    private void guardTransition(MntmState state, TuringTransitionConfig[] configs) {
        ensureValidTransition(state, configs);
        if (tapeCount != -1) {
            ensureCompatibleTapeCount(state, configs);
        }
        else {
            tapeCount = configs.length;
        }
    }
    
    private void ensureValidTransition(MntmState state, TuringTransitionConfig[] configs) {
        Validators.ensureNonNull(state, configs);
        Validators.ensureAllNonNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("There must be at least one tape configuration");
        }
    }
    
    private void ensureCompatibleTapeCount(MntmState state, TuringTransitionConfig[] configs) {
        if (configs.length != tapeCount) {
            throw new IllegalArgumentException("Incompatible number of tapes and number of tape configurations");
        }
        
        if (state == this) {
            return;
        }
        
        if (state.tapeCount != -1 && state.tapeCount != tapeCount) {
            throw new IllegalArgumentException("Incompatible next state with different number of tapes");
        }
        else {
            state.tapeCount = tapeCount;
        }
    }
}
