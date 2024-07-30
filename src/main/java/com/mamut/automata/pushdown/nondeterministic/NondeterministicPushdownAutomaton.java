/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.nondeterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.BacktrackableInputMechanism;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.pushdown.PdaStorageDevice;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.pushdown.Transition;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NondeterministicPushdownAutomaton implements Accepter {
    private record Configuration(NpdaState state, int inputMechanismPosition, String storageSnapshot) {}
    
    private final BacktrackableInputMechanism inputMechanism;
    private final ControlUnit<NpdaState> controlUnit;
    private final PdaStorageDevice storage;
    
    public NondeterministicPushdownAutomaton(
            BacktrackableInputMechanism inputMechanism,
            ControlUnit<NpdaState> controlUnit,
            PdaStorageDevice storage
    ) {
        Validators.ensureNonNull(inputMechanism, controlUnit, storage);
        
        this.inputMechanism = inputMechanism;
        this.controlUnit = controlUnit;
        this.storage = storage;
    }

    @Override
    public boolean test(String inputFile) {
        if (!inputMechanism.loadInputFile(inputFile)) {
            return false;
        }
        controlUnit.initialize();
        storage.initialize();
        
        return testImpl(new HashSet<>());
    }
    
    private boolean testImpl(Set<Configuration> visited) {
        Configuration currentConfiguration = getCurrentConfiguration();
        if (!visited.add(currentConfiguration)) {
            return false;
        }

        boolean result;
        try {
            if (inputMechanism.isEOF()) {
                result = handleEOF(visited);
            }
            else if (checkLambdaTransitions(visited)) {
                result = true;
            }
            else {
                result = checkNormalTransitions(visited);
            }

            return result;
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    private boolean handleEOF(Set<Configuration> visited) {
        return controlUnit.isAccepted() || checkLambdaTransitions(visited);
    }
    
    private boolean checkLambdaTransitions(Set<Configuration> visited) {
        NpdaState currentState = controlUnit.getInternalState();
        char storageSymbol = storage.peek();
        
        Set<Transition<NpdaState>> lambdaTransitions = CollectionUtils.union(
                currentState.lambdaTransitions(storageSymbol), 
                currentState.epsilonLambdaTransitions()
        );
        
        for (Transition<NpdaState> lambdaTransition : lambdaTransitions) {
            NpdaState nextState = lambdaTransition.nextState();
            StorageOperation operation = lambdaTransition.operation();
            
            operation.execute(storage);
            controlUnit.setInternalState(nextState);
            
            if (testImpl(visited)) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            operation.revert(storage);
        }
        
        return false;
    }
    
    private boolean checkNormalTransitions(Set<Configuration> visited) {
        inputMechanism.markPosition();
        
        char symbol = inputMechanism.advance();
        NpdaState currentState = controlUnit.getInternalState();
        char storageSymbol = storage.peek();
        
        Set<Transition<NpdaState>> transitions = CollectionUtils.union(
                currentState.transitions(symbol, storageSymbol),
                currentState.epsilonTransitions(symbol)
        );
        
        for (Transition<NpdaState> transition : transitions) {
            NpdaState nextState = transition.nextState();
            StorageOperation operation = transition.operation();
            
            operation.execute(storage);
            controlUnit.setInternalState(nextState);
            
            if (testImpl(visited)) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            operation.revert(storage);
        }
        
        inputMechanism.returnToLastMarkedPosition();
        return false;
    }
    
    private Configuration getCurrentConfiguration() {
        NpdaState currentState = controlUnit.getInternalState();
        int position = inputMechanism.getPosition();
        String storageSnapshot = storage.snapshot();
        return new Configuration(currentState, position, storageSnapshot);
    }
}
