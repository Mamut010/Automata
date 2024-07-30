/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.util.DequeStack;
import com.mamut.automata.util.Stack;

/**
 *
 * @author Pc
 */
public class DefaultStorageDevice implements PdaStorageDevice {
    private final char initialSymbol;
    private final Stack<Character> stack = new DequeStack<>();
    
    public DefaultStorageDevice(char initialSymbol) {
        this.initialSymbol = initialSymbol;
        stack.push(initialSymbol);
    }
    
    @Override
    public void initialize() {
        stack.clear();
        push(initialSymbol);
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public char peek() {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        return stack.peek();
    }

    @Override
    public char pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        return stack.pop();
    }

    @Override
    public void push(char item) {
        stack.push(item);
    }
    
    @Override
    public String toString() {
        return stack.toString();
    }
}
