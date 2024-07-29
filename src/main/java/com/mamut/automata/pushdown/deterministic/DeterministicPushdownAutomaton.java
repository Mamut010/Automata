/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.pushdown.TransitionData;
import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.pushdown.PdaStorageDevice;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public class DeterministicPushdownAutomaton implements Accepter {
    private final InputMechanism inputMechanism;
    private final DpdaControlUnit controlUnit;
    private final PdaStorageDevice storage;
    
    public DeterministicPushdownAutomaton(
            InputMechanism inputMechanism,
            DpdaControlUnit controlUnit,
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
        if (storage.isEmpty() || !processLambdaTransition()) {
            return false;
        }
        
        while (!inputMechanism.isEOF() && !storage.isEmpty()) {
            char symbol = inputMechanism.advance();
            DpdaState currentState = controlUnit.getInternalState();
            char storageSymbol = storage.peek();
            
            TransitionData transition = currentState.transition(symbol, storageSymbol);
            if (transition == null) {
                return false;
            }
            
            StorageOperation operation = transition.operation();
            if (operation == null) {
                return false;
            }
            
            operation.execute(storage);
            controlUnit.setInternalState(transition.state());
            
            if (!processLambdaTransition()) {
                return false;
            }
        }
        
        return controlUnit.isAccepted();
    }
    
    private boolean processLambdaTransition() {
        if (storage.isEmpty()) {
            return true;
        }
        
        DpdaState currentState = controlUnit.getInternalState();
        char storageSymbol = storage.peek();
        
        TransitionData lambdaTransition = currentState.lambdaTransition(storageSymbol);
        while (lambdaTransition != null) {
            StorageOperation operation = lambdaTransition.operation();
            if (operation == null) {
                return false;
            }
            
            operation.execute(storage);
            
            currentState = lambdaTransition.state();
            controlUnit.setInternalState(currentState);
            lambdaTransition = currentState.lambdaTransition(storageSymbol);
        }
        
        return true;
    }
}
