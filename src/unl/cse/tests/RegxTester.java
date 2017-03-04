/* A utility class to test Regular expressions */
package unl.cse.tests;


import unl.cse.automata.DeterFiniteAutomata;
import unl.cse.automata.NonDeterFiniteAutomata;
import unl.cse.automata.RegularExpression;
import unl.cse.automata.elements.SymbolSet;

public class RegxTester {

	public static void main(String args[]) {
		
		/*Inputs */
		SymbolSet<String> symbols= Generator.getSymbolSet("a,b,e",",");
		String[] reString = {"(a|b)*a","((a|b)(a|b))*","aaa*b*a*a","a*b(a*ba*ba*)*a*","(a|b)*(aba|bab)(a|b)*" };
		String[][] inputs = {
							{"aaaa","aba","bba","a","b","bbb"},
							{"abbabb","e","aa","ab","aaaaa","bba"},
							{"aabaa","aaa","aabba","abbaa","abbbbbbbbbbbbbbbbbbba","bbaa"},
							{"b","bbb","ababababab","aaaaabaaaaa","e","aaa"},
							{"ababab","bababa","abab","baba","aba","e"}
							};
		RegularExpression re = new RegularExpression(symbols);
		NonDeterFiniteAutomata nfa[] = new NonDeterFiniteAutomata[reString.length];
		DeterFiniteAutomata dfa[] = new DeterFiniteAutomata[reString.length];
		for (int i=0;i<reString.length;i++) {
			System.out.println("Running TestCase" + i + ":");
			System.out.println("Regular Expression: " + reString[i]);
			System.out.print("Strings:");
			for(String input: inputs[i])
				System.out.print(" " + input);
				
			try {
			nfa[i] = re.parsetoNFA(reString[i]);
			System.out.println("\n" + nfa[i].getTransitions());
			nfa[i].exportAsLatex("NFA-RE-" + (i+1));
			nfa[i].removeEpsilon();
			dfa[i] = nfa[i].convertToDFA();
			System.out.println(dfa[i].getTransitions());
			dfa[i].exportAsLatex("DFA-RE-" + (i+1));
			

			System.out.println("Processing inputs for RE-" + i + ": " + reString[i]);
			for(String input : inputs[i]) {
				System.out.println("       Input:" + input + " NFA:" + nfa[i].process(Generator.getSymbols(input))//);
				+		" DFA:" + dfa[i].process(Generator.getSymbols(input)));
			}
			}
			
			catch(Exception e) {
				e.printStackTrace();
				continue;
			}
			
		}	
	}

}
