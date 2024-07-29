/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

import java.util.Set;

/**
 *
 * @author Pc
 * @param <T> The type of the State
 */
public interface MultiStatesControlUnit<T extends State> {
    void initialize();
    void setInternalStates(Set<T> states);
    Set<T> getInternalStates();
    boolean isAccepted();
}
