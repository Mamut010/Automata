/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.pushdown.operations.*;

/**
 *
 * @author Pc
 */
public final class StorageOperations {
    private StorageOperations() {}
    
    public static StorageOperation push(Character symbol) {
        return new PushStorageOperation(symbol);
    }
    
    public static StorageOperation pop() {
        return new PopStorageOperation();
    }
    
    public static StorageOperation noop() {
        return new NoopStorageOperation();
    }
    
    public static StorageOperation replace(String symbols) {
        return new ReplaceStorageOperation(symbols);
    }
}
