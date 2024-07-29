/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.core;

import com.mamut.automata.contracts.MultiStatesControlUnit;
import com.mamut.automata.contracts.State;
import java.util.Set;

/**
 *
 * @author Pc
 * @param <T> The type of the State
 */
public class DefaultMultiStatesControlUnit<T extends State> implements MultiStatesControlUnit<T> {
    private final Set<T> initialStates;
    private Set<T> currentStates;
    
    public DefaultMultiStatesControlUnit(Set<T> initialStates) {
        if (initialStates == null || initialStates.isEmpty()) {
            throw new IllegalArgumentException();
        }
        
        this.initialStates = initialStates;
        currentStates = null;
    }
    
    public DefaultMultiStatesControlUnit(T initialState) {
        this(Set.of(initialState));
    }
    
    @Override
    public void setInternalStates(Set<T> states) {
        currentStates = states;
    }
    
    @Override
    public Set<T> getInternalStates() {
        return currentStates;
    }

    @Override
    public void initialize() {
        setInternalStates(initialStates);
    }

    @Override
    public boolean isAccepted() {
        return currentStates != null && currentStates.stream().anyMatch(State::isFinal);
    }
}
