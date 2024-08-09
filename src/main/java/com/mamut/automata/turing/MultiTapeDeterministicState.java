/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import java.util.List;

/**
 *
 * @author Pc
 */
public interface MultiTapeDeterministicState extends MultiTapeState {
    List<Transition<MultiTapeDeterministicState>> transitions(List<Character> symbols);
}
