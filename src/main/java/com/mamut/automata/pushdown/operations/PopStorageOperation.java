/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.operations;

import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.contracts.SymbolStack;
import java.util.ArrayDeque;
import java.util.Deque;

/**
 *
 * @author Pc
 */
public class PopStorageOperation implements StorageOperation {
    private Deque<Character> poppedSymbols;
    
    @Override
    public void execute(SymbolStack storage) {
        if (poppedSymbols == null) {
            poppedSymbols = new ArrayDeque<>();
        }
        
        char poppedSymbol = storage.pop();
        poppedSymbols.push(poppedSymbol);
    }
    
    @Override
    public void revert(SymbolStack storage) {
        if (poppedSymbols == null || poppedSymbols.isEmpty()) {
            return;
        }
        
        char lastPoppedSymbol = poppedSymbols.pop();
        storage.push(lastPoppedSymbol);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        return getClass() != obj.getClass();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }
}
