/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeNondeterministicState;
import com.mamut.automata.turing.MultiTrackTuringTransitionConfig;
import com.mamut.automata.turing.TuringTransition;
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
public class MultiTrackNtmState implements MultiTapeNondeterministicState<MultiTrackNtmState> {
    private final Map<List<Character>, Set<List<TuringTransition<MultiTrackNtmState>>>> transitionMap;
    private int tapeCount;
    
    public MultiTrackNtmState() {
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
    
    public MultiTrackNtmState addSelfLoop(Movement movement, MultiTrackTuringTransitionConfig... configs) {
        return addTransition(this, movement, configs);
    }
    
    public MultiTrackNtmState addTransition(MultiTrackNtmState state, Movement movement, MultiTrackTuringTransitionConfig... configs) {
        guardTransition(state, configs);
        
        List<Character> symbols = new ArrayList<>();
        List<TuringTransition<MultiTrackNtmState>> transitions = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++) {
            MultiTrackTuringTransitionConfig config = configs[i];
            Character symbol = config.symbol();
            Character replacingSymbol = config.replacingSymbol();
            
            TuringTransition<MultiTrackNtmState> transition = new TuringTransition<>(state, replacingSymbol, movement);
            
            symbols.add(symbol);
            transitions.add(transition);
        }
        
        Set<List<TuringTransition<MultiTrackNtmState>>> possibleTransitions = CollectionUtils.getOrPutNew(transitionMap,
                symbols, HashSet::new);
        possibleTransitions.add(transitions);
        
        return this;
    }
    
    @Override
    public Set<List<TuringTransition<MultiTrackNtmState>>> transitions(List<Character> symbols) {
        return transitionMap.getOrDefault(symbols, Collections.EMPTY_SET);
    }
    
    private void guardTransition(MultiTrackNtmState state, MultiTrackTuringTransitionConfig[] configs) {
        ensureValidTransition(state, configs);
        if (tapeCount != -1) {
            ensureCompatibleTapeCount(state, configs);
        }
        else {
            tapeCount = configs.length;
        }
    }
    
    private void ensureValidTransition(MultiTrackNtmState state, MultiTrackTuringTransitionConfig[] configs) {
        Validators.ensureNonNull(state, configs);
        Validators.ensureAllNonNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("There must be at least one track configuration");
        }
    }
    
    private void ensureCompatibleTapeCount(MultiTrackNtmState state, MultiTrackTuringTransitionConfig[] configs) {
        if (configs.length != tapeCount) {
            throw new IllegalArgumentException("Incompatible number of tracks and number of track configurations");
        }
        
        if (state == this) {
            return;
        }
        
        if (state.tapeCount != -1 && state.tapeCount != tapeCount) {
            throw new IllegalArgumentException("Incompatible next state with different number of tracks");
        }
        else {
            state.tapeCount = tapeCount;
        }
    }
}
