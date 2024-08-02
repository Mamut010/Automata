/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.State;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.contracts.Transducer;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 * @param <T> The State type
 */
public abstract class AbstractTuringMachine<T extends State> implements Accepter, Transducer {
    protected final Tape tape;
    protected final ReadWriteHead readWriteHead;
    protected final ControlUnit<T> controlUnit;
    
    public AbstractTuringMachine(Tape tape, ReadWriteHead readWriteHead, ControlUnit<T> controlUnit) {
        Validators.ensureNonNull(tape, readWriteHead, controlUnit);
        
        this.tape = tape;
        this.readWriteHead = readWriteHead;
        this.controlUnit = controlUnit;
    }
    
    @Override
    public Character[] transduce(String inputFile) {
        if (test(inputFile)) {
            return tape.getContent();
        }
        else {
            return null;
        }
    }
    
    protected Configuration<T> getCurrentConfiguration() {
        T currentState = controlUnit.getInternalState();
        int offset = readWriteHead.getOffset();
        String tapeSnapshot = tape.snapshot();
        return new Configuration(currentState, offset, tapeSnapshot);
    }
}
