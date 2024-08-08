/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.turing.TuringTransitionConfig;
import com.mamut.automata.turing.Movement;
import com.mamut.automata.turing.MultiTapeState;
import com.mamut.automata.turing.Transition;
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
public class MdtmState implements MultiTapeState {
    private final Map<List<Character>, List<Transition<MdtmState>>> transitionMap;
    private int tapeCount;
    
    public MdtmState() {
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
    
    public MdtmState addSelfLoop(TuringTransitionConfig... configs) {
        return addTransition(this, configs);
    }
    
    public MdtmState addTransition(MdtmState state, TuringTransitionConfig... configs) {
        ensureValidTransition(state, configs);
        if (tapeCount != -1) {
            ensureCompatibleTapeCount(state, configs);
        }
        else {
            tapeCount = configs.length;
        }
        
        List<Character> symbols = new ArrayList<>();
        List<Transition<MdtmState>> transitions = new ArrayList<>();
        for (int i = 0; i < tapeCount; i++) {
            TuringTransitionConfig config = configs[i];
            Character symbol = config.symbol();
            Character replacingSymbol = config.replacingSymbol();
            Movement movement = config.movement();
            
            Transition<MdtmState> transition = new Transition<>(state, replacingSymbol, movement);
            
            symbols.add(symbol);
            transitions.add(transition);
        }
        
        transitionMap.put(symbols, transitions);
        
        return this;
    }
    
    public List<Transition<MdtmState>> transitions(List<Character> symbols) {
        return transitionMap.getOrDefault(symbols, Collections.EMPTY_LIST);
    }
    
    private void ensureValidTransition(MdtmState state, TuringTransitionConfig[] configs) {
        Validators.ensureNonNull(state, configs);
        Validators.ensureAllNonNull(configs);
        if (configs.length == 0) {
            throw new IllegalArgumentException("There must be at least one tape configuration");
        }
    }
    
    private void ensureCompatibleTapeCount(MdtmState state, TuringTransitionConfig[] configs) {
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

