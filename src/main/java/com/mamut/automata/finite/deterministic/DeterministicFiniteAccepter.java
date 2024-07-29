/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.deterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public class DeterministicFiniteAccepter implements Accepter {
    private final InputMechanism inputMechanism;
    private final DfaControlUnit controlUnit;
    
    public DeterministicFiniteAccepter(InputMechanism inputMechanism, DfaControlUnit controlUnit) {
        Validators.ensureNonNull(inputMechanism, controlUnit);
        
        this.inputMechanism = inputMechanism;
        this.controlUnit = controlUnit;
    }

    @Override
    public boolean test(String inputFile) {
        if (!inputMechanism.loadInputFile(inputFile)) {
            return false;
        }
        controlUnit.initialize();
        
        while (!inputMechanism.isEOF()) {
            char symbol = inputMechanism.advance();
            DfaState currentState = controlUnit.getInternalState();
            
            DfaState nextState = currentState.nextState(symbol);
            if (nextState == null) {
                return false;
            }
            
            controlUnit.setInternalState(nextState);
        }
        
        return controlUnit.isAccepted();
    }
}
