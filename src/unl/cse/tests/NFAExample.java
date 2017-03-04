package unl.cse.tests;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import unl.cse.automata.DeterFiniteAutomata;
import unl.cse.automata.NonDeterFiniteAutomata;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.TransitionTable;

public class NFAExample {

	public static void main(String args[]) {

		try {
		Scanner sc = new Scanner(new File(args[0]));
				
		SymbolSet<String> symbols3= Generator.getSymbolSet(sc.next(),sc.nextLine().trim());
		StateSet states31= Generator.getStateSet(sc.next(),sc.next(),sc.nextLine().trim());
		String nfaLabel = sc.next().trim();
		String epsilon = sc.next().trim();
		String ssep = sc.next().trim();
		String csep = sc.next().trim();
		String semi = ";";
		String transitions = sc.nextLine().trim();
		while(sc.hasNext()) {
			transitions+=(semi + sc.nextLine());
		}
		sc.close();
		TransitionTable<String> tTable31 = Generator.getTransitionTable(states31,transitions,semi,ssep,csep);
		NonDeterFiniteAutomata nfa31 = new NonDeterFiniteAutomata(states31,symbols3,tTable31,nfaLabel, new Symbol<String>(epsilon));
//		nfa31.exportAsPDF(nfaLabel);
		/*Print the transition table*/ 
		System.out.println("Stateset=" + states31);
		System.out.println(nfa31.getTransitions());
		
		DeterFiniteAutomata dfa33 = nfa31.convertToDFA();
		System.out.println("Name:dfa33 NoOfStates:" + dfa33.getStates().size());
		System.out.println("Name:dfa33 States:" + dfa33.getStates());
		System.out.println("Transitions:" + dfa33.getTransitions());
		
		/* Process strings */
		String[] inputs1 = {"ZSZSZS","Z*BBBBB","ZBXSBS/","ZBXS","ZB\\/\\/\\/BB\\\\\\BBB","XS*XS","SSSSSSS", "SS"};
		for (String in : inputs1) {
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			System.out.println("Processing " + in + ": " + nfa31.processandFinal(symbolList));			
		}
		
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}
