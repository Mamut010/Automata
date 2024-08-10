/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.ReadWriteHead;

/**
 *
 * @author Pc
 */
public interface MultiTapeHeadCollection extends MultiTapeCollection {
    ReadWriteHead getHead(int index);
}
