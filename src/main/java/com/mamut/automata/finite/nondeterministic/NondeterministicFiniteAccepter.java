/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.finite.nondeterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NondeterministicFiniteAccepter implements Accepter {
    private final InputMechanism inputMechanism;
    private final NfaControlUnit controlUnit;
    
    public NondeterministicFiniteAccepter(InputMechanism inputMechanism, NfaControlUnit controlUnit) {
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
        
        Set<NfaState> currentStates = moveControlUnitToReachableStates();
        
        while (!inputMechanism.isEOF() && !currentStates.isEmpty()) {
            char symbol = inputMechanism.advance();
            Set<NfaState> nextStates = getDirectNextStates(currentStates, symbol);
            controlUnit.setInternalStates(nextStates);
            currentStates = moveControlUnitToReachableStates();
        }
        
        return controlUnit.isAccepted();
    }
    
    private Set<NfaState> moveControlUnitToReachableStates() {
        Set<NfaState> currentStates = controlUnit.getInternalStates();
        Set<NfaState> directLambdaTransitions = getDirectLambdaTransitions(currentStates);
        if (directLambdaTransitions.isEmpty()) {
            return currentStates;
        }
        
        Set<NfaState> reachableStates = new HashSet<>(currentStates);
        
        while (!directLambdaTransitions.isEmpty()) {
            int oldStateCount = reachableStates.size();
            reachableStates.addAll(directLambdaTransitions);
            int newStateCount = reachableStates.size();
            if (newStateCount == oldStateCount) {
                break;
            }
            directLambdaTransitions = getDirectLambdaTransitions(directLambdaTransitions);
        }
        
        controlUnit.setInternalStates(reachableStates);
        return reachableStates;
    }
    
    private static Set<NfaState> getDirectNextStates(Set<NfaState> currentStates, char symbol) {
        return CollectionUtils.flatMapToSet(currentStates, state -> state.nextStates(symbol));
    }
    
    private static Set<NfaState> getDirectLambdaTransitions(Set<NfaState> states) {
        return CollectionUtils.flatMapToSet(states, NfaState::lambdaTransitions);
    }
}
