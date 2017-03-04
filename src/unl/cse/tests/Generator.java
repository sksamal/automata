/* This class is a utility/helper class used to convert the input string
 * to Symbols accepted by DFA. I have assumed that Symbols are strings
 */
package unl.cse.tests;

import java.util.ArrayList;
import java.util.List;

import unl.cse.automata.elements.State;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

public class Generator {

    public static SymbolSet<String> getSymbolSet(String s) {
    	return getSymbolSet(s,",");
		
	}
	
	public static SymbolSet<String> getSymbolSet(String s, String splitter) {
		String[] tokens = s.trim().split(splitter);
		if (tokens == null || tokens.length ==0)
			return null;
		SymbolSet<String> ss = new SymbolSet<String>();
		
		for (String token : tokens) {
			ss.add(new Symbol<String>(token.trim()));
		}
		return ss;
	}
	
	public static List<Symbol<String>> getSymbols(String s) {
		return getSymbols(s,"");
	}
		
	public static List<Symbol<String>> getSymbols(String s, String splitter) {
		String[] tokens = s.trim().split(splitter);
		if (tokens == null || tokens.length ==0)
			return null;
		List<Symbol<String>> sList = new ArrayList<Symbol<String>>();
		
		for (String token : tokens)
			if(!token.equals(""))
			  sList.add(new Symbol<String>(token.trim()));
		return sList;
	}
	
	public static StateSet getStateSet(String s) {
    	return getStateSet(s,",",":");
	}
	
	public static StateSet getStateSet(String s, String splitter1) {
    	return getStateSet(s,splitter1,":");
	}
	
	
	public static StateSet getStateSet(String s, String splitter1, String splitter2) {
		String[] tokens = s.trim().split(splitter1);
		if (tokens == null || tokens.length ==0)
			return null;
		StateSet ss = new StateSet();
		
		for (String token : tokens) {
			String[] tok = token.trim().split(splitter2);
			if(tok.length == 3)
				ss.add(new State(tok[0].trim(),Integer.parseInt(tok[1])!=0,Integer.parseInt(tok[2])!=0));	
			else if (tok.length == 2)
				ss.add(new State(tok[0].trim(),Integer.parseInt(tok[1])!=0));
			else				
				ss.add(new State(tok[0]));
		}
			
		return ss;
	}
	
	public static TransitionTable<String> getTransitionTable(StateSet ss, String s, String splitter1, String splitter2,String splitter3) {
		
		String[] tokens = s.trim().split(splitter1);
		if (tokens == null || tokens.length ==0)
			return null;
		TransitionTable<String> tt = new TransitionTable<String>();
		
		for (String token : tokens) {
			String[] tok = token.trim().split(splitter2);
			
			if(tok.length != 3) continue; /*Invalid transition*/		
			/* Create a transition from the string */
			else 
				tt.add(new Transition<String>(ss,new State(tok[0].trim()),new Symbol<String>(tok[1].trim()),getStateSet(tok[2].trim(),",")));
			}
		
		/*for(Transition<String> t : tt.getAll())
			for(State d : t.getDests().getStates())
			  System.out.println(t.getSrc().getName() + "," + t.getSymbol().getValue() + ","+ d.getName()); 
		*/	
		return tt;
	}
}
