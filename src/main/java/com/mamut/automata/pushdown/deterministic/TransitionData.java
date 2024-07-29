/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown.deterministic;

import com.mamut.automata.pushdown.StorageOperation;

/**
 *
 * @author Pc
 */
public record TransitionData(DpdaState state, StorageOperation operation) {
};
