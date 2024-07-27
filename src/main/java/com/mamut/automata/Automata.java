/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mamut.automata;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.InputMechanism;
import com.mamut.automata.core.SimpleInputMechanism;
import com.mamut.automata.finite.deterministic.*;
import java.util.List;

/**
 *
 * @author Pc
 */
public class Automata {

    public static void main(String[] args) {
        System.out.println("Testing DFA1");
        testDfa(config1(), List.of("abab", "aaba", "abaaaaaababbbaabba"));
        System.out.println();
        System.out.println("Testing DFA2");
        testDfa(config2(), List.of("010", "100", "001", "10001", "10101"));
    }
    
    public static void testAccepter(Accepter accepter, List<String> inputs) {
        inputs.stream().forEach(input -> {
            boolean accepted = accepter.test(input);
            String outputMsg = "Test '%s': %s".formatted(input, accepted ? "accepted" : "rejected");
            System.out.println(outputMsg);
        });
    }
    
    public static void testDfa(DfaState initialState, List<String> inputs) {
        InputMechanism inputMechanism = new SimpleInputMechanism();
        DfaControlUnit controlUnit = new DfaControlUnit(initialState);
        DeterministicFiniteAccepter dfa = new DeterministicFiniteAccepter(inputMechanism, controlUnit);
        testAccepter(dfa, inputs);
    }
    
    public static DfaState config1() {
        DfaState q0 = new DfaState();
        DfaState q1 = new DfaState();
        DfaState q2 = new DfaState(true);
        DfaState q3 = new DfaState();
        
        q0.addTransition(q1, 'a');
        q0.addTransition(q3, 'b');
        q1.addTransition(q3, 'a');
        q1.addTransition(q2, 'b');
        q2.addSelfLoop('a', 'b');
        q3.addSelfLoop('a', 'b');
        
        return q0;
    }
    
    public static DfaState config2() {
        DfaState q0 = new DfaState(true);
        DfaState q1 = new DfaState(true);
        DfaState q2 = new DfaState(true);
        DfaState q3 = new DfaState();
        
        q0.addTransition(q1, '0');
        q0.addSelfLoop('1');
        q1.addTransition(q2, '0');
        q1.addTransition(q0, '1');
        q2.addSelfLoop('0');
        q2.addTransition(q3, '1');
        q3.addSelfLoop('0', '1');
        
        return q0;
    }
}
