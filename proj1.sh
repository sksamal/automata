#!/bin/sh
#######################################################
# This script acts as a driver to read standard
# input and feed them as arguments to the automata.jar
# file
# LastUpdated: Oct,25, 2014
#######################################################

JAVA="/usr/bin/java"
JAVAAPP="./automata.jar"

# First Line is Regular expression
#echo "Enter the Regular Expression:"
read regx 
#echo "Regular Expression: " $regx 

# Read strings to be tested 
#echo "Enter the strings (blank line to exit)"
while read line
do
    strs="$strs $line"
    if [ "$line" == "" ]; then
	break
    fi
done 
if [ "$strs" == "" ]; then
     echo "No strings to be tested, cannot proceed!" 
     exit
fi
#echo "Strings to be tested: " $strs
$JAVA -jar $JAVAAPP "$regx" $strs
