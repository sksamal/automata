package unl.cse.automata.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StateSet {
	
	private Set<State> states = null;
	
	public StateSet(HashSet<State> states) {
		this.states = states;
	}
	
	public StateSet() {
		this.states = new HashSet<State>();
	}

	public Set<State> getStates() {
		return states;
	}
	
	public void add(State s) {
	   if(findState(s.getName()) == null)
		this.states.add(s);
	}
	
	public void add(StateSet ss) {
		for(State st : ss.getStates())
			add(st);
	}

	public void setStates(HashSet<State> states) {
		this.states = states;
	}
	
	public boolean isValid(State s) {
		if(this.states.contains(s))
			return true;
		else
			return false;
	}
	
	public List<State> getStates(State st, String splitter) {
		String sNames[] = st.getName().split(splitter);
		List<State> states = new ArrayList<State>();
		for(String s : sNames) 
			if(findState(s) != null)
				states.add(findState(s));
		return states;
	}
	
	public int size() {
		return this.states.size();
	}

	public State getInitial() {
		for(State s : states) {
			if(s.isInitial()) return s;
		}
		return null;
	}
	
	public StateSet getFinal() {
		StateSet ss = new StateSet();
		for(State s : this.states) {
			if(s.isFinal()) ss.add(s);
		}
		return ss;
	}
	
	public boolean contains(String s) {
		if(findState(s)==null)
			return false;
		else
			return true;
	}
	
	public boolean contains(State s) {
		return this.states.contains(s);
	}
	
	public State findState(String name) {
		for(State st : this.states)
			if(name.trim().equals(st.getName()))
				return st;
	    return null;
	}
	
	public String toString() {
		String out = " StateSet: [ ";
		for(State st : this.states)
				out +=st + "\n ";
	    return out + "]";
	}
	
	public void setAccept(boolean b) {
		for (State s : this.states)
			s.setAccept(b);
	}
	
	public StateSet clone() {
		StateSet ss = new StateSet();
		for(State st : this.states)
			ss.add(st.clone());
		return ss;
	}
}
