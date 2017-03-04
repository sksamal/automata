package unl.cse.automata.elements;

import java.util.Comparator;

/* To order the states before merging */
public class StateComparator implements Comparator<State> {


	public StateComparator() {
		super();
	}

	@Override
	public int compare(State o1, State o2) {
		if(o1.isInitial() && !o2.isInitial())
			return -1;
		if(!o1.isInitial() && o2.isInitial())
			return 1;
		if(o2.isFinal() && !o1.isFinal())
			return -1;
		if(!o2.isFinal() && o1.isFinal())
			return 1;
		return 0;
	}

}
