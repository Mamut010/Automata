/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mamut.automata;

import com.mamut.automata.core.*;
import com.mamut.automata.contracts.*;
import com.mamut.automata.finite.deterministic.*;
import com.mamut.automata.finite.nondeterministic.*;
import com.mamut.automata.pushdown.DefaultStorageDevice;
import com.mamut.automata.pushdown.StorageOperations;
import com.mamut.automata.pushdown.deterministic.*;
import com.mamut.automata.pushdown.nondeterministic.*;
import com.mamut.automata.turing.DefaultReadWriteHead;
import com.mamut.automata.turing.TapeOrderedCollection;
import com.mamut.automata.turing.InfiniteTape;
import com.mamut.automata.turing.Movements;
import com.mamut.automata.turing.MultiTrackTuringTransitionConfig;
import com.mamut.automata.turing.MultiTapeHeadPairCollection;
import com.mamut.automata.contracts.MultiTapeHeadCollection;
import com.mamut.automata.turing.TuringTransitionConfig;
import com.mamut.automata.turing.deterministic.*;
import com.mamut.automata.turing.nondeterministic.*;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Pc
 */
public class Automata {
    private static final Character BLANK = null;

    public static void main(String[] args) {
        testDfa();
        System.out.println();
        testNfa();
        System.out.println();
        testDpda();
        System.out.println();
        testNpda();
        System.out.println();
        testDtm();
        System.out.println();
        testNtm();
        System.out.println();
        testMdtm();
        System.out.println();
        testMntm();
        System.out.println();
        testMtdtm();
        System.out.println();
        testMtntm();
    }
    
    public static void testAccepter(Accepter accepter, List<String> inputs) {
        inputs.stream().forEach(input -> {
            boolean accepted = accepter.test(input);
            String outputMsg = "Test '%s': %s".formatted(input, accepted ? "accepted" : "rejected");
            System.out.println(outputMsg);
        });
    }
    
    public static void testTransducer(Transducer transducer, List<String> inputs) {
        inputs.stream().forEach(input -> {
            Character[] symbols = transducer.transduce(input);
            String output = "FAILED";
            
            if (symbols != null) {
                StringBuilder builder = new StringBuilder();
                for (Character symbol : symbols) {
                    builder.append(symbol);
                }
                output = "'" + builder.toString() + "'";
            }
                    
            String outputMsg = "Transduce '%s': %s".formatted(input, output);
            System.out.println(outputMsg);
        });
    }
    
    public static void testDfa() {
        System.out.println("Testing DFA1 - Language: ab.(a+b)*");
        DeterministicFiniteAccepter dfa1 = new DeterministicFiniteAccepter(
                new StringInputMechanism(), 
                new DefaultControlUnit(dfaConfig1())
        );
        testAccepter(dfa1, List.of("abab", "aaba", "abaaaaaababbbaabba"));
        
        System.out.println();
        
        System.out.println("Testing DFA2 - Language: w in (0+1)* | w does not contain '001'");
        DeterministicFiniteAccepter dfa2 = new DeterministicFiniteAccepter(
                new StringInputMechanism(), 
                new DefaultControlUnit(dfaConfig2())
        );
        testAccepter(dfa2, List.of("", "010", "100", "001", "10001", "10101"));
    }
    
    public static void testNfa() {
        System.out.println("Testing NFA1 - Language: (10)*");
        NondeterministicFiniteAccepter nfa1 = new NondeterministicFiniteAccepter(
                new StringInputMechanism(), 
                new DefaultMultiStatesControlUnit(nfaConfig1())
        );
        testAccepter(nfa1, List.of("", "0", "1", "10"));
        
        System.out.println();
        
        System.out.println("Testing NFA2 - Language: a+");
        NondeterministicFiniteAccepter nfa2 = new NondeterministicFiniteAccepter(
                new StringInputMechanism(), 
                new DefaultMultiStatesControlUnit(nfaConfig2())
        );
        testAccepter(nfa2, List.of("", "a", "b", "ab"));
    }
    
    public static void testDpda() {
        System.out.println("Testing DPDA1 - Language: a^n.b^n, n >= 1");
        InitialStateAndSymbol<DpdaState> config1 = dpdaConfig1();
        DeterministicPushdownAutomaton dpda1 = new DeterministicPushdownAutomaton(
                new StringInputMechanism(), 
                new DefaultControlUnit(config1.state()),
                new DefaultStorageDevice(config1.symbol())
        );
        testAccepter(dpda1, List.of("", "ab", "aaabbb", "aaabb", "aabbb"));
        
        System.out.println();
        
        System.out.println("Testing DPDA2 - Language: w.#.w^R");
        InitialStateAndSymbol<DpdaState> config2 = dpdaConfig2();
        DeterministicPushdownAutomaton dpda2 = new DeterministicPushdownAutomaton(
                new StringInputMechanism(), 
                new DefaultControlUnit(config2.state()),
                new DefaultStorageDevice(config2.symbol())
        );
        testAccepter(dpda2, List.of("", "#", "a#b", "aa#bb", "ab#ab", "ab#ba", "aa#bbb", "aba#aba"));
    }
    
    public static void testNpda() {
        System.out.println("Testing NPDA1 - Language: w.(a+b)?.w^R (Palindrome)");
        InitialStateAndSymbol<NpdaState> config1 = npdaConfig1();
        NondeterministicPushdownAutomaton npda1 = new NondeterministicPushdownAutomaton(
                new PositionBufferedStringInputMechanism(), 
                new DefaultControlUnit(config1.state()),
                new DefaultStorageDevice(config1.symbol())
        );
        testAccepter(npda1, List.of("", "aba", "abba", "abab", "ababa"));
        
        System.out.println();
        
        System.out.println("Testing NPDA2 - Grammar: S -> aSbb | a");
        InitialStateAndSymbol<NpdaState> config2 = npdaConfig2();
        NondeterministicPushdownAutomaton npda2 = new NondeterministicPushdownAutomaton(
                new PositionBufferedStringInputMechanism(), 
                new DefaultControlUnit(config2.state()),
                new DefaultStorageDevice(config2.symbol())
        );
        testAccepter(npda2, List.of("", "a", "b", "aabb", "aabbb", "aaabbbb"));
    }
    
    public static void testDtm() {
        System.out.println("Testing DTM1 - Language: 0^n.1^n.2^n, n >= 1");
        TuringMachine dtm1 = new TuringMachine(
                new InfiniteTape(BLANK), 
                new DefaultReadWriteHead(),
                new DefaultControlUnit(dtmConfig1())
        );
        testAccepter(dtm1, List.of("", "012", "00122", "012012", "001122"));
        
        System.out.println();
        
        System.out.println("Testing DTM2 - Transducer to compute the sum of 2 positive numbers in unary number system");
        TuringMachine dtm2 = new TuringMachine(
                new InfiniteTape(BLANK), 
                new DefaultReadWriteHead(),
                new DefaultControlUnit(dtmConfig2())
        );
        testTransducer(dtm2, List.of("", "111+11", "1+11111", "111++11", "11111+11111"));
        
        System.out.println();
        
        System.out.println("Testing DTM3 - Transducer to compute the parity of a binary number");
        TuringMachine dtm3 = new TuringMachine(
                new InfiniteTape(BLANK), 
                new DefaultReadWriteHead(),
                new DefaultControlUnit(dtmConfig3())
        );
        testTransducer(dtm3, List.of("", "001001", "101010", "1110110", "101"));
    }
    
    public static void testNtm() {
        System.out.println("Testing NTM1 - Language: a+");
        NondeterministicTuringMachine ntm1 = new NondeterministicTuringMachine(
                new InfiniteTape(BLANK), 
                new DefaultReadWriteHead(),
                new DefaultControlUnit(ntmConfig1())
        );
        testAccepter(ntm1, List.of("", "a", "aa", "aba", "aaaaaabaaaaaa", "aaaaaaaaaaaaaaaa"));
        
        System.out.println();
        
        System.out.println("Testing NTM2 - Transducer to invert a binary string");
        NondeterministicTuringMachine ntm2 = new NondeterministicTuringMachine(
                new InfiniteTape(BLANK), 
                new DefaultReadWriteHead(),
                new DefaultControlUnit(ntmConfig2())
        );
        testTransducer(ntm2, List.of("", "10", "1234", "00", "110011", "10101"));
    }
    
    public static void testMdtm() {
        System.out.println("Testing MDTM1 - Language: w.#.w, w is a binary string");
        InitialStateAndTapeHeadCollection<MdtmState> config1 = mdtmConfig1();
        MultiTapeTuringMachine<MdtmState> mdtm1 = new MultiTapeTuringMachine<>(
                config1.collection(),
                new DefaultControlUnit<>(config1.state())
        );
        testAccepter(mdtm1, List.of("", "#", "000", "00#00", "10#10", "10#100", "01011#01011"));
        
        System.out.println();
        
        System.out.println("Testing MDTM2 - Language: a^n.b^n.c^n, n >= 0");
        InitialStateAndTapeHeadCollection<MdtmState> config2 = mdtmConfig2();
        MultiTapeTuringMachine<MdtmState> mdtm2 = new MultiTapeTuringMachine<>(
                config2.collection(),
                new DefaultControlUnit<>(config2.state())
        );
        testAccepter(mdtm2, List.of("", "abc", "aabbcc", "aaccbb", "abccba", "aaaabbbbcccc", "1"));
    }
    
    public static void testMntm() {
        System.out.println("Testing MNTM1 - Transducer to detect word w in Language w.w, w is a binary string");
        InitialStateAndTapeHeadCollection<MntmState> config1 = mntmConfig1();
        MultiTapeNondeterministicTuringMachine mntm1 = new MultiTapeNondeterministicTuringMachine(
                config1.collection(),
                new DefaultControlUnit(config1.state())
        );
        testTransducer(mntm1, List.of("", "#", "0000", "0101", "11", "01010", "11001100"));
        
        System.out.println();
        
        System.out.println("Testing MNTM2 - Simulating a Multi-track Turing Machine Transducer to double x --> 2x, "
                + "x is a positive number in unary number system");
        InitialStateAndTapeHeadCollection<MntmState> config2 = mntmConfig2();
        MultiTapeNondeterministicTuringMachine mntm2 = new MultiTapeNondeterministicTuringMachine(
                config2.collection(),
                new DefaultControlUnit(config2.state())
        );
        testTransducer(mntm2, List.of("", "#", "0", "1", "11", "111", "11111"));
    }
    
    public static void testMtdtm() {
        System.out.println("Testing MTDTM - Transducer to perform x --> 2x, x is a positive number in unary number system");
        var initialState = mtdtmConfig();
        var tapes = new TapeOrderedCollection();
        for (int i = 0; i < initialState.getTapeCount(); i++) {
            tapes.add(new InfiniteTape(BLANK));
        }
        MultiTrackTuringMachine mtdtm = new MultiTrackTuringMachine(
                tapes,
                DefaultReadWriteHead::new,
                new DefaultControlUnit<>(initialState)
        );
        testTransducer(mtdtm, List.of("", "#", "0", "1", "11", "111", "11111"));
    }
    
    public static void testMtntm() {
        System.out.println("Testing MTNTM1 - Transducer to perform x --> 2x, x is a positive number in unary number system");
        var initialState = mtntmConfig1();
        var tapes = new TapeOrderedCollection();
        for (int i = 0; i < initialState.getTapeCount(); i++) {
            tapes.add(new InfiniteTape(BLANK));
        }
        MultiTrackNondeterministicTuringMachine mtntm = new MultiTrackNondeterministicTuringMachine(
                tapes,
                DefaultReadWriteHead::new,
                new DefaultControlUnit<>(initialState)
        );
        testTransducer(mtntm, List.of("", "#", "0", "1", "11", "111", "11111"));
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
    
    /**
     * Turing Machine for Language: 0^n.1^n.2^n, n >= 1
     * @return The initial state
     */
    public static DtmState dtmConfig1() {
        DtmState q0 = new DtmState();
        DtmState q1 = new DtmState();
        DtmState q2 = new DtmState();
        DtmState q3 = new DtmState();
        DtmState q4 = new DtmState();
        DtmState q5 = new DtmState();
        
        q0.addTransition(q1, '0', 'A', Movements.right())
                .addTransition(q4, 'B', 'B', Movements.right());
        q1.addSelfLoop('B', 'B', Movements.right())
                .addSelfLoop('0', '0', Movements.right())
                .addTransition(q2, '1', 'B', Movements.right());
        q2.addSelfLoop('C', 'C', Movements.right())
                .addSelfLoop('1', '1', Movements.right())
                .addTransition(q3, '2', 'C', Movements.left());
        q3.addSelfLoop('1', '1', Movements.left())
                .addSelfLoop('0', '0', Movements.left())
                .addSelfLoop('B', 'B', Movements.left())
                .addSelfLoop('C', 'C', Movements.left())
                .addTransition(q0, 'A', 'A', Movements.right());
        q4.addSelfLoop('B', 'B', Movements.right())
                .addSelfLoop('C', 'C', Movements.right())
                .addTransition(q5, BLANK, BLANK, Movements.right());
        
        return q0;
    }
    
    /**
     * Turing Machine to compute the sum of 2 positive numbers in unary number system
     * @return The initial state
     */
    public static DtmState dtmConfig2() {
        DtmState q0 = new DtmState();
        DtmState q1 = new DtmState();
        DtmState q2 = new DtmState();
        DtmState q3 = new DtmState();
        DtmState q4 = new DtmState();
        DtmState HALT = new DtmState();
        
        q0.addTransition(q1, '1', '1', Movements.right());
        q1.addSelfLoop('1', '1', Movements.right())
                .addTransition(q2, '+', '1', Movements.right());
        q2.addSelfLoop('1', '1', Movements.right())
                .addTransition(q3, BLANK, BLANK, Movements.left());
        q3.addTransition(q4, '1', BLANK, Movements.right());
        q4.addTransition(HALT, BLANK, BLANK, Movements.right());
        
        return q0;
    }
    
    /**
     * Turing Machine to compute the parity of a binary number
     * @return The initial state
     */
    public static DtmState dtmConfig3() {
        DtmState q0 = new DtmState();
        DtmState q1 = new DtmState();
        DtmState q2 = new DtmState();
        DtmState HALT = new DtmState();
        
        q0.addTransition(q1, '0', '0', Movements.right())
                .addTransition(q2, '1', '1', Movements.right());
        q1.addSelfLoop('0', '0', Movements.right())
                .addTransition(q2, '1', '1', Movements.right())
                .addTransition(HALT, BLANK, '0', Movements.right());
        q2.addSelfLoop('0', '0', Movements.right())
                .addTransition(q1, '1', '1', Movements.right())
                .addTransition(HALT, BLANK, '1', Movements.right());
        
        return q0;
    }
    
    /**
     * Non-deterministic Turing Machine for Language: a+
     * @return The initial state
     */
    public static NtmState ntmConfig1() {
        NtmState q0 = new NtmState();
        NtmState q1 = new NtmState();
        NtmState HALT = new NtmState();
        
        q0.addSelfLoop('a', 'a', Movements.right())
                .addTransition(q1, 'a', 'a', Movements.right());
        q1.addTransition(HALT, BLANK, BLANK, Movements.stay());
        
        return q0;
    }
    
    /**
     * Non-deterministic Turing Machine to invert a binary string
     * @return The initial state
     */
    public static NtmState ntmConfig2() {
        NtmState q0 = new NtmState();
        NtmState q1 = new NtmState();
        NtmState ACCEPT = new NtmState();
        
        q0.addTransition(q1, '0', '0', Movements.stay())
                .addTransition(q1, '1', '1', Movements.stay());
        q1.addSelfLoop('0', '1', Movements.right())
                .addSelfLoop('1', '0', Movements.right())
                .addTransition(ACCEPT, BLANK, BLANK, Movements.right());
        
        return q0;
    }
    
    /**
     * Multi-tape Turing Machine for Language: w.#.w, w a is binary string
     * @return The initial state and tape head collection
     */
    public static InitialStateAndTapeHeadCollection<MdtmState> mdtmConfig1() {
        MdtmState q0 = new MdtmState();
        MdtmState q1 = new MdtmState();
        MdtmState q2 = new MdtmState();
        MdtmState q3 = new MdtmState();
        MdtmState q4 = new MdtmState();
        
        q0
                .addTransition(q1,
                        new TuringTransitionConfig('1',  '1', Movements.right()),
                        new TuringTransitionConfig(BLANK,  '1', Movements.right())
                )
                .addTransition(q1, 
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, '0', Movements.right())
                );
        
        q1
                .addSelfLoop(
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, '0', Movements.right())
                )
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, '1', Movements.right())
                )
                .addTransition(q2, 
                        new TuringTransitionConfig('#', '#', Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left())
                );
        
        q2
                .addSelfLoop(
                        new TuringTransitionConfig('#', '#', Movements.stay()),
                        new TuringTransitionConfig('1', '1', Movements.left())
                )
                .addSelfLoop(
                        new TuringTransitionConfig('#', '#', Movements.stay()),
                        new TuringTransitionConfig('0', '0', Movements.left())
                )
                .addTransition(q3, 
                        new TuringTransitionConfig('#', '#', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                );
        
        q3
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig('1', '1', Movements.right())
                )
                .addSelfLoop(
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig('0', '0', Movements.right())
                )
                .addTransition(q4, 
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                );
        
        return new InitialStateAndTapeHeadCollection<>(q0, makeMultiTapeHeadCollection(q0.getTapeCount()));
    }
    
    /**
     * Multi-tape Turing Machine for Language: a^n.b^n.c^n, n >= 0
     * @return The initial state and tape head collection
     */
    public static InitialStateAndTapeHeadCollection<MdtmState> mdtmConfig2() {
        MdtmState q0 = new MdtmState();
        MdtmState q1 = new MdtmState();
        MdtmState q2 = new MdtmState();
        MdtmState q3 = new MdtmState();
        MdtmState q4 = new MdtmState();
        
        q4
                .addTransition(q0, 
                        new TuringTransitionConfig('a', 'a', Movements.right()),
                        new TuringTransitionConfig(BLANK, 'a', Movements.right())
                )
                .addTransition(q3,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                );
        
        q0
                .addSelfLoop(
                        new TuringTransitionConfig('a', 'a', Movements.right()),
                        new TuringTransitionConfig(BLANK, 'a', Movements.right())
                )
                .addTransition(q1, 
                        new TuringTransitionConfig('b', 'b', Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left())
                );
        
        q1
                .addSelfLoop(
                        new TuringTransitionConfig('b', 'b', Movements.right()),
                        new TuringTransitionConfig('a', 'a', Movements.left())
                )
                .addTransition(q2, 
                        new TuringTransitionConfig('c', 'c', Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                );
        
        q2
                .addSelfLoop(
                        new TuringTransitionConfig('c', 'c', Movements.right()),
                        new TuringTransitionConfig('a', 'a', Movements.right())
                )
                .addTransition(q3, 
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                );
        
        return new InitialStateAndTapeHeadCollection<>(q4, makeMultiTapeHeadCollection(q4.getTapeCount()));
    }
    
    /**
     * Multi-tape Turing Machine to detect word w in Language w.w, w is a binary string
     * @return The initial state and tape head collection
     */
    public static InitialStateAndTapeHeadCollection<MntmState> mntmConfig1() {
        MntmState q0 = new MntmState();
        MntmState q1 = new MntmState();
        MntmState q2 = new MntmState();
        MntmState q3 = new MntmState();
        
        q0
                .addSelfLoop(
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay())
                )
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay())
                )
                .addTransition(q1,
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, '0', Movements.right())
                )                
                .addTransition(q1,
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, '1', Movements.right())
                );
        
        q1
                .addSelfLoop(
                        new TuringTransitionConfig('0', '0', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, '0', Movements.right())
                )                
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, '1', Movements.right())
                )
                .addTransition(q2,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left())
                );
        
        q2
                .addSelfLoop(
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig('0', '0', Movements.left()),
                        new TuringTransitionConfig('0', '0', Movements.left())
                )
                .addSelfLoop(
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig('1', '1', Movements.left()),
                        new TuringTransitionConfig('1', '1', Movements.left())
                )
                .addTransition(q3,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay())
                );
        
        return new InitialStateAndTapeHeadCollection<>(q0, makeMultiTapeHeadCollection(q0.getTapeCount()));
    }
    
    /**
     * Multi-tape Turing Machine simulating Multi-track Turing Machine to double x --> 2x, x is a positive number in unary number system
     * @return The initial state and tape head collection
     */
    public static InitialStateAndTapeHeadCollection<MntmState> mntmConfig2() {
        MntmState q0 = new MntmState();
        MntmState q1 = new MntmState();
        MntmState q2 = new MntmState();
        MntmState q3 = new MntmState();
        
        q0
                .addTransition(q3,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig('1', '1', Movements.stay())
                )
                .addTransition(q3,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.stay())
                )               
                .addTransition(q1,
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, '1', Movements.right())
                );
        
        q1
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right())
                )                
                .addSelfLoop(
                        new TuringTransitionConfig(BLANK, BLANK, Movements.right()),
                        new TuringTransitionConfig('1', '1', Movements.right())
                )
                .addTransition(q2,
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left()),
                        new TuringTransitionConfig(BLANK, '1', Movements.left())
                );
        
        q2
                .addSelfLoop(
                        new TuringTransitionConfig('1', '1', Movements.left()),
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left())
                )
                .addSelfLoop(
                        new TuringTransitionConfig(BLANK, BLANK, Movements.left()),
                        new TuringTransitionConfig('1', '1', Movements.left())
                )
                .addTransition(q0,
                        new TuringTransitionConfig('1', '1', Movements.right()),
                        new TuringTransitionConfig('1', '1', Movements.right())
                );
        
        return new InitialStateAndTapeHeadCollection<>(q0, makeMultiTapeHeadCollection(q0.getTapeCount()));
    }
    
    private static MultiTrackDtmState mtdtmConfig() {
        MultiTrackDtmState q0 = new MultiTrackDtmState();
        MultiTrackDtmState q1 = new MultiTrackDtmState();
        MultiTrackDtmState q2 = new MultiTrackDtmState();
        MultiTrackDtmState HALT = new MultiTrackDtmState();
        
        q0
                .addTransition(HALT, Movements.stay(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(HALT, Movements.stay(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addTransition(q1, Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, '1')
                );
        
        q1
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(q2, Movements.left(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, '1')
                );
        
        q2                
                .addSelfLoop(Movements.left(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addSelfLoop(Movements.left(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(q0, Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig('1', '1')
                );
        
        return q0;
    }
    
    private static MultiTrackNtmState mtntmConfig1() {
        MultiTrackNtmState q0 = new MultiTrackNtmState();
        MultiTrackNtmState q1 = new MultiTrackNtmState();
        MultiTrackNtmState q2 = new MultiTrackNtmState();
        MultiTrackNtmState HALT = new MultiTrackNtmState();
        
        q0
                .addTransition(HALT, Movements.stay(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(HALT, Movements.stay(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addTransition(q1, Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, '1')
                );
        
        q1
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(q2, Movements.left(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, '1')
                );
        
        q2                
                .addSelfLoop(Movements.left(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )
                .addSelfLoop(Movements.left(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addTransition(q0, Movements.right(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig('1', '1')
                );
               
        // Additional arbitrary nondeterministic paths
        q0
                .addTransition(q1, Movements.left(),
                        new MultiTrackTuringTransitionConfig('1', '1'),
                        new MultiTrackTuringTransitionConfig(BLANK, '1')
                )
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addSelfLoop(Movements.left(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig('1', '1')
                )
                .addSelfLoop(Movements.right(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                )               
                .addSelfLoop(Movements.stay(),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK),
                        new MultiTrackTuringTransitionConfig(BLANK, BLANK)
                );
        
        return q0;
    }
    
    private static MultiTapeHeadCollection makeMultiTapeHeadCollection(int tapeCount) {
        MultiTapeHeadPairCollection tapeHeads = new MultiTapeHeadPairCollection();
        for (int i = 0; i < tapeCount; i++) {
            tapeHeads.add(new InfiniteTape(BLANK), new DefaultReadWriteHead());
        }
        return tapeHeads;
    }

    public record InitialStateAndSymbol<T extends State>(T state, char symbol) {}
    
    public record InitialStateAndTapeHeadCollection<T extends MultiTapeState>(T state, MultiTapeHeadCollection collection) {}
}
