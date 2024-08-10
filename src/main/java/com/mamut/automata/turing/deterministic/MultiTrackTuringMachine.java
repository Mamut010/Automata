/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing.deterministic;

import com.mamut.automata.contracts.ControlUnit;
import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.turing.MultiTrackCollection;
import com.mamut.automata.contracts.MultiTapeCollection;
import java.util.function.Supplier;

/**
 *
 * @author Pc
 */
public class MultiTrackTuringMachine extends MultiTapeTuringMachine<MultiTrackDtmState> {
    public MultiTrackTuringMachine(
            MultiTapeCollection tapes,
            Supplier<ReadWriteHead> headCreator, 
            ControlUnit<MultiTrackDtmState> controlUnit
    ) {
        super(new MultiTrackCollection(tapes, headCreator), controlUnit);
    }
}
