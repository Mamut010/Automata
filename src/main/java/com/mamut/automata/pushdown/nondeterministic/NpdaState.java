/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.nondeterministic;

import com.mamut.automata.pushdown.TransitionData;
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
    private final Map<Object, Map<Character, Set<TransitionData<NpdaState>>>> transitions;
    private Map<Object, Set<TransitionData<NpdaState>>> lambdaTransitions;
    
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
    
    public void addSelfLoop(char symbol, char storageSymbol, StorageOperation operation) {
        addTransition(this, symbol, storageSymbol, operation);
    }
    
    public void addLambdaSelfLoop(char storageSymbol, StorageOperation operation) {
        addLambdaTransition(this, storageSymbol, operation);
    }
    
    public void addTransition(NpdaState state, char symbol, char storageSymbol, StorageOperation operation) {
        addTransition(state, symbol, Character.valueOf(storageSymbol), operation);
    }
    
    public void addEpsilonTransition(NpdaState state, char symbol, StorageOperation operation) {
        addTransition(state, symbol, EPSILON, operation);
    }
    
    public void addLambdaTransition(NpdaState state, char storageSymbol, StorageOperation operation) {
        addLambdaTransition(state, Character.valueOf(storageSymbol), operation);
    }
    
    public void addEpsilonLambdaTransition(NpdaState state, StorageOperation operation) {
        addLambdaTransition(state, EPSILON, operation);
    }
    
    public Set<TransitionData<NpdaState>> transitions(char symbol, char storageSymbol) {
        return transitions(symbol, Character.valueOf(storageSymbol));
    }
    
    public Set<TransitionData<NpdaState>> epsilonTransitions(char symbol) {
        return transitions(symbol, EPSILON);
    }
    
    public Set<TransitionData<NpdaState>> lambdaTransitions(char storageSymbol) {
        return lambdaTransitions(Character.valueOf(storageSymbol));
    }
    
    public Set<TransitionData<NpdaState>> epsilonLambdaTransitions() {
        return lambdaTransitions(EPSILON);
    }
    
    private void addTransition(NpdaState state, char symbol, Object storageSymbol, StorageOperation operation) {
        Validators.ensureNonNull(state, storageSymbol, operation);

        Map<Character, Set<TransitionData<NpdaState>>> storageSymbolBasedTransitions = CollectionUtils.getOrCreateMap(
                transitions, storageSymbol);
        Set<TransitionData<NpdaState>> possibleTransitions = CollectionUtils.getOrCreateSet(storageSymbolBasedTransitions, 
                symbol);
        
        possibleTransitions.add(new TransitionData(state, operation));
    }
    
    private void addLambdaTransition(NpdaState state, Object storageSymbol, StorageOperation operation) {
        if (lambdaTransitions == null) {
            lambdaTransitions = new HashMap<>();
        }
        
        Set<TransitionData<NpdaState>> possibleTransitions = CollectionUtils.getOrCreateSet(lambdaTransitions, storageSymbol);
        possibleTransitions.add(new TransitionData(state, operation));
    }
    
    private Set<TransitionData<NpdaState>> transitions(char symbol, Object storageSymbol) {
        Map<Character, Set<TransitionData<NpdaState>>> storageSymbolBasedTransitions = transitions.getOrDefault(
                storageSymbol, Collections.EMPTY_MAP
        );
        return storageSymbolBasedTransitions.getOrDefault(symbol, Collections.EMPTY_SET);
    }
    
    private Set<TransitionData<NpdaState>> lambdaTransitions(Object storageSymbol) {
        if (lambdaTransitions == null) {
            return Collections.EMPTY_SET;
        }
        else {
            return lambdaTransitions.getOrDefault(storageSymbol, Collections.EMPTY_SET);
        }
    }
}
