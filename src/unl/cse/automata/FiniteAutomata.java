package unl.cse.automata;
/* This class is a subclass of automata
 * correspoinding to FiniteAutomata
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import unl.cse.automata.elements.State;
import unl.cse.automata.elements.StateComparator;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

public abstract class FiniteAutomata implements Automata {

	/* States symbols and transitions */
	StateSet states = null;
	SymbolSet<String> symbols = null;
	TransitionTable<String> transitions = null;
	String name = "Noname";   /* Unique name for the Automata */
	public final String LATEX2PDF = "xelatex"; /* latex compiler executable path and name */

	public FiniteAutomata() {
		
	}
	
    public FiniteAutomata(StateSet states,SymbolSet<String> symbols,TransitionTable<String> transitions,String name) {
	 this.states=states;
	 this.symbols=symbols;
	 this.transitions=transitions;
	 this.name=name;
	}
    
    public FiniteAutomata(StateSet states,SymbolSet<String> symbols,TransitionTable<String> transitions) {
   	 this.states=states;
   	 this.symbols=symbols;
   	 this.transitions=transitions;
   	}
   	
	
	public void addState(State s) {
		this.states.add(s);
	}
	
	public StateSet getStates() {
		return states;
	}

	public void setStates(StateSet states) {
		this.states = states;
	}

	public SymbolSet<String> getSymbols() {
		return symbols;
	}

	public void setSymbols(SymbolSet<String> symbols) {
		this.symbols = symbols;
	}

	public TransitionTable<String> getTransitions() {
		return transitions;
	}

	public void setTransitions(TransitionTable<String> transitions) {
		this.transitions = transitions;
	}

	public void addSymbol(Symbol<String> s) {
		this.symbols.add(s);
	}
	
	public abstract void addTransition(Transition<String> s);
	
	public abstract boolean process(List<Symbol<String>> input);
	
	public boolean validate(List<Symbol<String>> sList) {
		for(Symbol<String> s: sList)
			if(this.symbols.find(s)==null)
				{ System.out.println("s="+s); return false;}
			return true;
	}
	
	
	/* dumps latex code using tikz package */
    public void exportAsLatex(String file) {
    	
    	String[] node_pos = {"below of="};//,"above right of=","below right of=","above of=","below of="};
    	String[] edge_pos = {"[bend left]","[bend right]"};
    	try{
    	PrintWriter pw = new PrintWriter(new File(file+".tex"));
    	pw.write("\\documentclass{article}\n");
    	pw.write("\\usepackage{pgf}\n");
    	pw.write("\\usepackage{tikz}\n");
    	pw.write("\\usetikzlibrary{arrows,automata}\n");
    	pw.write("\\usepackage[latin1]{}\n");
    	pw.write("\\usepackage{fancyhdr}\n");
    	pw.write("\\pagestyle{fancy}\n");
    	pw.write("\\fancyhf{}\n");
    	pw.write("\\rhead{ssamal@cse.unl.edu}\n");
    	pw.write("\\lhead{CSCE828-Project1-" + file + "}\n");
    	pw.write("\\lfoot{\\copyright Suraj Samal}\n");
    	pw.write("\\rfoot{\\thepage}\n");
    	pw.write("\\begin{document}\n");
    	pw.write("\\title{CSCE828-Project1-ssamal@cse.unl.edu}\n");
    	pw.write("\\begin{tikzpicture}[->,>=stealth',shorten >=1pt,"
    			+ "auto,node distance=1.8cm,semithick]\n");
    	pw.write("   \\tikzstyle{every state}=[fill=red,draw=blue,text=white]\n");
    	
    	/* StateSet*/
    	State old = null;
    	int ni=0;
    	List<State> sList = new ArrayList<State>(states.getStates());
    	Collections.sort(sList, new StateComparator());
    	for(State s: sList) {
    		String out = "   \\node[state";
    		if(s.isInitial())
    			out+=",initial";
    		if(s.isFinal())
    			out+=",accepting";
    		out+="]   ";
    		String npos="    ";
    		if(old!=null)
    			npos= "[" + node_pos[(ni++%node_pos.length)] + old.getName() + "]";
    		out+="("+s.getName()+")  " + npos + "  {$"+s.getName()+"$};\n";
    		pw.write(out);
    		old=s;
    		}
    	pw.write("\n");
    	
    	/*Transitions*/
    	int ei=0;  
    	pw.write("   \\path  ");
    	for(Transition<String> t: transitions.getAll()) {    		
    		for(State dest : t.getDests().getStates()) {
    			String tOut="\n     ";
    			tOut+="(" + t.getSrc().getName() + ")  edge  ";
    			if(dest.getName().equalsIgnoreCase(t.getSrc().getName()))
    				tOut+="[loop above]  ";
    			else
    				tOut+=edge_pos[(ei++%edge_pos.length)]+"    ";
    		    tOut+="node {"+t.getSymbol().getValue()+"}  ";
    		    tOut+="("+dest.getName()+")";
    		    pw.write(tOut);
    		}
    	}
    	pw.write(";\n");
    	pw.write("\\end{tikzpicture}\n");
        pw.write("\\end{document}");
    	pw.close();
    	
    	}
    	
    	catch (FileNotFoundException e) {
    		System.out.println("\nUnable to write to file:" + file);
			e.printStackTrace();
		}
    	
	}
	
    /* Runs Latex compiler to generate a PDF representation */
	public void exportAsPDF(String file) {
		exportAsLatex(file);
		try {
			Process p = Runtime.getRuntime().exec(LATEX2PDF + " " + file + ".tex");
			String s = null;
			BufferedReader stdError = new BufferedReader(new  InputStreamReader(p.getErrorStream()));
			while ((s = stdError.readLine())!=null) {
                System.out.println(s);
            }
			// wait for 10 seconds and then destroy the process
	         Thread.sleep(10000);
	         p.destroy();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
