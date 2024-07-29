/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.contracts.StorageDevice;
import java.util.ArrayDeque;
import java.util.Deque;
import com.mamut.automata.contracts.SymbolStack;

/**
 *
 * @author Pc
 */
public class PdaStorageDevice implements StorageDevice, SymbolStack {
    private final char initialSymbol;
    private final Deque<Character> deque = new ArrayDeque<>();
    
    public PdaStorageDevice(char initialSymbol) {
        this.initialSymbol = initialSymbol;
        deque.push(initialSymbol);
    }
    
    @Override
    public void initialize() {
        deque.clear();
        push(initialSymbol);
    }

    @Override
    public boolean isEmpty() {
        return deque.size() == 1;
    }

    @Override
    public char peek() {
        return deque.peek();
    }

    @Override
    public char pop() throws IllegalStateException {
        if (isEmpty()) {
            throw new IllegalStateException();
        }
        return deque.pop();
    }

    @Override
    public void push(char item) {
        deque.push(item);
    }
}
