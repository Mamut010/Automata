/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

import java.util.List;

/**
 *
 * @author Pc
 */
public interface MultiTrackReadWriteHead extends Moveable {
    void initialize(List<Tape> tapes);
    List<Character> read();
    void write(List<Character> symbols);
}
