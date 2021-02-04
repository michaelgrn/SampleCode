/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * One of the largest nodes in this language. This interpretter contains a seperate node
 * for each of the possible interpretation of a statement. The following nodes extend
 * NodeStmt:
 * 
 * -NodeStmtAssn
 * -NodeStmtRdId
 * -NodeStmtWrExpr
 * -NodeStmtIfBoolExprThenStmt
 * -NodeStmtIfBoolExprThenStmtElseStmt
 * -NodeStmtWhileBoolExprDoStmt
 * -NodeStmtBeginBlockEnd
 * 
 * @author Michael Green
 *
 */
public class NodeStmt extends Node {

}
