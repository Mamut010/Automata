/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.contracts.StorageDevice;
import com.mamut.automata.contracts.SymbolStack;

/**
 *
 * @author Pc
 */
public interface PdaStorageDevice extends StorageDevice, SymbolStack {
    String snapshot();
}
