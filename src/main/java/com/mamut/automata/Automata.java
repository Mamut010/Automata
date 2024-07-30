/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mamut.automata;

import com.mamut.automata.core.*;
import com.mamut.automata.contracts.Accepter;
import com.mamut.automata.contracts.State;
import com.mamut.automata.finite.deterministic.*;
import com.mamut.automata.finite.nondeterministic.*;
import com.mamut.automata.pushdown.DefaultStorageDevice;
import com.mamut.automata.pushdown.StorageOperations;
import com.mamut.automata.pushdown.deterministic.*;
import com.mamut.automata.pushdown.nondeterministic.*;
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
        System.out.println();
        testNpda();
    }
    
    public static void testAccepter(Accepter accepter, List<String> inputs) {
        inputs.stream().forEach(input -> {
            boolean accepted = accepter.test(input);
            String outputMsg = "Test '%s': %s".formatted(input, accepted ? "accepted" : "rejected");
            System.out.println(outputMsg);
        });
    }
    
    public static void testDfa() {
        System.out.println("Testing DFA1 - Language: ab.(a+b)*");
        DeterministicFiniteAccepter dfa1 = new DeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DefaultControlUnit(dfaConfig1())
        );
        testAccepter(dfa1, List.of("abab", "aaba", "abaaaaaababbbaabba"));
        
        System.out.println();
        
        System.out.println("Testing DFA2 - Language: w in (0+1)* | w does not contain '001'");
        DeterministicFiniteAccepter dfa2 = new DeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DefaultControlUnit(dfaConfig2())
        );
        testAccepter(dfa2, List.of("", "010", "100", "001", "10001", "10101"));
    }
    
    public static void testNfa() {
        System.out.println("Testing NFA1 - Language: (10)*");
        NondeterministicFiniteAccepter nfa1 = new NondeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DefaultMultiStatesControlUnit(nfaConfig1())
        );
        testAccepter(nfa1, List.of("", "0", "1", "10"));
        
        System.out.println();
        
        System.out.println("Testing NFA2 - Language: a+");
        NondeterministicFiniteAccepter nfa2 = new NondeterministicFiniteAccepter(
                new SimpleInputMechanism(), 
                new DefaultMultiStatesControlUnit(nfaConfig2())
        );
        testAccepter(nfa2, List.of("", "a", "b", "ab"));
    }
    
    public static void testDpda() {
        System.out.println("Testing DPDA1 - Language: a^n.b^n, n >= 1");
        InitialStateAndSymbol<DpdaState> config1 = dpdaConfig1();
        DeterministicPushdownAutomaton dpda1 = new DeterministicPushdownAutomaton(
                new SimpleInputMechanism(), 
                new DefaultControlUnit(config1.state()),
                new DefaultStorageDevice(config1.symbol())
        );
        testAccepter(dpda1, List.of("", "ab", "aaabbb", "aaabb", "aabbb"));
        
        System.out.println();
        
        System.out.println("Testing DPDA2 - Language: w.#.w^R");
        InitialStateAndSymbol<DpdaState> config2 = dpdaConfig2();
        DeterministicPushdownAutomaton dpda2 = new DeterministicPushdownAutomaton(
                new SimpleInputMechanism(), 
                new DefaultControlUnit(config2.state()),
                new DefaultStorageDevice(config2.symbol())
        );
        testAccepter(dpda2, List.of("", "#", "a#b", "aa#bb", "ab#ab", "ab#ba", "aa#bbb", "aba#aba"));
    }
    
    public static void testNpda() {
        System.out.println("Testing NPDA1 - Language: w.(a+b)?.w^R (Palindrome)");
        InitialStateAndSymbol<NpdaState> config1 = npdaConfig1();
        NondeterministicPushdownAutomaton npda1 = new NondeterministicPushdownAutomaton(
                new PositionBufferedInputMechanism(), 
                new DefaultControlUnit(config1.state()),
                new DefaultStorageDevice(config1.symbol())
        );
        testAccepter(npda1, List.of("", "aba", "abba", "abab", "ababa"));
        
        System.out.println();
        
        System.out.println("Testing NPDA2 - Grammar: S -> aSbb | a");
        InitialStateAndSymbol<NpdaState> config2 = npdaConfig2();
        NondeterministicPushdownAutomaton npda2 = new NondeterministicPushdownAutomaton(
                new PositionBufferedInputMechanism(), 
                new DefaultControlUnit(config2.state()),
                new DefaultStorageDevice(config2.symbol())
        );
        testAccepter(npda2, List.of("", "a", "b", "aabb", "aabbb", "aaabbbb"));
    }
    
    /**
     * DFA for Language: ab.(a+b)*
     * @return The initial state
     */
    public static DfaState dfaConfig1() {
        DfaState q0 = new DfaState();
        DfaState q1 = new DfaState();
        DfaState q2 = new DfaState(true);
        DfaState q3 = new DfaState();
        
        q0.addTransition(q1, 'a')
                .addTransition(q3, 'b');
        q1.addTransition(q3, 'a')
                .addTransition(q2, 'b');
        q2.addSelfLoop('a', 'b');
        q3.addSelfLoop('a', 'b');
        
        return q0;
    }
    
    /**
     * DFA for Language: w in (0+1)* | w does not contain '001'
     * @return The initial state
     */
    public static DfaState dfaConfig2() {
        DfaState q0 = new DfaState(true);
        DfaState q1 = new DfaState(true);
        DfaState q2 = new DfaState(true);
        DfaState q3 = new DfaState();
        
        q0.addTransition(q1, '0')
                .addSelfLoop('1');
        q1.addTransition(q2, '0')
                .addTransition(q0, '1');
        q2.addSelfLoop('0')
                .addTransition(q3, '1');
        q3.addSelfLoop('0', '1');
        
        return q0;
    }
    
    /**
     * NFA for Language: (10)*
     * @return The initial state
     */
    public static NfaState nfaConfig1() {
        NfaState q0 = new NfaState(true);
        NfaState q1 = new NfaState();
        NfaState q2 = new NfaState();
        
        q0.addLambdaTransition(q2)
                .addTransition(q1, '1');
        q1.addTransitions(Set.of(q0, q2), '0')
                .addTransition(q2, '1');
        
        return q0;
    }
    
    /**
     * NFA for Language: a+
     * @return The initial state
     */
    public static NfaState nfaConfig2() {
        NfaState q0 = new NfaState();
        NfaState q1 = new NfaState(true);
        NfaState q2 = new NfaState();
        
        q0.addTransition(q1, 'a');
        q1.addLambdaTransition(q2);
        q2.addLambdaTransition(q0);
        
        return q0;
    }
    
    /**
     * PDA for Language: a^n.b^n, n >= 1
     * @return The configuration including initial state and storage symbol
     */
    public static InitialStateAndSymbol<DpdaState> dpdaConfig1() {
        DpdaState q0 = new DpdaState();
        DpdaState q1 = new DpdaState();
        DpdaState q2 = new DpdaState(true);
        
        q0.addSelfLoop('a', 'Z', StorageOperations.push('A'))
                .addSelfLoop('a', 'A', StorageOperations.push('A'))
                .addTransition(q1, 'b', 'A', StorageOperations.pop());
        q1.addSelfLoop('b', 'A', StorageOperations.pop())
                .addLambdaTransition(q2, 'Z', StorageOperations.noop());
        
        return new InitialStateAndSymbol(q0, 'Z');
    }
    
    /**
     * PDA for Language: w.#.w^R
     * @return The configuration including initial state and storage symbol
     */
    public static InitialStateAndSymbol<DpdaState> dpdaConfig2() {
        DpdaState q0 = new DpdaState();
        DpdaState q1 = new DpdaState();
        DpdaState q2 = new DpdaState(true);
        
        q0.addSelfLoop('a', 'Z', StorageOperations.push('A'))
                .addSelfLoop('a', 'A', StorageOperations.push('A'))
                .addSelfLoop('a', 'B', StorageOperations.push('A'))
                .addSelfLoop('b', 'Z', StorageOperations.push('B'))
                .addSelfLoop('b', 'A', StorageOperations.push('B'))
                .addSelfLoop('b', 'B', StorageOperations.push('B'))
                .addTransition(q1, '#', 'Z', StorageOperations.noop())
                .addTransition(q1, '#', 'A', StorageOperations.noop())
                .addTransition(q1, '#', 'B', StorageOperations.noop());
        q1.addSelfLoop('a', 'A', StorageOperations.pop())
                .addSelfLoop('b', 'B', StorageOperations.pop())
                .addLambdaTransition(q2, 'Z', StorageOperations.noop());
        
        return new InitialStateAndSymbol(q0, 'Z');
    }
    
    /**
     * NPDA for Language: w.(a+b)?.w^R (Palindrome)
     * @return The configuration including initial state and storage symbol
     */
    public static InitialStateAndSymbol<NpdaState> npdaConfig1() {
        NpdaState q0 = new NpdaState();
        NpdaState q1 = new NpdaState();
        NpdaState q2 = new NpdaState(true);
        
        q0.addSelfLoop('a', 'Z', StorageOperations.push('A'))
                .addSelfLoop('a', 'A', StorageOperations.push('A'))
                .addSelfLoop('a', 'B', StorageOperations.push('A'))
                .addSelfLoop('b', 'Z', StorageOperations.push('B'))
                .addSelfLoop('b', 'A', StorageOperations.push('B'))
                .addSelfLoop('b', 'B', StorageOperations.push('B'))
                .addEpsilonTransition(q1, 'a', StorageOperations.noop())
                .addEpsilonTransition(q1, 'b', StorageOperations.noop())
                .addEpsilonLambdaTransition(q1, StorageOperations.noop());
        q1.addSelfLoop('a', 'A', StorageOperations.pop())
                .addSelfLoop('b', 'B', StorageOperations.pop())
                .addLambdaTransition(q2, 'Z', StorageOperations.noop());
        
        return new InitialStateAndSymbol(q0, 'Z');
    }
    
    /**
     * NPDA for Grammar: S -> aSbb | a
     * @return The configuration including initial state and storage symbol
     */
    public static InitialStateAndSymbol<NpdaState> npdaConfig2() {
        NpdaState q0 = new NpdaState();
        NpdaState q1 = new NpdaState();
        NpdaState q2 = new NpdaState(true);
        
        q0.addLambdaTransition(q1, 'Z', StorageOperations.push('S'));
        q1.addSelfLoop('a', 'S', StorageOperations.replace("SA"))
                .addSelfLoop('a', 'S', StorageOperations.pop())
                .addSelfLoop('b', 'A', StorageOperations.replace("B"))
                .addSelfLoop('b', 'B', StorageOperations.pop())
                .addLambdaTransition(q2, 'Z', StorageOperations.pop());
        
        return new InitialStateAndSymbol(q0, 'Z');
    }

    public record InitialStateAndSymbol<T extends State>(State state, char symbol) {}
}
