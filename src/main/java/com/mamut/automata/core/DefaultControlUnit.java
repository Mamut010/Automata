/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.core;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.State;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 * @param <T> The type of the state
 */
public class DefaultControlUnit<T extends State> implements ControlUnit<T> {
    private final T initialState;
    private T currentState;
    
    public DefaultControlUnit(T initialState) {
        Validators.ensureNonNull(initialState);
        this.initialState = initialState;
        currentState = null;
    }
    
    @Override
    public void setInternalState(T state) {
        currentState = state;
    }
    
    @Override
    public T getInternalState() {
        return currentState;
    }

    @Override
    public void initialize() {
        currentState = initialState;
    }

    @Override
    public boolean isAccepted() {
        return currentState != null && currentState.isFinal();
    }
}
