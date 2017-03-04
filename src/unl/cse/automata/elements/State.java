package unl.cse.automata.elements;

/* An individual state of automata with a 
 * name and properties attached to it
 */

public class State {

	public boolean start = false; /* Is it a start state */
	public boolean accept = false;  /* Is it a accept state */
	
	String name = null;
	
	public State() {
		
	}
	
	public State(String name) {
		this.name = name.trim();
	}
	
	public State(String name, boolean start) {
		this.name = name.trim();
		this.start = start;
	}
	
	public State(String name, boolean start, boolean accept) {
		this.name = name.trim();
		this.start = start;
		this.accept = accept;
	}
	public boolean isInitial() {
		return this.start;
	}
	
	public boolean isFinal() {
		return this.accept;
	}
	
	public boolean isNormal() {
		return !this.start && !this.accept;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.trim();
	}
	
	public String toString() {
		return this.name + "[s="+(start?1:0) + ",a="+ (accept?1:0) + "]";
	}

	public boolean isStart() {
		return start;
	}

	public void setStart(boolean start) {
		this.start = start;
	}

	public boolean isAccept() {
		return accept;
	}

	public void setAccept(boolean accept) {
		this.accept = accept;
	}
	
	public State clone() {
		State s = new State(new String(this.name));
		s.setAccept(this.accept);
		s.setStart(this.start);
		return s;
	}
	
	
/*	public boolean equals(State s2) {
		if(this.name.equals(s2.name) && this.start==s2.start &&
				this.accept ==s2.accept)
			return true;
		return false;
	}*/

}
