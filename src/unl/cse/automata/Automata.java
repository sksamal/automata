package unl.cse.automata;

import java.util.List;

import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

/* This is an abstract generic interface that defines
 * a generic automata
 */
public interface Automata {

	public abstract StateSet getStates();

	public abstract void setStates(StateSet ss);

	public abstract SymbolSet<String> getSymbols();

	public abstract void setSymbols(SymbolSet<String> ss);

	public abstract TransitionTable<String> getTransitions();

	public abstract void setTransitions(TransitionTable<String> t);

	public abstract void addSymbol(Symbol<String> s);
	
	public abstract void addTransition(Transition<String> s);
	
	public abstract boolean process(List<Symbol<String>> input);
	

}
