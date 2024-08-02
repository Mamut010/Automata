/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mamut.automata.turing;

import com.mamut.automata.contracts.Tape;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

/**
 *
 * @author Pc
 */
public class InfiniteTape implements Tape {
    private record TranslateOffsetResult(int index, boolean isFront) {}
    
    private final Character blankSymbol;
    private final List<Character> front;
    private final List<Character> back;
    
    public InfiniteTape(Character blankSymbol) {
        this.blankSymbol = blankSymbol;
        front = new ArrayList<>();
        back = new ArrayList<>();
    }

    @Override
    public boolean loadInput(String input) {
        front.clear();
        back.clear();
        if (input == null) {
            return true;
        }
        
        int middleIndex = input.length() / 2;
        for (int i = middleIndex - 1; i >= 0; i--) {
            char symbol = input.charAt(i);
            front.add(symbol);
        }
        for (int i = middleIndex; i < input.length(); i++) {
            char symbol = input.charAt(i);
            back.add(symbol);
        }
        
        return true;
    }
    
    @Override
    public int contentSize() {
        return front.size() + back.size();
    }
    
    @Override
    public Character[] getContent() {
        Character[] result = new Character[contentSize()];
        
        int frontSize = front.size();
        for (int i = 0; i < result.length; i++) {
            if (i < front.size()) {
                int index = frontSize - 1 - i;
                result[i] = front.get(index);
            }
            else {
                int index = i - frontSize;
                result[i] = back.get(index);
            }
        }
        
        return result;
    }

    @Override
    public Character getSymbol(int offset) {
        TranslateOffsetResult translateOffsetResult = translateOffsetToIndex(offset);
        int index = translateOffsetResult.index();
        boolean isFront = translateOffsetResult.isFront();
        Character symbol = blankSymbol;
        
        if (isFront && index < front.size()) {
            symbol = front.get(index);
        }
        else if (!isFront && index < back.size()) {
            symbol = back.get(index);
        }
        
        return symbol;
    }

    @Override
    public int setSymbol(int offset, Character symbol) {
        TranslateOffsetResult translateOffsetResult = translateOffsetToIndex(offset);
        int index = translateOffsetResult.index();
        boolean isFront = translateOffsetResult.isFront();
        
        if (isFront) {
            upsert(front, index, symbol);
        }
        else {
            upsert(back, index, symbol);
        }
        
        int oldFrontSize = front.size();
        int oldBackSize = back.size();
        
        trimBlankSymbols();
        
        int newFrontSize = front.size();
        int newBackSize = back.size();
        if (newFrontSize != oldFrontSize) {
            offset -= oldFrontSize - newFrontSize;
            if (newFrontSize == 0) {
                offset -= oldBackSize - newBackSize;
            }
        }
        
        return offset;
    }
    
    @Override
    public String snapshot() {
        StringJoiner joiner = new StringJoiner(",", "[", "]");
        
        for (int i = front.size() - 1; i >= 0; i--) {
            Character symbol = front.get(i);
            joiner.add(String.valueOf(symbol));
        }
        for (int i = 0; i < back.size(); i++) {
            Character symbol = back.get(i);
            joiner.add(String.valueOf(symbol));
        }
        
        return joiner.toString();
    }
    
    @Override
    public String toString() {
        return snapshot();
    }
    
    /**
     * Translate a given offset to the corresponding index of the suitable list (front or back)
     * @param offset The offset
     * @return The corresponding index of the given offset and a flag indicating if the index is for the front list.
     */
    private TranslateOffsetResult translateOffsetToIndex(int offset) {
        int frontSize = front.size();
        if (offset < frontSize) {
            return new TranslateOffsetResult(frontSize - 1 - offset, true);
        }
        else {
            return new TranslateOffsetResult(offset - frontSize, false);
        }
    }
    
    private void upsert(List<Character> list, int index, Character symbol) {
        int listSize = list.size();
        if (index < listSize) {
            list.set(index, symbol);
        }
        else if (!Objects.equals(blankSymbol, symbol)) {
            for (int i = listSize; i < index; i++) {
                list.add(blankSymbol);
            }
            list.add(symbol);
        }
    }
    
    private void trimBlankSymbols() {
        truncateTrailingBlankSymbols(front);
        truncateTrailingBlankSymbols(back);
        
        if (front.isEmpty()) {
            for (int i = 0; i < back.size(); i++) {
                Character symbol = back.get(i);
                if (!Objects.equals(blankSymbol, symbol)) {
                    break;
                }
                back.remove(i);
            }
        }
        
        if (back.isEmpty()) {
            for (int i = 0; i < front.size(); i++) {
                Character symbol = front.get(i);
                if (!Objects.equals(blankSymbol, symbol)) {
                    break;
                }
                front.remove(i);
            }
        }
    }
    
    private void truncateTrailingBlankSymbols(List<Character> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Character symbol = list.get(i);
            if (!Objects.equals(blankSymbol, symbol)) {
                break;
            }
            list.remove(i);
        }
    }
}
