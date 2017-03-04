package unl.cse.tests;

import java.util.List;

import unl.cse.automata.DeterFiniteAutomata;
import unl.cse.automata.NonDeterFiniteAutomata;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.TransitionTable;

public class AutomataTester {

	public static void main(String args[]) {
		
	/*	SymbolSet<String> symbols= Generator.getSymbolSet("0,1,e",",");
		StateSet states= Generator.getStateSet("q_0:1,q_1,q_2,q_3:0:1,q_4:0:1",",",":");
		TransitionTable<String> tTable = Generator.getTransitionTable(states,"q_0-1-q_1;q_0-0-q_0;q_1-1-q_2;"
				+ "q_1-0-q_2;q_2-0-q_1;q_2-1-q_3;q_3-1-q_3;q_3-0-q_4;q_4-0-q_4;q_4-1-q_2",";","-",",");
		FiniteAutomata fa1 = new DeterFiniteAutomata(states,symbols,tTable);
		fa1.exportAsPDF("FA1");
		System.out.println(fa1.getTransitions());
		
		
		 //Create a finite Automata for Q1 - FSM for divisibility by 3 
		SymbolSet<String> symbols1= Generator.getSymbolSet("0,1",",");
		StateSet states1= Generator.getStateSet("q0:1:1,q1,q2",",",":");
		TransitionTable<String> tTable1 = Generator.getTransitionTable(states1,"q0-0-q0;q0-1-q1;q1-0-q2;q1-1-q0;q2-0-q1;q2-1-q2",";","-",":");
		FiniteAutomata fa2 = new DeterFiniteAutomata(states1,symbols1,tTable1);
		fa2.exportAsPDF("FA2");
		
		 //Print the transition table 
		//System.out.println(fa2.getTransitions());
		
		 //Input Symbol 
		String[] inputs = {"010","00","11","110","1101","1111"};
		for (String in : inputs) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + fa2.process(symbolList));
		}	
		
		SymbolSet<String> symbols2= Generator.getSymbolSet("0,1,e",",");
		StateSet states2= Generator.getStateSet("q0:1,q1,q2,q3:0:1",",",":");
		TransitionTable<String> tTable2 = Generator.getTransitionTable(states2,"q0-0-q0;q0-0-q1;q0-1-q0;q1-0-q2;q1-1-q2;q2-0-q3;q3-1-q3;q3-0-q3;q0-e-q2",";","-",":");
		FiniteAutomata fa3 = new NonDeterFiniteAutomata(states2,symbols2,tTable2,"e");
		fa3.exportAsPDF("NFA1");
		 //Print the transition table 
		//System.out.println(fa3.getTransitions());
		
		String[] inputs1 = {"0010","0110","10101010111","010","1111"};
		for (String in : inputs1) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + fa3.process(symbolList));
		}
		*/
		
		SymbolSet<String> symbols3= Generator.getSymbolSet("0,1",",");
		StateSet states31= Generator.getStateSet("a0:1,a1,a2,a3:0:1",",",":");
		TransitionTable<String> tTable31 = Generator.getTransitionTable(states31,"a0-0-a1;a1-0-a2;a2-0-a3",";","-",":");
		NonDeterFiniteAutomata nfa31 = new NonDeterFiniteAutomata(states31,symbols3,tTable31,"NFA31");
		nfa31.exportAsPDF("NFA31");
		/*Print the transition table*/ 
		System.out.println(nfa31.getTransitions());
		
	
		StateSet states32= Generator.getStateSet("b0:1,b1,b2,b3:0:1",",",":");
		TransitionTable<String> tTable32 = Generator.getTransitionTable(states32,"b0-1-b1;b1-1-b2;b2-1-b3",";","-",":");
		NonDeterFiniteAutomata nfa32 = new NonDeterFiniteAutomata(states32,symbols3,tTable32,"e");
		nfa32.exportAsPDF("NFA32");
		/*Print the transition table*/ 
		System.out.println(nfa32.getTransitions());
	
		/* Test for UNION */
		NonDeterFiniteAutomata nfa3 = nfa31.union(nfa32);		
		/*Print the transition table*/ 
		System.out.println(nfa3.getTransitions());
		nfa3.exportAsPDF("NFA3");
		
		/* Test for Remove EPSILON */
		nfa3.removeEpsilon();
		System.out.println(nfa3.getTransitions());
		nfa3.exportAsPDF("NFA3");
		
		/* Process strings */
		String[] inputs1 = {"0010","0110","10101010111","010","1111","000","0000","00000","000000"};
		for (String in : inputs1) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + nfa31.process(symbolList));
		}
		
		/* Kleene Star test */
		NonDeterFiniteAutomata nfa33 = nfa31.kleeneStar();
		System.out.println(nfa33.getTransitions());
		nfa33.exportAsPDF("NFA33");
		
		/* Remove Epsilon */
		nfa33.removeEpsilon();
		System.out.println("NFA33:" + nfa33.getTransitions());
		nfa33.exportAsPDF("NFA33-e");
	
		/* PROCESS */
		for (String in : inputs1) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + nfa33.process(symbolList));
		}
		
		/* Convert to DFA */
		DeterFiniteAutomata dfa33 = nfa33.convertToDFA();
		System.out.println("DFA33:" + dfa33.getTransitions());
		dfa33.exportAsPDF("DFA33");
		
		for (String in : inputs1) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + dfa33.process(symbolList));
		}
		
		/* CONCATANATE */
		NonDeterFiniteAutomata nfa31u32 = nfa31.concatanate(nfa32);		
		/*Print the transition table*/ 
		System.out.println(nfa31u32.getTransitions());
		nfa31u32.exportAsPDF("NFA31U32");
		
	}
}
