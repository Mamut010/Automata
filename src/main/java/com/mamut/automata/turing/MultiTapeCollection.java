/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Tape;

/**
 *
 * @author Pc
 */
public interface MultiTapeCollection {
    int getTapeCount();
    int getInputTapeIndex();
    int getOutputTapeIndex();
    Tape getTape(int index);
}
