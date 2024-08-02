/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 */
public interface Tape {
    boolean loadInput(String input);
    int contentSize();
    Character[] getContent();
    Character getSymbol(int offset);
    int setSymbol(int offset, Character symbol);
    String snapshot();
}
