package unl.cse.automata.elements;

public class Transition<T> {

	State src = null;
	StateSet dests = null;
	Symbol<T> symbol = null;
	
	public Transition(StateSet ss, State src,Symbol<T> symbol, StateSet dests) {
		this.src = find(ss,src);
		this.symbol = symbol;
		this.dests = find(ss,dests);
	}
	
	public Transition(State src,Symbol<T> symbol, StateSet dests) {
		this.src = src;
		this.symbol = symbol;
		this.dests = dests;
	}

	public State getSrc() {
		return src;
	}

	public void setSrc(StateSet ss, State src) {
		this.src = find(ss,src);
	}

	public StateSet getDests() {
		return dests;
	}

	public void setDests(StateSet ss, StateSet dests) {
		this.dests = find(ss,dests);
	}
	
	public void addDest(StateSet ss, State s) {
		this.dests.add(find(ss,s));
	}
	
	public void addDests(StateSet ss, StateSet s) {
		this.dests.add(find(ss,s));
	}
	
	public void merge(Transition<T> t) {
		if(this.src!=t.src && this.symbol.getValue()!=t.symbol.getValue())
			return;
		this.dests.add(t.getDests());
	}

	public Symbol<T> getSymbol() {
		return symbol;
	}

	public void setSymbol(Symbol<T> symbol) {
		this.symbol = symbol;
	}
	
	public State find(StateSet ss, State s)
	{
		/* Check if state is valid and return its instance*/
		if(ss.findState(s.getName())==null) {
			System.out.println("Invalid start state in transition: " + s.getName() + " Ignoring..");
			return null;
		}
//		System.out.println("Found state in transition: " + ss.findState(s.getName()).getName());
		return ss.findState(s.getName());
	}
	
	public StateSet find(StateSet ss, StateSet ss1)
	{
		StateSet ss2 = new StateSet();
		/* Check if state is valid */
		for(State s : ss1.getStates()) {
		  if(ss.findState(s.getName())==null) {
			System.out.println("Invalid start state in transition: " + s.getName() + " Ignoring..");
			return null;
		  }
//		  System.out.println("Found state in transition: " + ss.findState(s.getName()).getName());
		  ss2.add(ss.findState(s.getName()));
		}
		return ss2;
	}
	
	public Transition<T> clone(StateSet ss) {
		return new Transition<T>(ss,this.src,this.symbol,this.dests);
	}

}
