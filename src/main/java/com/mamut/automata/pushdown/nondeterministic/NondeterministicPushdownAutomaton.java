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
import com.mamut.automata.pushdown.TransitionData;
import com.mamut.automata.util.CollectionUtils;
import com.mamut.automata.util.Validators;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class NondeterministicPushdownAutomaton implements Accepter {
    private int depth = 0;
    private final int DEPTH_LIMIT = 1 << 10;
    
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
        depth = 0;
        
        try {
            return testImpl();
        }
        catch (IllegalStateException e) {
            return false;
        }
    }
    
    private boolean testImpl() {
        depth++;
        if (depth > DEPTH_LIMIT) {
            throw new RuntimeException("An infinite NPDA recursion occured");
        }
        
        NpdaState currentState = controlUnit.getInternalState();
        char storageSymbol = storage.peek();
        boolean result;
        
        if (inputMechanism.isEOF()) {
            result = handleEOF(currentState, storageSymbol);
        }
        else if (checkLambdaTransitions(currentState, storageSymbol)) {
            result = true;
        }
        else {
            result = checkNormalTransitions(currentState, storageSymbol);
        }
        
        depth--;
        return result;
    }
    
    private boolean handleEOF(NpdaState currentState, char storageSymbol) {
        return controlUnit.isAccepted() || checkLambdaTransitions(currentState, storageSymbol);
    }
    
    private boolean checkLambdaTransitions(NpdaState currentState, char storageSymbol) {
        Set<TransitionData<NpdaState>> lambdaTransitions = CollectionUtils.union(
                currentState.lambdaTransitions(storageSymbol), 
                currentState.epsilonLambdaTransitions()
        );
        
        for (TransitionData<NpdaState> lambdaTransition : lambdaTransitions) {
            NpdaState nextState = lambdaTransition.state();
            StorageOperation operation = lambdaTransition.operation();
            
            operation.execute(storage);
            controlUnit.setInternalState(nextState);
            
            if (testImpl()) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            operation.revert(storage);
        }
        
        return false;
    }
    
    private boolean checkNormalTransitions(NpdaState currentState, char storageSymbol) {
        inputMechanism.markPosition();
        
        char symbol = inputMechanism.advance();
        
        Set<TransitionData<NpdaState>> transitions = CollectionUtils.union(
                currentState.transitions(symbol, storageSymbol),
                currentState.epsilonTransitions(symbol)
        );
        
        for (TransitionData<NpdaState> transition : transitions) {
            NpdaState nextState = transition.state();
            StorageOperation operation = transition.operation();
            
            operation.execute(storage);
            controlUnit.setInternalState(nextState);
            
            if (testImpl()) {
                return true;
            }
            
            controlUnit.setInternalState(currentState);
            operation.revert(storage);
        }
        
        inputMechanism.returnToLastMarkedPosition();
        return false;
    }
}
