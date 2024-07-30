/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.operations;

import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.contracts.SymbolStack;

/**
 *
 * @author Pc
 */
public class PushStorageOperation implements StorageOperation {
    private final char symbol;
    private int pushCount = 0;
    
    public PushStorageOperation(char symbol) {
        this.symbol = symbol;
    }
    
    @Override
    public void execute(SymbolStack storage) {
        storage.push(symbol);
        pushCount++;
    }
    
    @Override
    public void revert(SymbolStack storage) {
        if (pushCount == 0) {
            return;
        }
        
        storage.pop();
        pushCount--;
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
        final PushStorageOperation other = (PushStorageOperation) obj;
        return this.symbol == other.symbol;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.symbol;
        return hash;
    }
}
