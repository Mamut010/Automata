/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

import java.util.EmptyStackException;

/**
 *
 * @author Pc
 */
public interface SymbolStack {
    void clear();
    int size();
    boolean isEmpty();
    
    char peek() throws EmptyStackException;
    char pop() throws EmptyStackException;
    void push(char symbol);
}
