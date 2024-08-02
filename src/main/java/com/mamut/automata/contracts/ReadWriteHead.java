/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mamut.automata.contracts;

/**
 *
 * @author Pc
 */
public interface ReadWriteHead {
    void initialize(Tape tape);
    int getOffset();
    void setOffset(int offset);
    Character read();
    void write(Character symbol);
}
