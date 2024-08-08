/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.util.Validators;

/**
 *
 * @author Pc
 */
public record TuringTransitionConfig (Character symbol, Character replacingSymbol, Movement movement) {
    public TuringTransitionConfig {
        Validators.ensureNonNull(movement);
    }
}
