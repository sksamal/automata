package unl.cse.automata;
/* This class is a subclass of FiniteAutomata and 
 * represents Deterministic Finite Automata*/
import java.util.List;
import unl.cse.automata.elements.State;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

public class DeterFiniteAutomata extends FiniteAutomata {
	
	/* Its unfortunate - this is required to just handle the null string */
	Symbol<String> epsilon = null;

	public DeterFiniteAutomata() {
		super();
	}

	public DeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions, String name, Symbol<String> epsilon) {
		super(states, symbols, transitions,name);
		this.epsilon = epsilon;
	}
	
	public DeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions, String name) {
		super(states, symbols, transitions,name);
		this.epsilon = new Symbol<String>("e");
	}
	
	public DeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions) {
		super(states, symbols, transitions);
	}
	
	public DeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions, Symbol<String> epsilon) {
		super(states, symbols, transitions);
		this.epsilon = new Symbol<String>("e");
	}

	public void addTransition(Transition<String> s) {
		/* Check for validity - only one output state  for every transition*/		
		this.transitions.add(s);
	}
	
		
	/* Process and return if the string is accepted or rejected */
	@Override
	public boolean process(List<Symbol<String>> input) {
			
		/* Get the start state */
		State nextState = states.getInitial();
		
		/* This is not good, but input symbol can be epsilon - a special case, just make input null */
		if(input.size()==1 && input.get(0).equals(this.epsilon))
			input.remove(0);
		
		if(!validate(input)) {
			System.out.println("Invalid symbols in the input!. Cannot proceed");
			return false;
		}
		/* Process Transitions */
		for(Symbol<String> s : input) {
//			System.out.println("Current State:" + nextState + " Next symbol:" + s.getValue());
		  	nextState = transitions.get(nextState,s).getStates().toArray(new State[0])[0]; 
		}
		
		/* After the processing of input check if we reached a final state */
		if(nextState.isFinal())
			return true;
		else
			return false;
	}
}
