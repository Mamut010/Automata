/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 */
public interface SymbolStack {
    boolean isEmpty();
    char peek() throws IllegalStateException;
    char pop() throws IllegalStateException;
    void push(char symbol);
}
