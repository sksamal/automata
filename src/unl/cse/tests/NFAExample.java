package unl.cse.tests;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import unl.cse.automata.DeterFiniteAutomata;
import unl.cse.automata.NonDeterFiniteAutomata;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.TransitionTable;

public class NFAExample {

	public static String Yinputs = "Z,S,=,X";
	public static String Xinputs = "|,O";
	
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
		
		Scanner sc1 = new Scanner(new File(args[1]));
		String[] inputs1 = new String[Integer.parseInt(sc1.nextLine())];
		int i=0;
		while(sc1.hasNext()) {
			inputs1[i++]=(sc1.nextLine());
		}
		sc1.close(); sc.close();
		TransitionTable<String> tTable31 = Generator.getTransitionTable(states31,transitions,semi,ssep,csep);
		NonDeterFiniteAutomata nfa31 = new NonDeterFiniteAutomata(states31,symbols3,tTable31,nfaLabel, new Symbol<String>(epsilon));
//		nfa31.exportAsPDF(nfaLabel);
		System.out.println("NFA NoOfStates:" +  nfa31.getStates().size());
		System.out.println("NFA NoOfTransitions:" + nfa31.getTransitions().size());
		/*Print the transition table*/ 
		System.out.println(states31);
		System.out.println(nfa31.getTransitions().sString());
		
		DeterFiniteAutomata dfa33 = nfa31.convertToDFA();
		System.out.println("DFA: NoOfStates:" + dfa33.getStates().size());
		System.out.println("DFA: NoOfTransitions:" + dfa33.getTransitions().size());
		System.out.println("DFA: States:" + dfa33.getStates());
		System.out.println("DFA: Transitions:" + dfa33.getTransitions().sString());
		
//		process(nfa31,inputs1);
		
		generate(nfa31, (args.length>2)?Integer.parseInt(args[2]):10, (args.length>2)?Integer.parseInt(args[3]):0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void process(NonDeterFiniteAutomata nfa, String[] inputs) {
		/* Process strings */
//		String[] inputs1 = {"ZS\\ZS\\Z","Z*S/Z\\S/Z","ZB//X\\SBS/","ZBXS","ZBBBBB","XS////\\\\*XS","Z*Z*ZZ", "SSSS"};
		ArrayList<String> accepted = new ArrayList<String>();
		System.out.println("Rejected:");
		for (String in : inputs) {
			in = in.trim();
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			if(!nfa.process(symbolList)) 
			{ System.out.printf("\tProcessing : '%-40s : %d     : %-100s\n",in+"'", symbolList.size(), nfa.processandFinal(symbolList));}
			else
			accepted.add(String.format("\tProcessing : '%-40s : %d     : %-100s\n",in + "'", symbolList.size(), nfa.processandFinal(symbolList)));			

		}
	
		System.out.println("Accepted:");
		for(String out: accepted)
			System.out.print(out);
	}
	
	public static void generate(NonDeterFiniteAutomata nfa, int num, int size) {
		List<Symbol<String>> Xarray = Generator.getSymbols(Xinputs,",");
		List<Symbol<String>> Yarray = Generator.getSymbols(Yinputs,",");

		if(size==0) size=3;
		String input="";
//		System.out.printf("Processing : %-21s : Length : %-100s\n","Input", "Final State(s)");

		System.out.println("Rejected of length " + size + ":");
		Symbol<String> prevX = Xarray.get((int)(Math.random()*Xarray.size()));
		Symbol<String> prevY = null;
		
		for(int i=0;i<num;) {
			input = prevX.getValue().trim(); //prevsymbol.getValue().trim();
			int length = (int) (Math.random()*(size-3)) + 3;

			Symbol<String> nextX = null;
			while (length > 1) {
			if(nextX == null)
				nextX = new Symbol<String>(Xarray.get((int)(Math.random()*Xarray.size())).getValue().trim());
			
			Symbol<String> nextY = null;
			if(length>1) 
				nextY = new Symbol<String>(Yarray.get((int)(Math.random()*Yarray.size())).getValue().trim());
			
//			System.out.println("i=" + i + " length=" + length + " prevY=" + prevY + " prevX="+prevX + "nextY=" + nextY + 
//					" nextX=" + nextX );
			// Not allow Z0S or S0Z
			if((nextY!=null) && (prevY!=null) && ((prevY.getValue().equalsIgnoreCase("Z") && nextX.getValue().equals("O") && nextY.getValue().equalsIgnoreCase("S"))
					|| ((prevY.getValue().equalsIgnoreCase("S") && nextX.getValue().equals("O") && nextY.getValue().equalsIgnoreCase("Z")))))
				continue;

			// Not allow 0=0
			if((nextX!=null && nextY!=null) && (prevX.getValue().equalsIgnoreCase("O") && nextY.getValue().equals("=") && nextX.getValue().equalsIgnoreCase("0")))
				continue;
			length--;
			//
//			if(next2symbol!=null && prevsymbol!=null)
//			if(prevsymbol.getValue().equalsIgnoreCase("/") && nextsymbol.getValue().equalsIgnoreCase("Z") &&
//					next2symbol.getValue().equalsIgnoreCase("/"))
//				nextsymbol.setValue("/");
//			
//			if(next2symbol!=null && prevsymbol!=null)
//			if(prevsymbol.getValue().equalsIgnoreCase("\\") && nextsymbol.getValue().equalsIgnoreCase("S") &&
//							next2symbol.getValue().equalsIgnoreCase("\\"))
//				nextsymbol.setValue("\\");

			if(nextY!=null)		input = input + nextY.getValue().trim() + nextX.getValue().trim();
			else	input = input + nextX.getValue().trim();
				
//			System.out.println("\t : " + input);
			prevX = nextX;
			prevY = nextY;
		}
//		System.out.println("Input generated: " + input);
		List<Symbol<String>> symbolList = Generator.getSymbols(input);		
		if(!nfa.process(symbolList)) 
	//		{ System.out.printf("Processing : '%-20s : %d     : %-100s\n",input+"'", input.length(), nfa.processandFinal(symbolList)); i++;}
//		else
//			System.out.println("Processing " + input + ": " + nfa31.processandFinal(symbolList));			
		{ System.out.printf("%-20s\n",input); i++;}
		//else
		}
	}
}
