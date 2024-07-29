/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.operations;

import com.mamut.automata.pushdown.StorageOperation;
import com.mamut.automata.contracts.SymbolStack;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;

/**
 *
 * @author Pc
 */
public class ReplaceStorageOperation implements StorageOperation {
    private final String symbols;
    private Deque<Character> poppedSymbols;
    
    public ReplaceStorageOperation(String symbols) {
        this.symbols = symbols != null ? symbols : "";
    }
    
    @Override
    public void execute(SymbolStack storage) {
        if (poppedSymbols == null) {
            poppedSymbols = new ArrayDeque<>();
        }
        
        char poppedSymbol = storage.pop();
        for (int i = symbols.length() - 1; i >= 0; i--) {
            char symbol = symbols.charAt(i);
            storage.push(symbol);
        }
        
        poppedSymbols.push(poppedSymbol);
    }
    
    @Override
    public void revert(SymbolStack storage) {
        if (poppedSymbols == null || poppedSymbols.isEmpty()) {
            return;
        }
        
        for (int i = 0; i < symbols.length(); i++) {
            storage.pop();
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ReplaceStorageOperation other = (ReplaceStorageOperation) obj;
        return Objects.equals(this.symbols, other.symbols);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.symbols);
        return hash;
    }
}
