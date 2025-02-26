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
public class NoopStorageOperation implements StorageOperation {
    @Override
    public void execute(SymbolStack storage) {
        // DO NOTHING
    }
    
    @Override
    public void revert(SymbolStack storage) {
        // DO NOTHING
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
        int hash = 7;
        return hash;
    }
}
