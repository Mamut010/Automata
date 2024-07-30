/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.util;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.EmptyStackException;
import java.util.Iterator;
import java.util.Objects;

/**
 *
 * @author Pc
 * @param <T> Type of items in the stack
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
    public T peek() {
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

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T> () {
            private final Iterator<T> iter = deque.iterator();

            @Override
            public boolean hasNext() {
                return iter.hasNext();
            }

            @Override
            public T next() {
                return iter.next();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    @Override
    public String toString() {
        return deque.toString();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.deque);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final DequeStack<?> other = (DequeStack<?>) obj;
        return Objects.equals(this.deque, other.deque);
    }
}
