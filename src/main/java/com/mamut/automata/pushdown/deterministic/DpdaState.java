/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.contracts.State;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.pushdown.PushdownTransition;
import com.mamut.automata.util.Validators;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author Pc
 */
public class DpdaState implements State {
    private final boolean isFinalState;
    private final Map<Character, Map<Character, PushdownTransition<DpdaState>>> transitions;
    private Map<Character, PushdownTransition<DpdaState>> lambdaTransitions;
    
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
    
    public DpdaState addSelfLoop(char symbol, char storageSymbol, StorageOperation operation) {
        return addTransition(this, symbol, storageSymbol, operation);
    }
    
    public DpdaState addLambdaSelfLoop(char storageSymbol, StorageOperation operation) {
        return addLambdaTransition(this, storageSymbol, operation);
    }
    
    public DpdaState addTransition(DpdaState state, char symbol, char storageSymbol, StorageOperation operation) {
        Validators.ensureNonNull(state, operation);
        if (lambdaTransitions != null && lambdaTransitions.containsKey(storageSymbol)) {
            throw new IllegalStateException();
        }
        
        Map<Character, PushdownTransition<DpdaState>> storageSymbolBasedTransitions = transitions.get(storageSymbol);
        if (storageSymbolBasedTransitions == null) {
            storageSymbolBasedTransitions = new TreeMap<>();
            transitions.put(storageSymbol, storageSymbolBasedTransitions);
        }
        
        storageSymbolBasedTransitions.put(symbol, new PushdownTransition(state, operation));
        return this;
    }
    
    public DpdaState addLambdaTransition(DpdaState state, char storageSymbol, StorageOperation operation) {
        if (transitions.containsKey(storageSymbol)) {
            throw new IllegalStateException();
        }
        
        if (lambdaTransitions == null) {
            lambdaTransitions = new TreeMap<>();
        }
        
        lambdaTransitions.put(storageSymbol, new PushdownTransition(state, operation));
        return this;
    }
    
    public PushdownTransition<DpdaState> transition(char symbol, char storageSymbol) {
        Map<Character, PushdownTransition<DpdaState>> storageSymbolBasedTransitions = transitions.get(storageSymbol);
        if (storageSymbolBasedTransitions == null) {
            return null;
        }
        
        return storageSymbolBasedTransitions.get(symbol);
    }
    
    public PushdownTransition<DpdaState> lambdaTransition(char storageSymbol) {
        if (lambdaTransitions == null) {
            return null;
        }
        return lambdaTransitions.get(storageSymbol);
    }
}
