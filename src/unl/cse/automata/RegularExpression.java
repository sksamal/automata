package unl.cse.automata;
/* This class is to represent a regular expresion using a  
 * symbolsset and operationSet
 */
import unl.cse.automata.elements.State;
import unl.cse.automata.elements.StateSet;
import unl.cse.automata.elements.Symbol;
import unl.cse.automata.elements.SymbolSet;
import unl.cse.automata.elements.Transition;
import unl.cse.automata.elements.TransitionTable;

public class RegularExpression {

	SymbolSet<String> symbolSet = null;
	SymbolSet<String> operSet = null;
	
	public final String LEFTBRACE = "(";
	public final String RIGHTBRACE = ")";
	public final String KLEENESTAR = "*";
	public final String UNION = "|";
	int p =0;
	
	public RegularExpression(SymbolSet<String> symbolSet) {
		this.symbolSet = symbolSet;
		this.operSet = new SymbolSet<String>();
		this.operSet.add(new Symbol<String>(LEFTBRACE));
		this.operSet.add(new Symbol<String>(RIGHTBRACE));
		this.operSet.add(new Symbol<String>(KLEENESTAR));
		this.operSet.add(new Symbol<String>(UNION));
	}
	
	public SymbolSet<String> getSymbolSet() {
		return symbolSet;
	}
	
	public void setSymbolSet(SymbolSet<String> symbolSet) {
		this.symbolSet = symbolSet;
	}
	
	public SymbolSet<String> getOpers() {
		return this.operSet;
	}
	
	public boolean isOperator(String s) {
		return (this.operSet.find(s)!=null);
	}
	
	public boolean isSymbol(String s) {
		return (this.symbolSet.find(s)!=null);
	}
	
	/* Convert regular expression to NFA recursively */
	public NonDeterFiniteAutomata parsetoNFA(String input) {
					
		/* Start state, states, symbols, Final Set, Transition */
		State sState = new State("s"+p);
		sState.setStart(true);
		StateSet states = new StateSet();
		states.add(sState);
		State fState = new State("f"+(p++));
		fState.setAccept(true);
		StateSet fStateSet = new StateSet();
		fStateSet.add(fState);
		states.add(fState);
		TransitionTable<String> tTable = new TransitionTable<String>();
		Symbol<String> epsilon = new Symbol<String>("e");
		this.symbolSet.add(epsilon);
		
		/* Null input */
		if(input==null || input.equals("") || input.length() ==0)
		{
		  tTable.add(new Transition<String>(states,sState,epsilon,fStateSet));
		  //return new NonDeterFiniteAutomata(states,symbolSet,tTable,"NFA",epsilon);
		  return null;
		}
		
		/* Single length input */
		if(input.length()==1)
		{
			if(!isSymbol(input))
				System.out.println("Invalid input :" + input);
			tTable.add(new Transition<String>(states,sState,new Symbol<String>(input),fStateSet));
			return new NonDeterFiniteAutomata(states,symbolSet,tTable,"NFA",epsilon);
		}
		
		/* Other cases */
		NonDeterFiniteAutomata nfa = null;

		/* Go by precedence rule - Bracket, kleenstar and then union */
		/* BRACKET */
		int lbraceIndex = input.indexOf(LEFTBRACE);
		int rbraceIndex = findClosing(input, lbraceIndex);

		if ((lbraceIndex == -1 && rbraceIndex != -1)
				|| (lbraceIndex != -1 && rbraceIndex == -1)) {
			System.out.println("ERROR: Paranthesis not matched properly");
			return null;
		}

		if ((lbraceIndex != -1) && (rbraceIndex != -1)) {
			NonDeterFiniteAutomata leftNfa = parsetoNFA(input.substring(0,
					lbraceIndex));
			NonDeterFiniteAutomata innerNfa = parsetoNFA(input.substring(
					lbraceIndex + 1, rbraceIndex));
	//		System.out.println("input:" + input + "leftindex=" + lbraceIndex + "left: " + input.substring(0,lbraceIndex)
		//			 + "inner: " + input.substring(lbraceIndex + 1, rbraceIndex)
		//			 + "right:" + input.substring(rbraceIndex + 1, input.length()));
			NonDeterFiniteAutomata rightNfa = null;
			int i = 1;
			String next = null;
			while (rbraceIndex+i < input.length()) {
					next = input.substring(rbraceIndex + i, rbraceIndex + i+1);
					if (next.equals(KLEENESTAR))
						innerNfa = innerNfa.kleeneStar();
					if (next.equals(UNION)) {
						rightNfa = parsetoNFA(input.substring(rbraceIndex + i
								+ 1, input.length()));
						innerNfa = innerNfa.union(rightNfa);
						break;
					}
					if(!next.equals(KLEENESTAR) && !next.equals(UNION))
						break;
					i++;
					next=null;
				}

				if (next!= null && !next.equals(UNION)) {
					rightNfa = parsetoNFA(input.substring(rbraceIndex + i,
							input.length()));
					innerNfa = innerNfa.concatanate(rightNfa);
				}
				if(leftNfa != null)
					return (leftNfa.concatanate(innerNfa));
				else
					return innerNfa;
		}
		
		
		/* UNION */
		int unionIndex = input.indexOf(UNION);
		if(unionIndex ==0) {
			System.out.println("Invalid use of Union:");
			return null;
		}
		
		if(unionIndex > 0) {
		NonDeterFiniteAutomata leftNfa = parsetoNFA(input.substring(0,unionIndex));
	//	System.out.println("SS" + input.substring(0,unionIndex) + " " + input.substring(unionIndex + 1,
	//			input.length()));
		NonDeterFiniteAutomata rightNfa = parsetoNFA(input.substring(unionIndex + 1,
				input.length()));
		return leftNfa.union(rightNfa);
		}
		
		
		/* KLEENESTAR */
		int kleeneIndex = input.lastIndexOf(KLEENESTAR);
		if(kleeneIndex==0) {
			System.out.println("Wrong use of operator *");
		    return null;
	     }
		if(kleeneIndex > 0) {
			
			nfa = parsetoNFA(input.substring(kleeneIndex-1,kleeneIndex));
			nfa = nfa.kleeneStar();
			
			NonDeterFiniteAutomata leftNfa = null;
			
			String before ="";
			if(kleeneIndex-2>=0)
				before = input.substring(kleeneIndex-2,kleeneIndex-1);
			
			if(before.equals(UNION)) {			
				leftNfa = parsetoNFA(input.substring(0,kleeneIndex-2));
				if(leftNfa!=null)
					return leftNfa.union(nfa.concatanate(parsetoNFA(input.substring(kleeneIndex+1,input.length()))));
				else
					return nfa.concatanate(parsetoNFA(input.substring(kleeneIndex+1,input.length())));
			}
			
	//		System.out.println(nfa.getTransitions());
			leftNfa = parsetoNFA(input.substring(0,kleeneIndex-1));
			if(leftNfa!=null)
				nfa = leftNfa.concatanate(nfa.concatanate(parsetoNFA(input.substring(kleeneIndex+1,input.length()))));
			else
				nfa = nfa.concatanate(parsetoNFA(input.substring(kleeneIndex+1,input.length())));
			return nfa;
		}
		
		
		/* Nothing match , just break it at first position as one string and return */
		return parsetoNFA(input.substring(0,1)).concatanate(parsetoNFA(input.substring(1,input.length())));
		
		
	}

	/* Find closing index of leftbrace */
	public int findClosing(String input, int openPos) {
	    int closePos = openPos;
	    int counter = 1;
	    while (counter > 0) {
	    	if(closePos ==input.length()-1 || closePos == -1)
	    		return -1;
	        String s = input.substring(++closePos,closePos+1);
	        if (s.equals(LEFTBRACE)) {
	            counter++;
	        }
	        else if (s.equals(RIGHTBRACE)) {
	            counter--;
	        }
	    }
	    return closePos;
	}
}
