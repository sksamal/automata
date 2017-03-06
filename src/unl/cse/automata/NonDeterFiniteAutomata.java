package unl.cse.automata;
/* This class is a subclass of FiniteAutomata and 
 * represents Non-deterministic Finite Automata*/

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import unl.cse.automata.elements.State;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

public class NonDeterFiniteAutomata extends FiniteAutomata {

	private Symbol<String> epsilon = null;
	private final String STATE_COMBINER = "-";
	private int iter =0;

	public NonDeterFiniteAutomata() {
		super();
	}

	public NonDeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions, String name) {
		super(states, symbols, transitions, name);
		this.epsilon = new Symbol<String>("e");
		this.symbols.add(this.epsilon);
	}

	public NonDeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions, String name,
			Symbol<String> epsilon) {
		super(states, symbols, transitions, name);
		this.epsilon = epsilon;
		this.symbols.add(this.epsilon);
	}

	public NonDeterFiniteAutomata(StateSet states, SymbolSet<String> symbols,
			TransitionTable<String> transitions) {
		super(states, symbols, transitions);
	}

	public void addTransition(Transition<String> s) {
		this.transitions.add(s);
	}

	/* Process the input string and return whether true or false */
	@Override
	public boolean process(List<Symbol<String>> input) {

		/* Get the start state */
		List<State> nextStates = new ArrayList<State>();
		nextStates.add(states.getInitial());
		
		/* If input is null, means a null string */
		if(input.size()==0) 
			input.add(this.epsilon);
		
		/* Validate inputs */
		if(!validate(input)) {
			System.out.println("Invalid symbols in the input!. Cannot proceed");
			return false;
		}
		
		/* Process Transitions */
		int i = 0;
		for (Symbol<String> s : input) {
			List<State> tempStates = new ArrayList<State>();
			i++;
			for (State st : nextStates) {
	//			System.out.println(i + ": Current State:" + st
		//				+ " Next symbol:" + s.getValue());

				/* If current symbol is epsilon, add the current state as well */
				if(s.getValue().equals(this.epsilon.getValue())) 
					tempStates.add(st);
				
				/* NFA may not have certain transitions */
				if (transitions.get(st, s) != null)
					tempStates.addAll(transitions.get(st, s).getStates());
				
			}
			nextStates = tempStates;
		}

		/* After the processing of input check if we reached a final state */
		for (State st : nextStates)
			if (st.isFinal())
				return true;
		return false;
	}

	public Set<State> processandFinal(List<Symbol<String>> input) {

	/* Get the start state */
	List<State> nextStates = new ArrayList<State>();
	nextStates.add(states.getInitial());
	Set<State> finalStates = new HashSet<State>();
	
	/* If input is null, means a null string */
	if(input.size()==0) 
		input.add(this.epsilon);
	
	/* Validate inputs */
	if(!validate(input)) {
		System.out.println("Invalid symbols in the input!. Cannot proceed");
	//	return false;
				return finalStates;
	}
	
	/* Process Transitions */
	int i = 0;
	for (Symbol<String> s : input) {
		List<State> tempStates = new ArrayList<State>();
		i++;
		for (State st : nextStates) {
//			System.out.println(i + ": Current State:" + st
	//				+ " Next symbol:" + s.getValue());

			/* If current symbol is epsilon, add the current state as well */
			if(s.getValue().equals(this.epsilon.getValue())) 
				tempStates.add(st);
			
			/* NFA may not have certain transitions */
			if (transitions.get(st, s) != null)
				tempStates.addAll(transitions.get(st, s).getStates());
			
		}
//		System.out.println("State: " + nextStates + " symbol: " + s + " Next: " + tempStates);
		nextStates = tempStates;
	}

	/* After the processing of input check if we reached a final state */
	for (State st : nextStates)
		if (st.isFinal()) finalStates.add(st);
	
	if(finalStates.size()==0) finalStates.addAll(nextStates);
	
	return finalStates;
}

	
	/* This method removes epsilon transition recursively */
	public void removeEpsilon() {

		Transition<String> et = null;
		/* Loop through each transition and find an epsilon transition */
		for (Transition<String> t : this.getTransitions().getAll()) {
			if (t.getSymbol().getValue().equals(this.epsilon.getValue())) {
				et = t;
				break;
			}
		}

		/* Return if no more null transition */
		if (et == null)
			return;

		/*
		 * Make the from State as final if the destination contains a final
		 * state
		 */
		for (State s : et.getDests().getStates())
			if (s.isFinal())
				et.getSrc().setAccept(true);

		/*
		 * Find the from States to source of the epsilon transitions and
		 * add/modify them
		 */
		for (Transition<String> t : this.getTransitions().getAll()) {
			if (t.getDests().contains(et.getSrc().getName())) {
				t.getDests().add(et.getDests());
			}
		}

		/* Find the toStates from epsilon transitions and add/modify them */
		List<Transition<String>> tList = new ArrayList<Transition<String>>();
		for (Transition<String> t : this.getTransitions().getAll()) {
			for (State st : et.getDests().getStates()) {
				if (st.getName().equals(t.getSrc().getName())) {
					tList.add(new Transition<String>(this.states, et.getSrc(),
							t.getSymbol(), t.getDests()));
				}
			}
		}
		
		/* Remove the epsilon transition and add the new ones*/
		this.transitions.remove(et);

		for (Transition<String> t : tList) 
			this.transitions.add(tList);

		/* Call this method recursively */
		removeEpsilon();
	}

	/* Does and union with nfa1 and returns back a new NFA */
	public NonDeterFiniteAutomata union(NonDeterFiniteAutomata nfa1) {
		if( nfa1 == null)
			return this;
		if (!this.symbols.equals(nfa1.symbols)) {
			System.out.println("UNION: not possible, different symbol sets");
			return null;
		}

		/* Create new merged states, transitions */
		StateSet newStates = this.getStates().clone();
		State start1 = newStates.getInitial();
		StateSet endstates1 = newStates.getFinal();
		StateSet nStates2 = nfa1.getStates().clone();
		State start2 = nStates2.getInitial();
		StateSet endstates2 = nStates2.getFinal();
		newStates.add(nStates2);

		TransitionTable<String> newTrans = new TransitionTable<String>();
		for (Transition<String> t : this.getTransitions().getAll()) {
			newTrans.add(t.clone(newStates));
		}
		for (Transition<String> t : nfa1.getTransitions().getAll()) {
			newTrans.add(t.clone(newStates));
		}

		/* Add epsilon transitions for start state */
		State newStartState = new State(start1.getName() + start2.getName(),
				true);
		newStates.add(newStartState);
		start1.setStart(false);
		start2.setStart(false);
		StateSet dest = new StateSet();
		dest.add(start1);
		dest.add(start2);
		newTrans.add(new Transition<String>(newStates, newStartState,
				this.epsilon, dest));

		/* Add epsilon transitions for final state */
		StateSet newFinalStates = new StateSet();
		newFinalStates.add(new State(endstates1.getStates().toArray(
				new State[0])[0].getName()
				+ endstates2.getStates().toArray(new State[0])[0].getName(),
				false, true));
		newStates.add(newFinalStates);

		for (State s1 : endstates1.getStates()) {
			s1.setAccept(false);
			newTrans.add(new Transition<String>(newStates, s1, this.epsilon,
					newFinalStates));
		}

		for (State s2 : endstates2.getStates()) {
			s2.setAccept(false);
			newTrans.add(new Transition<String>(newStates, s2, this.epsilon,
					newFinalStates));
		}

		/* Create the new NFA and return it */
		return new NonDeterFiniteAutomata(newStates, this.symbols, newTrans,
				this.name + " U " + nfa1.name);
	}
	
	
	/* Does and concatanation with nfa1 and returns back a new NFA */
	public NonDeterFiniteAutomata concatanate(NonDeterFiniteAutomata nfa1) {
	
		if(nfa1 == null)
			return this;
		/* Create new merged states, transitions */
		StateSet newStates = this.getStates().clone();
		StateSet endstates1 = newStates.getFinal();
		StateSet nStates2 = nfa1.getStates().clone();
		State start2 = nStates2.getInitial();
		newStates.add(nStates2);

		TransitionTable<String> newTrans = new TransitionTable<String>();
		for (Transition<String> t : this.getTransitions().getAll()) {
			newTrans.add(t.clone(newStates));
		}
		for (Transition<String> t : nfa1.getTransitions().getAll()) {
			newTrans.add(t.clone(newStates));
		}

		/* Fix start/end states and add epsilon transition*/
		endstates1.setAccept(false);
		start2.setStart(false);
		StateSet dest = new StateSet();
		dest.add(start2);
		for(State s :endstates1.getStates())
			newTrans.add(new Transition<String>(newStates, s,
				this.epsilon, dest));
		
		/* Create the new NFA and return it */
		return new NonDeterFiniteAutomata(newStates, this.symbols, newTrans,
				this.name + " o " + nfa1.name);
	}

	/* Performs kleenestar on the nfa and creates a new NFA */
	public NonDeterFiniteAutomata kleeneStar() {

		/* Create new merged states, transitions */
		StateSet newStates = this.getStates().clone();
		State start = newStates.getInitial();
		StateSet endStates = newStates.getFinal();

		TransitionTable<String> newTrans = new TransitionTable<String>();
		for (Transition<String> t : this.getTransitions().getAll()) {
			newTrans.add(t.clone(newStates));
		}

		/* Add epsilon transition from end states to the start state */
		StateSet startSet = new StateSet();
		startSet.add(start);
		for (State s : endStates.getStates())
			newTrans.add(new Transition<String>(newStates, s, this.epsilon,
					startSet));

		/* Add new Start and final state and epsilon transitions */
		State newStartState = new State("S" + start.getName(), true);
		newStates.add(newStartState);
		start.setStart(false);
		State newFinalState = new State("F"
				+ endStates.getStates().toArray(new State[0])[0].getName(),
				false, true);
		StateSet newFinalStates = new StateSet();
		newFinalStates.add(newFinalState);
		newStates.add(newFinalState);

		/*
		 * New transitions - from newstart to newfinal , newstart to start, end
		 * to new end
		 */
		newTrans.add(new Transition<String>(newStates, newStartState,
				this.epsilon, newFinalStates));
		newTrans.add(new Transition<String>(newStates, newStartState,
				this.epsilon, startSet));
		for (State s : endStates.getStates()) {
			newTrans.add(new Transition<String>(newStates, s, this.epsilon,
					newFinalStates));
			s.setAccept(false);
		}

		/* Create the new NFA and return it */
		return new NonDeterFiniteAutomata(newStates, this.symbols, newTrans,
				this.name + "-*");
	}

	/* Transforms to DFA using the normal algorithm 
	 * 1. Remove epsilon
	 * 2. Create a reject states for invalid transitions
	 * 3. Creates new states and new transition*/
	public DeterFiniteAutomata convertToDFA() {

		/* Create new states, & symbols */
		StateSet oldStates = this.states.clone();
		StateSet newStates = new StateSet();
		this.removeEpsilon();
		SymbolSet<String> newSymbols = this.symbols.clone();
		newSymbols.remove(this.epsilon);

		/* New Transition Table */
		TransitionTable<String> newTrans = new TransitionTable<String>();
		for (Transition<String> t : this.getTransitions().getAll()) {
			newTrans.add(t.clone(oldStates));
		}

		/* StateSet Queue */
		List<State> ssList = new ArrayList<State>();
		ssList.add(oldStates.getInitial());
		
		
		/* New Reject state */
		State rejectState = new State("r");
		StateSet rejectStateSet = new StateSet();
		rejectStateSet.add(rejectState);

		while (ssList.size() > 0) {
		
			
			State st = ssList.remove(0);
			newStates.add(st);				
				

			/* For each symbol get the transitions and process */
			for (Symbol<String> sy : newSymbols.getSymbols()) {

				Transition<String> t = newTrans.getTrans(st, sy);

				/* If no transition - add a transition to reject state */
				if (t == null) {

					/* Add reject state to the new states and the queue if not already present */
					if (!oldStates.contains(rejectState)) {
						oldStates.add(rejectState);
						newStates.add(rejectState);
						ssList.add(rejectState);
					}

					/* Add a transition to reject state */
					newTrans.add(new Transition<String>(st, sy, rejectStateSet));						

				}

				else {

					/* Merge states */
					State newToState = this.mergeStates(oldStates,t.getDests());
					if (!oldStates.contains(newToState)) {
						oldStates.add(newToState);

						/* Merge transitions corresponding to merged states */
						for (Symbol<String> symbol : newSymbols.getSymbols()) {
							StateSet destSet = new StateSet();
							for (State state : t.getDests().getStates())
								if (newTrans.get(state, symbol) != null)
									destSet.add(newTrans.get(state, symbol));
							if (destSet.size() != 0)
								newTrans.add(new Transition<String>(newToState,
										symbol, destSet));
						}
					}

						/* Remove the original transition */
						newTrans.remove(t);

						/* Create new Destination, transition to merged state */
						StateSet destSet = new StateSet();
						destSet.add(newToState);
						newTrans.add(new Transition<String>(st, sy, destSet));

						/*
						 * Add the new Destination to the List for later
						 * processing
						 */
						
					if(!newStates.contains(newToState) && !ssList.contains(newToState))
							ssList.add(newToState);

					/* Add the dests if not already processed */
					for(State s : t.getDests().getStates())
						if(!newStates.contains(s) && !ssList.contains(s))
							ssList.add(s);
				}
			}

		}

		return new DeterFiniteAutomata(newStates, newSymbols, newTrans, this.epsilon);
	}

	/* Merges a set of states and returns back a single state.
	 * If a merged state is already present, just finds and returns an instance of it.
	 * Does a set-union by sorting the states to handle duplicates */
	public State mergeStates(StateSet oldStates,StateSet states) {
		if (states.size() == 1)
			return states.getStates().toArray(new State[0])[0];

		String newName = "";
		boolean newStart = false;
		boolean newAccept = false;
	
		/* Use a set and sort to remove duplicates while merging */
		Set<String> nameSet = new HashSet<String>();
		
		for (State s : states.getStates()) {
			nameSet.addAll(Arrays.asList(s.getName().split(STATE_COMBINER)));
			newStart = newStart | s.isStart();
			newAccept = newAccept | s.isFinal();
		}
	
		/* Sort it so that its easier to merge and remove duplicates */
		List<String> sortedNames = new ArrayList<String>(nameSet);
		Collections.sort(sortedNames);
		
		for(String name : sortedNames)
			newName+=name + STATE_COMBINER;
		newName = newName.substring(0,
				newName.length() - STATE_COMBINER.length());
		if(oldStates.findState(newName) == null)
			return new State(newName, newStart, newAccept);
		else 
			return oldStates.findState(newName);
	}
}
