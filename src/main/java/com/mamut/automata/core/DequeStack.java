/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.core;

import com.mamut.automata.contracts.Stack;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;

/**
 *
 * @author Pc
 */
public class DequeStack<T> implements Stack<T> {
    private final Deque<T> deque = new ArrayDeque<>();

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
    public T peek() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return deque.peek();
    }

    @Override
    public T pop() throws EmptyStackException {
        if (isEmpty()) {
            throw new EmptyStackException();
        }
        return deque.pop();
    }

    @Override
    public void push(T item) {
        deque.push(item);
    }
}
