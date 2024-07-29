/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.contracts.StorageDevice;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import com.mamut.automata.contracts.SymbolStack;

/**
 *
 * @author Pc
 */
public class PdaStorageDevice implements StorageDevice, SymbolStack {
    private final Character initialSymbol;
    private final Deque<Character> deque = new ArrayDeque<>();
    
    public PdaStorageDevice(Character initialSymbol) {
        this.initialSymbol = initialSymbol;
    }
    
    @Override
    public void initialize() {
        clear();
        push(initialSymbol);
    }
    
    @Override
    public void clear() {
        deque.clear();
    }

    @Override
    public int size() {
        return deque.size();
    }

    @Override
    public boolean isEmpty() {
        return deque.isEmpty();
    }

    @Override
    public char peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return deque.peek();
    }

    @Override
    public char pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return deque.pop();
    }

    @Override
    public void push(char item) {
        deque.push(item);
    }
}
