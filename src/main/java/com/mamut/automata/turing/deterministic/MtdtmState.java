/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeState;
import com.mamut.automata.turing.MultiTrackTransition;
import com.mamut.automata.turing.MultiTrackTuringTransitionConfig;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Pc
 */
public class MtdtmState implements MultiTapeState {
    private final Map<List<Character>, MultiTrackTransition<MtdtmState>> transitions;
    private int tapeCount;
    
    public MtdtmState() {
        tapeCount = -1;
        transitions = new HashMap<>();
    }
    
    @Override
    public boolean isFinal() {
        return transitions.isEmpty();
    }
    
    @Override
    public int getTapeCount() {
        return tapeCount != -1 ? tapeCount : 0;
    }
    
    public MtdtmState addSelfLoop(Movement movement, MultiTrackTuringTransitionConfig... configs) {
        return addTransition(this, movement, configs);
    }
    
    public MtdtmState addTransition(MtdtmState state, Movement movement, MultiTrackTuringTransitionConfig... configs) {
        guardTransition(state, configs);
        
        List<Character> symbols = new ArrayList<>();
        List<Character> replacingSymbols = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++) {
            MultiTrackTuringTransitionConfig config = configs[i];
            Character symbol = config.symbol();
            Character replacingSymbol = config.replacingSymbol();
            
            symbols.add(symbol);
            replacingSymbols.add(replacingSymbol);
        }
        
        MultiTrackTransition<MtdtmState> transition = new MultiTrackTransition<>(state, movement, replacingSymbols);
        transitions.put(symbols, transition);
        
        return this;
    }
    
    public MultiTrackTransition<MtdtmState> transition(List<Character> symbols) {
        return transitions.getOrDefault(symbols, null);
    }
    
    private void guardTransition(MtdtmState state, MultiTrackTuringTransitionConfig[] configs) {
        ensureValidTransition(state, configs);
        if (tapeCount != -1) {
            ensureCompatibleTapeCount(state, configs);
        }
        else {
            tapeCount = configs.length;
        }
    }
    
    private void ensureValidTransition(MtdtmState state, MultiTrackTuringTransitionConfig[] configs) {
        Validators.ensureNonNull(state, configs);
        Validators.ensureAllNonNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("There must be at least one track configuration");
        }
    }
    
    private void ensureCompatibleTapeCount(MtdtmState state, MultiTrackTuringTransitionConfig[] configs) {
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
