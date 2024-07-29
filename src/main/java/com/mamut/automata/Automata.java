/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mamut.automata;

import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.core.SimpleInputMechanism;
import com.mamut.automata.finite.deterministic.*;
import com.mamut.automata.finite.nondeterministic.*;
import com.mamut.automata.pushdown.PdaStorageDevice;
import com.mamut.automata.pushdown.StorageOperations;
import com.mamut.automata.pushdown.deterministic.*;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class Automata {

    public static void main(String[] args) {
        testDfa();
        System.out.println();
        testNfa();
        System.out.println();
        testDpda();
    }
    
    public static void testAccepter(Accepter accepter, List<String> inputs) {
        inputs.stream().forEach(input -> {
            boolean accepted = accepter.test(input);
            String outputMsg = "Test '%s': %s".formatted(input, accepted ? "accepted" : "rejected");
            System.out.println(outputMsg);
        });
    }
    
    public static void testDfa() {
        System.out.println("Testing DFA1");
        DeterministicFiniteAccepter dfa1 = new DeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DfaControlUnit(dfaConfig1())
        );
        testAccepter(dfa1, List.of("abab", "aaba", "abaaaaaababbbaabba"));
        
        System.out.println();
        
        System.out.println("Testing DFA2");
        DeterministicFiniteAccepter dfa2 = new DeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DfaControlUnit(dfaConfig2())
        );
        testAccepter(dfa2, List.of("010", "100", "001", "10001", "10101"));
    }
    
    public static void testNfa() {
        System.out.println("Testing NFA1");
        NondeterministicFiniteAccepter nfa1 = new NondeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new NfaControlUnit(nfaConfig1())
        );
        testAccepter(nfa1, List.of("", "0", "1", "10"));
        
        System.out.println();
        
        System.out.println("Testing NFA2");
        NondeterministicFiniteAccepter nfa2 = new NondeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new NfaControlUnit(nfaConfig2())
        );
        testAccepter(nfa2, List.of("", "a", "b", "ab"));
    }
    
    public static void testDpda() {
        System.out.println("Testing DPDA1");
        DpdaInitialStateAndSymbol config1 = dpdaConfig1();
        DeterministicPushdownAutomaton dpda1 = new DeterministicPushdownAutomaton(
                new SimpleInputMechanism(), 
                new DpdaControlUnit(config1.state()),
                new PdaStorageDevice(config1.symbol())
        );
        testAccepter(dpda1, List.of("", "ab", "aaabbb", "aaabb", "aabbb"));
    }
    
    public static DfaState dfaConfig1() {
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
    
    public static DfaState dfaConfig2() {
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
    
    public static NfaState nfaConfig1() {
        NfaState q0 = new NfaState(true);
        NfaState q1 = new NfaState();
        NfaState q2 = new NfaState();
        
        q0.addLambdaTransition(q2);
        q0.addTransition(q1, '1');
        q1.addTransitions(Set.of(q0, q2), '0');
        q1.addTransition(q2, '1');
        
        return q0;
    }
    
    public static NfaState nfaConfig2() {
        NfaState q0 = new NfaState();
        NfaState q1 = new NfaState(true);
        NfaState q2 = new NfaState();
        
        q0.addTransition(q1, 'a');
        q1.addLambdaTransition(q2);
        q2.addLambdaTransition(q0);
        
        return q0;
    }
    
    // PDA for Language: a^n.b^n
    public static DpdaInitialStateAndSymbol dpdaConfig1() {
        DpdaState q0 = new DpdaState();
        DpdaState q1 = new DpdaState();
        DpdaState qf = new DpdaState(true);
        
        q0.addSelfLoop('a', 'a', StorageOperations.push('a'));
        q0.addSelfLoop('a', 'z', StorageOperations.push('a'));
        q0.addTransition(q1, 'b', 'a', StorageOperations.pop());
        q1.addSelfLoop('b', 'a', StorageOperations.pop());
        q1.addLambdaTransition(qf, 'z', StorageOperations.noop());
        
        return new DpdaInitialStateAndSymbol(q0, 'z');
    }

    public record DpdaInitialStateAndSymbol(DpdaState state, char symbol) {}
}
