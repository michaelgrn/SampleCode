/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node evaluates a fact which is an expression. 
 * 
 * @author Michael Green
 *
 */
public class NodeFactExpr extends NodeFact {

    private NodeExpr expr;

    public NodeFactExpr(NodeExpr expr) {
	this.expr=expr;
    }

    public double eval(Environment env) throws EvalException {
	return expr.eval(env);
    }

}
