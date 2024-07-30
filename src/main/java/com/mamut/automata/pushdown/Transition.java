/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.pushdown;

import com.mamut.automata.contracts.State;

/**
 *
 * @author Pc
 */
public record Transition<T extends State>(T nextState, StorageOperation operation) {
}
