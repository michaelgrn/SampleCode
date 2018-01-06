/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node allows for the usage of unary operators.
 * 
 * @author Michael Green
 *
 */

public class NodeUop extends Node {
	
	private String uop;
	
	public NodeUop(int pos, String uop){
		this.pos = pos;
		this.uop = uop;
	}
	
	public double op(double d) throws EvalException{
		if(uop.equals("-")){
			return (-d);
		}
		throw new EvalException(pos,"bogus uop: " +uop);
	}
}
