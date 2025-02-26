/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 * @param <T> The type of the State
 */
public interface ControlUnit<T extends State> {
    void initialize();
    void setInternalState(T state);
    T getInternalState();
    boolean isAccepted();
}
