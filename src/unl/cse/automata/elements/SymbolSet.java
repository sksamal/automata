package unl.cse.automata.elements;

import java.util.HashSet;
import java.util.Set;

public class SymbolSet<T> {
	
    private Set<Symbol<T>> symbols = null;
	
	public Set<Symbol<T>> getSymbols() {
		return symbols;
	}

	public void setSymbols(HashSet<Symbol<T>> symbols) {
		this.symbols = symbols;
	}
	
	public SymbolSet() {
       this.symbols = new HashSet<Symbol<T>>();
	}
	
	public void add(Symbol<T> s) {
		if(find(s.getValue()) == null)
		  this.symbols.add(s);
	}
	
	public boolean isValid(Symbol<T> s) {
		if(this.symbols.contains(s))
			return true;
		else
			return false;
	}
	
	public int size() {
		return this.symbols.size();
	}
	
	public Symbol<T> find(T t) {
		for(Symbol<T> s : this.symbols) {
			if(t.equals(s.getValue())) {
				return s;
			}
		}
		return null;
	}
	
	public Symbol<T> find(Symbol<T> sy) {
		for(Symbol<T> s : this.symbols) {
			if(sy.getValue().equals(s.getValue())) {
				return s;
			}
		}
		return null;
	}
	
	public boolean equals(SymbolSet<T> ss) {
		return this.symbols.equals(ss.getSymbols());
	}
	
	public SymbolSet<T> clone() {
		SymbolSet<T> sSet = new SymbolSet<T>();
		for(Symbol<T> s : this.symbols)
			sSet.add(s);
		return sSet;
	}

	public void remove(Symbol<T> epsilon) {
		this.symbols.remove(find(epsilon.getValue()));	
	}
	
	public String toString() {
		String toString = "";
		for(Symbol<T> s : this.symbols)
			toString+=("" + s);
		return toString;
	}

}
