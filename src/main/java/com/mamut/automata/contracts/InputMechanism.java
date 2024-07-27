/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 */
public interface InputMechanism {
    boolean loadInputFile(String inputFile);
    boolean isEOF();
    Character advance();
}
