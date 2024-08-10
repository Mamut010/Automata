/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.MultiTapeState;
import java.util.List;

/**
 *
 * @author Pc
 * @param <T> The implementing State type
 */
public interface MultiTapeDeterministicState<T extends MultiTapeState> extends MultiTapeState {
    List<TuringTransition<T>> transitions(List<Character> symbols);
}
