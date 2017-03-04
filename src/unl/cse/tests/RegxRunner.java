/* The main class that runs */
package unl.cse.tests;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import unl.cse.automata.DeterFiniteAutomata;
import unl.cse.automata.NonDeterFiniteAutomata;
import unl.cse.automata.RegularExpression;
import unl.cse.automata.elements.SymbolSet;

public class RegxRunner {

	public static void main(String args[]) {
		
		/* Logging setup */
		String regxlog = "regx.log";
		PrintWriter logger = null;
		try {
			logger = new PrintWriter(new File(regxlog));
		}
		
		catch (IOException e) {
			System.out.println("Unable to write to" + regxlog);
			regxlog = "Standard Output";
			logger = new PrintWriter(System.err);
		}
		
		logger.println("Assuming default symbols: [a,b,e]");
		
		logger.println("Logging to : " + regxlog);
		
		/* Create a new symbol set - default one */
		SymbolSet<String> symbols= Generator.getSymbolSet("a,b,e",",");
		
		/* Read inputs and validate */
		if(args.length < 2) {
			System.out.println("Usage: RegxRunner regExpression str1 str2 ...strN");
			System.out.println("	regExpression = Regular Expression E.g (a*b)|c");
			System.out.println("	str<i> = list of strings separated by spaces");
			System.exit(5);
		}
		String reString = args[0];
		String[] inputs = new String[args.length-1];
		for(int i=0;i<args.length-1;i++)
			inputs[i] = args[i+1];
		
		/* Create Regular Expression */
		logger.println("Creating Regular Expression for: " + reString);
		RegularExpression re = new RegularExpression(symbols);
		
		/* Creating NFA */
		logger.println("Creating NFA...");
		NonDeterFiniteAutomata nfa = re.parsetoNFA(reString);
		
		/* Give a random name to NFA and log the transitions */
		int rNumber = new Random().nextInt(100);
		logger.println("Name: NFA-RE-" + rNumber);
		logger.println("" + nfa.getTransitions());
		logger.println("Trying to export to latex..");
		nfa.exportAsLatex("NFA-RE-" + rNumber);
		
		/* Removing Epsilons */
		logger.println("Removing epsilon transitions...");
		nfa.removeEpsilon();
		
		/* Converting to DFA */
		logger.println("Converting to DFA...");
		DeterFiniteAutomata dfa= nfa.convertToDFA();
		logger.println("Name: DFA-RE-" + rNumber);
		logger.println("" + dfa.getTransitions());
		dfa.exportAsLatex("DFA-RE-" + rNumber);
		
		/* Processing Strings */
		logger.println("Processing strings for RE-" + rNumber + " :" + reString);
		for(String input : inputs) {
			System.out.println(translate(dfa.process(Generator.getSymbols(input))));
			logger.println("       Input:" + input + " Output:: NFA:" + nfa.process(Generator.getSymbols(input))
					+		" DFA:" + dfa.process(Generator.getSymbols(input)));
		}
		
		logger.close();
		
	}
	
	public static String translate(boolean b) {
		if(b) return "yes";
		else return "no";
	}

}
