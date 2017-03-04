package unl.cse.automata.elements;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TransitionTable<T> {
	
	private Map<State,Map<T,Transition<T>>> transitions = null;

	public TransitionTable() {
      this.transitions = new HashMap<State,Map<T,Transition<T>>>();
	}
	
	public TransitionTable(Map<State,Map<T,Transition<T>>> transitions) {
	 this.transitions = transitions;
	}
	
	public void add(Transition<T> t) {
		
		Map<T,Transition<T>> map = this.transitions.get(t.getSrc());
		
		if(map==null) {
		  map = new HashMap <T,Transition<T>>();
		}
		
		Transition<T> tt = map.get(t.getSymbol().getValue());
		if(tt!=null)
			t.merge(tt);
		map.put(t.getSymbol().getValue(),t);		
		this.transitions.put(t.getSrc(),map);
	}
	
	public void add(List<Transition<T>> tList) {	
		for(Transition<T> t : tList)
			this.add(t);
	}
	
	public void remove(Transition<T> t) {
		Map<T,Transition<T>> map = this.transitions.get(t.getSrc());
		Transition<T> tt = map.get(t.getSymbol().getValue());
		
		if(tt==null) {
			System.out.println("ERROR: Unable to remove the transition");
			return;
		}
		map.remove(t.getSymbol().getValue());
		
	}
	
	public void add(TransitionTable<T> tt) {
		for(Transition<T>  tran : tt.getAll())
			this.add(tran);
	}

	public StateSet get(State s, Symbol<T> sy) {
		Map<T,Transition<T>> map = this.transitions.get(s);
		if(map==null)
			return null;
	//	for(T val :map.keySet())
		//	System.out.println("trans " + val + ":" + map.get(val).getSrc() + map.get(val).getDests());
		if(map.get(sy.getValue())!=null)
		   return map.get(sy.getValue()).getDests();
		return null;
	}
	
	public Transition<T> getTrans(State s, Symbol<T> sy) {
		Map<T,Transition<T>> map = this.transitions.get(s);
		if(map==null)
			return null;
		if(map.get(sy.getValue())!=null) 
			return map.get(sy.getValue());
		return null;
	}
	
	public String toString() {
		String out = "Transition Table:\n";
		for(Transition<T> t: getAll()) {    		
    		for(State dest : t.getDests().getStates()) {
    			out+="  " + t.getSrc().toString() + " " + t.getSymbol().toString() + " " + dest + "\n";
    		}
		}
    	return out;
    	
	}
	
	public List<Transition<T>> getAll() {
		
		List<Transition<T>> tList= new ArrayList<Transition<T>>();
		
		for(State s : this.transitions.keySet()) {
			//System.out.println(s.getName());
			Map<T,Transition<T>> map = this.transitions.get(s);
			for(T v : map.keySet()) {
				tList.add(map.get(v));
			}
		}
		return tList;
	}
	
	public int size() {
		return this.transitions.size();
	}

}
