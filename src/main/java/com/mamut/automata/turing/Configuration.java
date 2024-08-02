/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.State;

/**
 *
 * @author Pc
 */
public record Configuration<T extends State>(T state, int readWriteHeadOffset, String tapeSnapshot) {
}
