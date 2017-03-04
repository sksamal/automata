=====================================================================
                                                                       
                              ======                                   
                              README                                   
                              ======                                   
                                                                       
                             SAIJ 1.0.0
                          25 October 2014
                                                                       
                 Suraj's Automata Implementation in JAVA 

         	     Copyright (C) 2014  @Suraj ssamal@cse.unl.edu
		    University of Nebraska-Lincoln
              web: http://cse.unl.edu/~ssamal/automata/saij.html            
                                                                       
=====================================================================

Contents:
---------

1. Version Information

2. Architecture Overview

3. Directory Structure

4. Logging

5. Dependencies

6. Implementation Specifics

7. Building the package

8. Testing the package

9. Running the package

10. Licencing Information

----------------------------------------------------------------------
1. VERSION
----------------------------------------------------------------------
   This is the initial version of the SAIJ software(v1.0.0). Following
features are provided:
   a) Ability to read a regular expression and convert it to a NFA using 
Thompson's Construction algorithm (http://en.wikipedia.org/wiki/Thompson's_construction_algorithm).
   b) Convert a NFA to DFA after removing epsilon transitions.
   c) Process a set of given strings to determine if they are in the language represented by 
the regular expression.
   d) A test program to check the validity of the constructed automata.
   e) Limited capability to export to pdf using latex and Tikz package (BETA).

----------------------------------------------------------------------
2. ARCHITECTURE
----------------------------------------------------------------------
   SAIJ has been designed using a scalable architecture with an 
intention of expanding its capabilities to various types of Automaton (PDA,
Turing Machine etc) in later versions. It is developed in Java and uses an 
object-oriented paradigm and its techniques, namely abstraction, 
encapsulation, inheritanceand polymorphism. Based on the domain, various 
objects and their relevant behaviours and relationships are identified and 
modelled as classes. A brief description of various important classes are 
as follows:

	a) State: This class corresponds to a state in automaton. It has a name,
and an indication of whether it is a normal state, start state or a final 
state.
	b) StateSet: A collection of state objects implemented as a java
set. This is used as a component of a Automaton and also of a transition (explained 
later).
	c) Symbol: An element referring to a symbol in automaton. This is 
implemented as a generic, but its usage is currently restricted to only 
string values.

	d) SymbolSet: A collection of set of Symbols implemented as a java set.
This is used to represent a set of symbols for an Automaton.

	e) Transition: This is an object consisting of a tuple of source state, 
symbol and a destination StateSet. For DFA, the StateSet would contain only
a single state.

	f) TransitionTable: This is the transition table which can be 
attached to an automaton. It is implemented as a HashMap in java and consists
of collection of transitions.

	g) Automata: A generic interface representing automata. It is
composed of a SymbolSet, a StateSet and a TransitionTable. It also enforces
a method process() that processes a input string and returns true or false 
depending on whether the string is accepted by the automata.

	h) FiniteAutomata: Direct subclass of Automata. Exposes certain 
methods to export and dump the automata into various formats (.tex,.pdf), 
still in BETA.

	i) NonDeterFiniteAutomata: Represents a NFA, direct subclass of 
FiniteAutomata. It exposes the following important methods:

	     1) process(): This method processes the set of input symbols and
returns true if the input is processed successfuly by the NFA. It splits
into multiple copies and returns true if one if the copies accepts the
input. 
	     2) removeEpsilon(): This method removes epsilon transitions from the
current NFA recursively by using a structured algorithm. 
	     3) union(): This method does a union operation with another input 
NFA using the standard algorithm, creates a new instance and returns the new NFA.
	     4) concatanate(): This method does a concatanate operation with 
another NFA using the standard algorithm, creates a new instance of NFA and returns
the new NFA.
	     5) kleeneStar(): This method performs a kleeneStar operation using
standard algorithm and returns a new instance of NFA.
	     6) convertToDFA(): This method uses an algorithm to convert the NFA to
an DFA and returns a new instance of DFA. New states are created by concatanating 
the values of their original NFA states and sorting them in order so as to 
prevent duplicates.

	j) DeterFiniteAutomata: This class represents a DFA, a direct subclass of
FiniteAutomata. It exposes the following important methods:

	     1) process(): This method processes the set of input symbols and
returns true if the input string is accepted by the DFA.

	k) RegularExpression: This class represents a regular expression. It 
comprises of two SymbolSets, one for the symbols and another for the operations,
kleenestar(*), union(|), bracket()(), and concatanation(). Concatanation is assumed
to be hidden with no operand. It exposes the following important methods:

	      1) parsetoNFA(): Reads the regular expression, parses it
recursively, generates a NFA and returns it. It uses a standard algorithm,
Thompson's construction algorithm and operator priorities to accomplish this task.

	l) RegxRunner: This class acts as the main driver of the software. It reads 
the regular expression and a set of strings from standard input and outputs if the 
strings belong to the language represented by the regular expression.

	m) RegxTester: This is a tester class that runs few test-cases on the
software package using certain hard-coded values.

	n) Generator: This class is a utility/helper class to convert the input 
strings to symbols and perform other simple tasks.

Other Important Files:

	a) README: This file which provides general instruction of usage of this 
software.
	
	b) build.xml: This is the ant configuration file for various tasks: compiling,
packaging, testing and exporting(in BETA) to .tex and .pdf formats.

	c) proj1.sh: This is the main driver script for the whole package. It reads from
the standard input and feeds them as arguments to the java jar file. This could have been
avoided but I decided to not decommision it.

For a further detailed description of each element/class/files, the reader can refer to
"automata-design.png" and "automata-elements.png" present under this folder. Also, 
it is highly encouraged to see the comments present in the source-code or email the 
author (ssamal@cse.unl.edu) directly.   

----------------------------------------------------------------------
3. DIRECTORY STRUCTURE
----------------------------------------------------------------------
Following is the directory structure of this software package:

├── automata.jar -> ./automata-20141025.jar   (The packaged java archive)
├── automata-design.png			      (Class diagram- Automata)
├── automata-elements.png		      (Class diagram - elements)
├── build.xml				      (Ant Build File)
├── proj1.sh				      (The main driver shell script)
├── README				      (This file)
├── build				      (Directory for storing compile classes)
└── src					      (Java source directory)
    └── unl
        └── cse
            ├── automata
            │   ├── Automata.java
            │   ├── DeterFiniteAutomata.java
            │   ├── elements
            │   │   ├── StateComparator.java
            │   │   ├── State.java
            │   │   ├── StateSet.java
            │   │   ├── Symbol.java
            │   │   ├── SymbolSet.java
            │   │   ├── Transition.java
            │   │   └── TransitionTable.java
            │   ├── FiniteAutomata.java
            │   ├── Mealy.java
            │   ├── Moore.java
            │   ├── NonDeterFiniteAutomata.java
            │   ├── PushDownAutomata.java
            │   ├── RegularExpression.java
            │   └── TuringMachine.java
            └── tests
                ├── AutomataTester.java
                ├── Generator.java
                ├── RegxRunner.java
                └── RegxTester.java

----------------------------------------------------------------------
4. LOGGING
----------------------------------------------------------------------
Logging in this version of the software has been restricted strictly
to "regx.log" which gets overwritten everytime the software runs. 

The log file displays debug information to certain extent including
the symbol-set, transition tables of NFA and DFA, and various steps
during the conversion of REGX --> NFA --> DFA --> Processing strings.

This application uses a random-number in a range between 0-100 to name
the corresponding DFA/NFA generated during the process. An example 
name is "NFA-RE-88" which refers to a NFA for some regular expression.

----------------------------------------------------------------------
5. DEPENDENCIES
----------------------------------------------------------------------
This software package depends on the following standard libraries:
	java 1.6 or higher (J2SE for compilation and JRE for running)
	ant 1.7 or higher (For compliation)	


----------------------------------------------------------------------
6. IMPLEMENTATION SPECIFICS
----------------------------------------------------------------------
Please refer to the corresponding section in this document for 
details:
 
  REGULAR EXPRESSION : Section 2, k 
  CONVERSION TO NFA  : Section 2, k, 1
  CONVERSION TO DFA  : Section 2, i, 6
  PROCESSING BY NFA  : Section 2, i, 1
  PROCESSING BY DFA  : Section 2, j, 1

----------------------------------------------------------------------
7. BUILDING
----------------------------------------------------------------------
The software distribution can build using the following command:

 user@host> ant dist (Compiles, generates the jar file, creates a soft 
			link to automata.jar)

Alternatively, it can be done in two steps:
 user@host> ant compile (Compiles and generates the classes in build directory)
 user@host> ant dist (Packages the jar file and creates a soft-link to automata.jar)

The build can be cleaned using the following command:
 user@host> ant clean (Cleans the build directories, any log files, .tex and other 
			temporary files and the automata<>.jar file)

----------------------------------------------------------------------
8. TESTING
----------------------------------------------------------------------
A sanity testing of the package can be done using the following command:

 user@host> ant test (Runs a small suite of test-cases and displays output on
		      standard output, no logging done)

----------------------------------------------------------------------
9. RUNNING
----------------------------------------------------------------------
The software package can be run as follows:

user@host> ./proj1.sh < q1.txt  

where q1.txt is an input file in the following format:

user@host> cat q1.txt
(a|b)*a                    (Regular expression)
aaaa			    (String 1)
aba			    (String 2)
bba
a
b
bbb			    (String N)

Alternatively, all the contents can be typed on the standard input. A
double enter is required to tell the program that all the strings have
been entered. Here is an example:

user@host> ./proj1.sh < q1.txt  
(a|b)*a                    (Regular expression)
aaaa			    (String 1)
aba			    (String 2)
		(<--Enter)
		(<--Enter again to end)

The same can be accomplished directly by running the jar file as follows:

user@host> java -jar automata.jar "<regx>" <str1 str2....strN>

The output of the run is displayed on the standard output. It consists of
"yes" or "no" corresponding to each input string on a separate line in
the same order. Below is a sample display of output:

user@host> ./proj1.sh
aaa*b*a*a
aabaa
aaa
aabba
abbaa
abbbbbbbbbbbbbbbbbbba
bbaa
	(<--Enter)
	(<--Enter again to end)
yes
yes
yes
no
no
no

For logging, "regx.log" can be seen. For details, please see LOGGING
section.

----------------------------------------------------------------------
10. LICENCING
----------------------------------------------------------------------
This work is protected and licenced under CC-BY-NC-SA 3.0(Creative Commons,
Non-commercial,Share-Alike Version 3.0).The license allows any user to view/
modify/share the material as long as it is non-commercial and not shared 
with a more restrictive license. Please follow the links below for more
details:

  Licence Summary: http://creativecommons.org/licenses/by-nc-sa/3.0/
  Licence Details: http://creativecommons.org/licenses/by-nc-sa/3.0/legalcode


Copyright (C) 2014  @Suraj ssamal@cse.unl.edu
