/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.turing;

/**
 *
 * @author Pc
 */
public interface Movement {
    int move(int offset);
    int revert(int offset);
}
