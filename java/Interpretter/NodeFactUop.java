/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node evaluates a node which is a unary operator and a fact.
 * 
 * @author Michael Green
 *
 */
public class NodeFactUop extends NodeFact{
	
	private NodeUop uop;
	private NodeFact fact;
	
	public NodeFactUop(NodeUop uop, NodeFact fact){
		this.uop = uop;
		this.fact = fact;
	}
	
	public double eval(Environment env) throws EvalException{
		return uop.op(fact.eval(env));
	}

}
