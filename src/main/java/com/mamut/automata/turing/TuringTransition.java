/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.State;

/**
 *
 * @author Pc
 */
public record TuringTransition<T extends State>(T nextState, Character replacingSymbol, Movement movement) {
}
