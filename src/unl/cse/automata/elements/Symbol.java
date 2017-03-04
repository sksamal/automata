package unl.cse.automata.elements;

public class Symbol<T> {

	private T value = null;
	
	public T getValue() {
		return value;
	}
	public void setValue(T value) {
		this.value = value;
	}
	
	public Symbol(T value) {
		this.value = value;
	}
	
	public Symbol() {
		
	}
	
	public String toString() {
		return value.toString();
	}	

	public Symbol<T> clone() {
		Symbol<T> s = new Symbol<T>(value);
		return s;
	}
	
	public boolean equals(Symbol<T> s) {
		return this.value.equals(s.getValue());
	}
}