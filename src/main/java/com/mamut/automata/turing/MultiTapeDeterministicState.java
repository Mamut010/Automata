/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import java.util.List;

/**
 *
 * @author Pc
 * @param <T> The implementing State type
 */
public interface MultiTapeDeterministicState<T extends MultiTapeState> extends MultiTapeState {
    List<Transition<T>> transitions(List<Character> symbols);
}
