/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.nondeterministic;

import com.mamut.automata.pushdown.Transition;
import com.mamut.automata.contracts.State;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NpdaState implements State {
    private static final Object EPSILON = new Object();
    
    private final boolean isFinalState;
    private final Map<Object, Map<Character, Set<Transition<NpdaState>>>> transitions;
    private Map<Object, Set<Transition<NpdaState>>> lambdaTransitions;
    
    public NpdaState(boolean isFinalState) {
        this.isFinalState = isFinalState;
        transitions = new HashMap<>();
        lambdaTransitions = null;
    }
    
    public NpdaState() {
        this(false);
    }
    
    @Override
    public boolean isFinal() {
        return isFinalState;
    }
    
    public NpdaState addSelfLoop(char symbol, char storageSymbol, StorageOperation operation) {
        return addTransition(this, symbol, storageSymbol, operation);
    }
    
    public NpdaState addLambdaSelfLoop(char storageSymbol, StorageOperation operation) {
        return addLambdaTransition(this, storageSymbol, operation);
    }
    
    public NpdaState addTransition(NpdaState state, char symbol, char storageSymbol, StorageOperation operation) {
        return addTransition(state, symbol, Character.valueOf(storageSymbol), operation);
    }
    
    public NpdaState addEpsilonTransition(NpdaState state, char symbol, StorageOperation operation) {
        return addTransition(state, symbol, EPSILON, operation);
    }
    
    public NpdaState addLambdaTransition(NpdaState state, char storageSymbol, StorageOperation operation) {
        return addLambdaTransition(state, Character.valueOf(storageSymbol), operation);
    }
    
    public NpdaState addEpsilonLambdaTransition(NpdaState state, StorageOperation operation) {
        return addLambdaTransition(state, EPSILON, operation);
    }
    
    public Set<Transition<NpdaState>> transitions(char symbol, char storageSymbol) {
        return transitions(symbol, Character.valueOf(storageSymbol));
    }
    
    public Set<Transition<NpdaState>> epsilonTransitions(char symbol) {
        return transitions(symbol, EPSILON);
    }
    
    public Set<Transition<NpdaState>> lambdaTransitions(char storageSymbol) {
        return lambdaTransitions(Character.valueOf(storageSymbol));
    }
    
    public Set<Transition<NpdaState>> epsilonLambdaTransitions() {
        return lambdaTransitions(EPSILON);
    }
    
    private NpdaState addTransition(NpdaState state, char symbol, Object storageSymbol, StorageOperation operation) {
        Validators.ensureNonNull(state, storageSymbol, operation);

        Map<Character, Set<Transition<NpdaState>>> storageSymbolBasedTransitions = CollectionUtils.getOrCreateMap(
                transitions, storageSymbol);
        Set<Transition<NpdaState>> possibleTransitions = CollectionUtils.getOrCreateSet(storageSymbolBasedTransitions, 
                symbol);
        
        possibleTransitions.add(new Transition(state, operation));
        return this;
    }
    
    private NpdaState addLambdaTransition(NpdaState state, Object storageSymbol, StorageOperation operation) {
        if (lambdaTransitions == null) {
            lambdaTransitions = new HashMap<>();
        }
        
        Set<Transition<NpdaState>> possibleTransitions = CollectionUtils.getOrCreateSet(lambdaTransitions, storageSymbol);
        possibleTransitions.add(new Transition(state, operation));
        return this;
    }
    
    private Set<Transition<NpdaState>> transitions(char symbol, Object storageSymbol) {
        Map<Character, Set<Transition<NpdaState>>> storageSymbolBasedTransitions = transitions.getOrDefault(
                storageSymbol, Collections.EMPTY_MAP
        );
        return storageSymbolBasedTransitions.getOrDefault(symbol, Collections.EMPTY_SET);
    }
    
    private Set<Transition<NpdaState>> lambdaTransitions(Object storageSymbol) {
        if (lambdaTransitions == null) {
            return Collections.EMPTY_SET;
        }
        else {
            return lambdaTransitions.getOrDefault(storageSymbol, Collections.EMPTY_SET);
        }
    }
}
