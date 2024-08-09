/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Tape;
import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public class DefaultTapeOrderedCollection extends TapeOrderedCollection {
    public void add(Tape tape) {
        Validators.ensureNonNull(tape);
        tapes.add(tape);
    }
}
