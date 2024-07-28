/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

import java.util.EmptyStackException;

/**
 *
 * @author Pc
 * @param <T> Type of stack items
 */
public interface Stack<T> {
    void clear();
    int size();
    boolean isEmpty();
    
    T peek() throws EmptyStackException;
    T pop() throws EmptyStackException;
    void push(T item);
}
