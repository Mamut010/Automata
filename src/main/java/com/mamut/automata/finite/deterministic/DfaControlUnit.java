/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.deterministic;

import com.mamut.automata.contracts.ControlUnit;

/**
 *
 * @author Pc
 */
public class DfaControlUnit implements ControlUnit {
    private final DfaState initialState;
    private DfaState currentState;
    
    public DfaControlUnit(DfaState initialState) {
        if (initialState == null) {
            throw new IllegalArgumentException();
        }
        
        this.initialState = initialState;
        currentState = null;
    }
    
    public void setInternalState(DfaState state) {
        currentState = state;
    }
    
    public DfaState getInternalState() {
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
