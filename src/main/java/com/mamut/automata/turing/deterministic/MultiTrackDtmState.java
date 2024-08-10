/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeDeterministicState;
import com.mamut.automata.turing.MultiTrackTuringTransitionConfig;
import com.mamut.automata.turing.TuringTransition;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pc
 */
public class MultiTrackDtmState implements MultiTapeDeterministicState<MultiTrackDtmState> {
    private final Map<List<Character>, List<TuringTransition<MultiTrackDtmState>>> transitionMap;
    private int tapeCount;
    
    public MultiTrackDtmState() {
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
    
    public MultiTrackDtmState addSelfLoop(Movement movement, MultiTrackTuringTransitionConfig... configs) {
        return addTransition(this, movement, configs);
    }
    
    public MultiTrackDtmState addTransition(MultiTrackDtmState state, Movement movement, MultiTrackTuringTransitionConfig... configs) {
        guardTransition(state, configs);
        
        List<Character> symbols = new ArrayList<>();
        List<TuringTransition<MultiTrackDtmState>> transitions = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++) {
            MultiTrackTuringTransitionConfig config = configs[i];
            Character symbol = config.symbol();
            Character replacingSymbol = config.replacingSymbol();
            
            TuringTransition<MultiTrackDtmState> transition = new TuringTransition<>(state, replacingSymbol, movement);
            
            symbols.add(symbol);
            transitions.add(transition);
        }
        
        transitionMap.put(symbols, transitions);
        
        return this;
    }
    
    @Override
    public List<TuringTransition<MultiTrackDtmState>> transitions(List<Character> symbols) {
        return transitionMap.getOrDefault(symbols, Collections.EMPTY_LIST);
    }
    
    private void guardTransition(MultiTrackDtmState state, MultiTrackTuringTransitionConfig[] configs) {
        ensureValidTransition(state, configs);
        if (tapeCount != -1) {
            ensureCompatibleTapeCount(state, configs);
        }
        else {
            tapeCount = configs.length;
        }
    }
    
    private void ensureValidTransition(MultiTrackDtmState state, MultiTrackTuringTransitionConfig[] configs) {
        Validators.ensureNonNull(state, configs);
        Validators.ensureAllNonNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("There must be at least one track configuration");
        }
    }
    
    private void ensureCompatibleTapeCount(MultiTrackDtmState state, MultiTrackTuringTransitionConfig[] configs) {
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
