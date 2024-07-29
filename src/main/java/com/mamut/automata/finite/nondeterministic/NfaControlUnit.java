/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.nondeterministic;

import com.mamut.automata.contracts.ControlUnit;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NfaControlUnit implements ControlUnit {
    private final Set<NfaState> initialStates;
    private Set<NfaState> currentStates;
    
    public NfaControlUnit(Set<NfaState> initialStates) {
        if (initialStates == null || initialStates.isEmpty()) {
            throw new IllegalArgumentException();
        }
        
        this.initialStates = initialStates;
        currentStates = null;
    }
    
    public NfaControlUnit(NfaState initialState) {
        this(Set.of(initialState));
    }
    
    public void setInternalStates(Set<NfaState> states) {
        currentStates = states;
    }
    
    public Set<NfaState> getInternalStates() {
        return currentStates;
    }

    @Override
    public void initialize() {
        setInternalStates(initialStates);
    }

    @Override
    public boolean isAccepted() {
        return currentStates != null && currentStates.stream().anyMatch(NfaState::isFinal);
    }
}
