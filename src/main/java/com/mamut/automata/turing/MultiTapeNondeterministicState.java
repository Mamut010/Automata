/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.MultiTapeState;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 * @param <T> The implementing State type
 */
public interface MultiTapeNondeterministicState<T extends MultiTapeState> extends MultiTapeState {
    Set<List<TuringTransition<T>>> transitions(List<Character> symbols);
}
