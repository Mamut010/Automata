/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 */
public interface BacktrackableInputMechanism extends InputMechanism {
    void markPosition();
    void returnToLastMarkedPosition();
    void discardLastMarkedPosition();
}
