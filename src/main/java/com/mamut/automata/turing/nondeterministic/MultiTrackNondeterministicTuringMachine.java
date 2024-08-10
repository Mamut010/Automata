/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.nondeterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.turing.InternalMultiTrackCollection;
import com.mamut.automata.contracts.MultiTapeCollection;
import java.util.function.Supplier;

/**
 *
 * @author Pc
 */
public class MultiTrackNondeterministicTuringMachine extends MultiTapeNondeterministicTuringMachine<MultiTrackNtmState> {
    public MultiTrackNondeterministicTuringMachine(
            MultiTapeCollection tapes,
            Supplier<ReadWriteHead> headCreator, 
            ControlUnit<MultiTrackNtmState> controlUnit
    ) {
        super(new InternalMultiTrackCollection(tapes, headCreator).toMultiTapeHeadCollection(), controlUnit);
    }
}
