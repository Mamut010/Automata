/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.nondeterministic;

import com.mamut.automata.contracts.State;
import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 *
 * @author Pc
 */
public final class NfaState implements State {
    private final boolean isFinalState;
    private final Map<Character, Set<NfaState>> transitions;
    private Set<NfaState> lambdaTransitions;
    
    public NfaState(boolean isFinalState) {
        this.isFinalState = isFinalState;
        transitions = new TreeMap<>();
        lambdaTransitions = null;
    }
    
    public NfaState() {
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
    
    public void addTransition(NfaState state, char symbol) {
        addTransitions(Set.of(state), symbol);
    }
    
    public void addTransitions(Set<NfaState> states, char symbol) {
        Set<NfaState> nextStates = getOrCreateNextStateSet(symbol);
        nextStates.addAll(states);
    }
    
    public void addLambdaTransition(NfaState state) {
        addLambdaTransitions(Set.of(state));
    }
    
    public void addLambdaTransitions(Set<NfaState> states) {
        if (lambdaTransitions == null) {
            lambdaTransitions = new HashSet<>();
        }
        
        lambdaTransitions.addAll(states);
    }
    
    public Set<NfaState> nextStates(char symbol) {
        Set<NfaState> next = transitions.get(symbol);
        if (next == null) {
            next = Set.of(this);
        }
        return next;
    }
    
    public Set<NfaState> lambdaTransitions() {
        return lambdaTransitions != null ? lambdaTransitions : Collections.EMPTY_SET;
    }
    
    private Set<NfaState> getOrCreateNextStateSet(char symbol) {
        Set<NfaState> nextStates = transitions.get(symbol);
        if (nextStates == null) {
            nextStates = new HashSet<>();
            transitions.put(symbol, nextStates);
        }
        return nextStates;
    }
}
