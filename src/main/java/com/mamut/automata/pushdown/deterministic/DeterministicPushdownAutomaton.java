/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.pushdown.PdaStorageDevice;
import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.pushdown.PushdownTransition;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public class DeterministicPushdownAutomaton implements Accepter {
    private final InputMechanism inputMechanism;
    private final ControlUnit<DpdaState> controlUnit;
    private final PdaStorageDevice storage;
    
    public DeterministicPushdownAutomaton(
            InputMechanism inputMechanism,
            ControlUnit<DpdaState> controlUnit,
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
        
        try {
            return testImpl();
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    private boolean testImpl() {
        if (!processLambdaTransition()) {
            return false;
        }
        
        while (!inputMechanism.isEOF()) {
            char symbol = inputMechanism.advance();
            DpdaState currentState = controlUnit.getInternalState();
            char storageSymbol = storage.peek();

            PushdownTransition<DpdaState> transition = currentState.transition(symbol, storageSymbol);
            if (transition == null) {
                return false;
            }

            StorageOperation operation = transition.operation();
            if (operation == null) {
                return false;
            }

            DpdaState nextState = transition.nextState();
            operation.execute(storage);
            controlUnit.setInternalState(nextState);

            if (!processLambdaTransition()) {
                return false;
            }
        }
        
        return controlUnit.isAccepted();
    }
    
    private boolean processLambdaTransition() {
        DpdaState currentState = controlUnit.getInternalState();
        char storageSymbol = storage.peek();
        PushdownTransition<DpdaState> lambdaTransition = currentState.lambdaTransition(storageSymbol);
        PushdownTransition<DpdaState> previousLambdaTransition = null;
        
        int iterationCount = 0;
        final int ITERATION_LIMIT = 1 << 16;
        
        while (lambdaTransition != null && !lambdaTransition.equals(previousLambdaTransition)) {
            iterationCount++;
            if (iterationCount > ITERATION_LIMIT) {
                System.err.println("Warning: Exceeded lambda transition iteration limit");
                return false;
            }
            
            StorageOperation operation = lambdaTransition.operation();
            if (operation == null) {
                return false;
            }
            
            operation.execute(storage);
            currentState = lambdaTransition.nextState();
            controlUnit.setInternalState(currentState);
            
            storageSymbol = storage.peek();
            previousLambdaTransition = lambdaTransition;
            lambdaTransition = currentState.lambdaTransition(storageSymbol);
        }
        
        return true;
    }
}
