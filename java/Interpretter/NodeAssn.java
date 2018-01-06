/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node assigns values to a variable. 
 * 
 * @author Michael Green
 *
 */
public class NodeAssn extends Node {

    private String id;
    private NodeExpr expr;

    public NodeAssn(String id, NodeExpr expr) {
	this.id=id;
	this.expr=expr;
    }

    public double eval(Environment env) throws EvalException {
	return env.put(id,expr.eval(env));
    }

}
