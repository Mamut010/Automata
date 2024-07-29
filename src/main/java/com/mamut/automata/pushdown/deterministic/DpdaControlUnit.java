/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public class DpdaControlUnit implements ControlUnit {
    private final DpdaState initialState;
    private DpdaState currentState;
    
    public DpdaControlUnit(DpdaState initialState) {
        Validators.ensureNonNull(initialState);
        this.initialState = initialState;
        currentState = null;
    }
    
    public void setInternalState(DpdaState state) {
        currentState = state;
    }
    
    public DpdaState getInternalState() {
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
