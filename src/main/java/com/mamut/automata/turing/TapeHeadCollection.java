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
import java.util.stream.Stream;

/**
 *
 * @author Pc
 */
public class TapeHeadCollection extends TapeOrderedCollection {
    private final List<ReadWriteHead> heads;
    
    public TapeHeadCollection() {
        heads = new ArrayList<>();
    }
    
    public void add(Tape tape, ReadWriteHead head) {
        Validators.ensureNonNull(tape, head);
        
        tapes.add(tape);
        heads.add(head);
    }
    
    public ReadWriteHead getHead(int index) {
        ensureValidIndex(index);
        return heads.get(index);
    }
    
    public Stream<ReadWriteHead> getHeadStream() {
        return heads.stream();
    }
}
