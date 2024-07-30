/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.contracts.State;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.pushdown.Transition;
import com.mamut.automata.util.Validators;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Pc
 */
public final class DpdaState implements State {
    private final boolean isFinalState;
    private final Map<Character, Map<Character, Transition<DpdaState>>> transitions;
    private Map<Character, Transition<DpdaState>> lambdaTransitions;
    
    public DpdaState(boolean isFinalState) {
        this.isFinalState = isFinalState;
        transitions = new TreeMap<>();
        lambdaTransitions = null;
    }
    
    public DpdaState() {
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
    
    public void addTransition(DpdaState state, char symbol, char storageSymbol, StorageOperation operation) {
        Validators.ensureNonNull(state, operation);
        if (lambdaTransitions != null && lambdaTransitions.containsKey(storageSymbol)) {
            throw new IllegalStateException();
        }
        
        Map<Character, Transition<DpdaState>> storageSymbolBasedTransitions = transitions.get(storageSymbol);
        if (storageSymbolBasedTransitions == null) {
            storageSymbolBasedTransitions = new TreeMap<>();
            transitions.put(storageSymbol, storageSymbolBasedTransitions);
        }
        
        storageSymbolBasedTransitions.put(symbol, new Transition(state, operation));
    }
    
    public void addLambdaTransition(DpdaState state, char storageSymbol, StorageOperation operation) {
        if (transitions.containsKey(storageSymbol)) {
            throw new IllegalStateException();
        }
        
        if (lambdaTransitions == null) {
            lambdaTransitions = new TreeMap<>();
        }
        
        lambdaTransitions.put(storageSymbol, new Transition(state, operation));
    }
    
    public Transition<DpdaState> transition(char symbol, char storageSymbol) {
        Map<Character, Transition<DpdaState>> storageSymbolBasedTransitions = transitions.get(storageSymbol);
        if (storageSymbolBasedTransitions == null) {
            return null;
        }
        
        return storageSymbolBasedTransitions.get(symbol);
    }
    
    public Transition<DpdaState> lambdaTransition(char storageSymbol) {
        if (lambdaTransitions == null) {
            return null;
        }
        return lambdaTransitions.get(storageSymbol);
    }
}
