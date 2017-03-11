package unl.cse.tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
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
		
		process(nfa31,inputs1);
		
//		generate(nfa31, (args.length>2)?Integer.parseInt(args[2]):10, (args.length>2)?Integer.parseInt(args[3]):0);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void process(NonDeterFiniteAutomata nfa, String[] inputs) throws IOException {
		/* Process strings */
//		String[] inputs1 = {"ZS\\ZS\\Z","Z*S/Z\\S/Z","ZB//X\\SBS/","ZBXS","ZBBBBB","XS////\\\\*XS","Z*Z*ZZ", "SSSS"};
		ArrayList<String> accepted = new ArrayList<String>();
		PrintWriter pw = new PrintWriter(new FileWriter("output"));
		System.out.println("Rejected:");
		pw.println("Rejected:");
		for (String in : inputs) {
			in = in.trim();
			List<Symbol<String>> symbolList = Generator.getSymbols(in);		
			if(!nfa.process(symbolList)) 
			{ System.out.printf("\tProcessing : '%-40s : %d     : %-100s\n",in+"'", (symbolList.size()-1)/2, nfa.processandFinal(symbolList));
			pw.printf("\tProcessing : '%-40s : %d     : %-100s\n",in+"'", (symbolList.size()-1)/2, nfa.processandFinal(symbolList));}
			else
			accepted.add(String.format("\tProcessing : '%-40s : %d     : %-100s\n",in + "'", (symbolList.size()-1)/2, nfa.processandFinal(symbolList)));			

		}
	
		System.out.println("Accepted:");
		pw.println("Accepted:");
		for(String out: accepted){
			System.out.print(out);
		    pw.print(out);
		}
		pw.close();
	}
	
	public static void generate(NonDeterFiniteAutomata nfa, int num, int size) {
		HashMap<String,String> hMap = new HashMap<String,String>();
		List<Symbol<String>> Xarray = Generator.getSymbols(Xinputs,",");
		List<Symbol<String>> Yarray = Generator.getSymbols(Yinputs,",");

		if(size==0) size=3;
		String input="";
		int rej=0,acc=0;
//		System.out.printf("Processing : %-21s : Length : %-100s\n","Input", "Final State(s)");

//		System.out.println("Rejected of length " + size + ":");
		
		for(int i=0;(i<num || rej<=num/2);) {
			Symbol<String> prevX = Xarray.get((int)(Math.random()*Xarray.size()));
			Symbol<String> prevY = null;
			Symbol<String> nextX = null;
			input = prevX.getValue().trim();
			int length = (int) (Math.random()*(size-3)) + 3;
			while (length > 1) {

			nextX = new Symbol<String>(Xarray.get((int)(Math.random()*Xarray.size())).getValue().trim());
			Symbol<String> nextY = null;
			if(length>1) 
				nextY = new Symbol<String>(Yarray.get((int)(Math.random()*Yarray.size())).getValue().trim());
			
//			System.out.println("i=" + i + " length=" + length + " prevY=" + prevY + " prevX="+prevX + "nextY=" + nextY + 
//					" nextX=" + nextX );
			// Not allow ZOS or SOZ or =OS or =OZ or =O=
			if((nextY!=null && prevY!=null && prevX.getValue().equals("O")) && 
				(   ((prevY.getValue().equalsIgnoreCase("Z") && nextY.getValue().equalsIgnoreCase("S"))
				   ||(prevY.getValue().equalsIgnoreCase("S") && nextY.getValue().equalsIgnoreCase("Z"))
//				   ||(prevY.getValue().equalsIgnoreCase("=") && nextY.getValue().equalsIgnoreCase("S"))
//				   ||(prevY.getValue().equalsIgnoreCase("=") && nextY.getValue().equalsIgnoreCase("Z"))
//				   ||(prevY.getValue().equalsIgnoreCase("S") && nextY.getValue().equalsIgnoreCase("Z"))
				   )))
			{		//		System.out.println("i=" + i + " length=" + length + " prevY=" + prevY + " prevX="+prevX + "nextY=" + nextY + 
				//" nextX=" + nextX );

				continue; }

			// Not allow O=O 
			if(nextY!=null && (nextY.getValue().equals("=") && 
//					(prevX.getValue().equalsIgnoreCase("O") && nextX.getValue().equalsIgnoreCase("O"))
					(prevX.getValue().equalsIgnoreCase("O") || nextX.getValue().equalsIgnoreCase("O"))
				))
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

//			if(input.contains("ZOS"))
//				System.out.println("i=" + i + " length=" + length + " prevY=" + prevY + " prevX="+prevX + " nextY=" + nextY + 
//				" nextX=" + nextX );

//				System.out.println("\t : " + input);
			prevX = nextX;
			prevY = nextY;
		}
			
		if(hMap.containsKey(input)) continue;
		hMap.put(input, input);
//		System.out.println("Input generated: " + input);
		List<Symbol<String>> symbolList = Generator.getSymbols(input);		
		if(!nfa.process(symbolList)) rej++; else acc++;
	//		{ System.out.printf("Processing : '%-20s : %d     : %-100s\n",input+"'", input.length(), nfa.processandFinal(symbolList)); i++;}
//		else
//			System.out.println("Processing " + input + ": " + nfa31.processandFinal(symbolList));			
		{ System.out.printf("%-20s\n",input); i++;}
		//else
		}
		System.out.println("rej="+rej + " acc="+acc);
	}
}
