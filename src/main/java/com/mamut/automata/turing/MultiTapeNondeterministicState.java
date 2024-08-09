/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public interface MultiTapeNondeterministicState extends MultiTapeState {
    Set<List<Transition<MultiTapeNondeterministicState>>> transitions(List<Character> symbols);
}
