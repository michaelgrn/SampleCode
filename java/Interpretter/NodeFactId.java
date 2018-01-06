/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node evaluates a fact which is an identifier.
 * 
 * @author Michael Green
 *
 */
public class NodeFactId extends NodeFact {

    private String id;

    public NodeFactId(int pos, String id) {
	this.pos=pos;
	this.id=id;
    }

    public double eval(Environment env) throws EvalException {
	return env.get(pos,id);
    }

}
