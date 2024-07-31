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
public class NfaState implements State {
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
    
    public NfaState addSelfLoop(char... symbols) {
        for(char symbol : symbols) {
            addTransition(this, symbol);
        }
        return this;
    }
    
    public NfaState addTransition(NfaState state, char symbol) {
        return addTransitions(Set.of(state), symbol);
    }
    
    public NfaState addTransitions(Set<NfaState> states, char symbol) {
        Set<NfaState> nextStates = getOrCreateNextStateSet(symbol);
        nextStates.addAll(states);
        return this;
    }
    
    public NfaState addLambdaTransition(NfaState state) {
        return addLambdaTransitions(Set.of(state));
    }
    
    public NfaState addLambdaTransitions(Set<NfaState> states) {
        if (lambdaTransitions == null) {
            lambdaTransitions = new HashSet<>();
        }
        
        lambdaTransitions.addAll(states);
        return this;
    }
    
    public Set<NfaState> nextStates(char symbol) {
        return transitions.getOrDefault(symbol, Collections.EMPTY_SET);
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
