/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.nondeterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.util.CollectionUtils;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NondeterministicFiniteAccepter implements Accepter {
    private final InputMechanism inputMechanism;
    private final NfaControlUnit controlUnit;
    
    public NondeterministicFiniteAccepter(InputMechanism inputMechanism, NfaControlUnit controlUnit) {
        if (inputMechanism == null || controlUnit == null) {
            throw new IllegalArgumentException();
        }
        
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
            Set<NfaState> currentStates = controlUnit.getInternalStates();
            Set<NfaState> nextStates = CollectionUtils.flatMapToSet(currentStates, state -> state.nextStates(symbol));
            controlUnit.setInternalStates(nextStates);
        }
        
        return controlUnit.isAccepted();
    }
}
