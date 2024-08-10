/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.ReadWriteHead;
import com.mamut.automata.contracts.Tape;
import com.mamut.automata.util.Validators;
import java.util.ArrayList;
import java.util.List;
import com.mamut.automata.contracts.TapeHeadIndexedCollection;

/**
 *
 * @author Pc
 */
public class TapeHeadPairIndexedCollection extends GeneralTapeIndexedCollection implements TapeHeadIndexedCollection {
    private final List<ReadWriteHead> heads;
    
    public TapeHeadPairIndexedCollection() {
        heads = new ArrayList<>();
    }
    
    public void add(Tape tape, ReadWriteHead head) {
        Validators.ensureNonNull(tape, head);
        tapes.add(tape);
        heads.add(head);
    }
    
    @Override
    public ReadWriteHead getHead(int index) {
        return heads.get(ensureValidIndex(index));
    }
}
