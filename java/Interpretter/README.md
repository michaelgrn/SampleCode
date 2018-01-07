Name: Michael Green
Assignment: IA2
Date: Thu Oct 26 21:55:42 MDT 2017

Overview:

A brief run down of the program. Implemented the ability to read in and write out values
in this version. Whenever anything is evaluated the output is not printed to console. 
Must use the wr command in order to do so. Implemented boolean expressions with
if then statements as well as if then else statements, as well as while do loops. In
order to run the test classes Interpreter must be compiled first. See written grammar
for full extent of functionality. 

In order to run the Interpretter it must first be compiled as following

$javac Interpreter.java

and then can be run like 

$java Interpreter "x = 2.0; wr x";
2.0

All functionality is in floating point, so each numeric value must include a dot. The 
semicolon is used to seperate each statement. Examples for all operations can be found
in the test folder. 
