/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.finite.deterministic;

import com.mamut.automata.contracts.State;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Pc
 */
public final class DfaState implements State {
    private final boolean isFinalState;
    private final Map<Character, DfaState> transitions;
    
    public DfaState(boolean isFinalState) {
        this.isFinalState = isFinalState;
        transitions = new TreeMap<>();
    }
    
    public DfaState() {
        this(false);
    }
    
    @Override
    public boolean isFinal() {
        return isFinalState;
    }
    
    public void addSelfLoop(char... symbols) {
        for(char symbol : symbols) {
            addTransition(this, symbol);
        }
    }
    
    public void addTransition(DfaState state, char symbol) {
        transitions.put(symbol, state);
    }
    
    public DfaState nextState(char symbol) {
        DfaState next = transitions.get(symbol);
        if (next == null)  {
            next = this;
        }
        return next;
    }
}
