/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import java.util.List;

/**
 *
 * @author Pc
 */
public record MultiTrackConfiguration<T extends MultiTapeState> (T state, int readWriteHeadOffset, List<String> tapeSnapshot) {
}
