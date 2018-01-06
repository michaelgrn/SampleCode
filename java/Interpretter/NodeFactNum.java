/**
 * Part of the Interpreter assignment for CS 354.
 * 
 * This class, and its subclasses,
 * collectively model parse-tree nodes.
 * Each kind of node can be eval()-uated.  
 * 
 * This node evalutes a fact which is a number.
 * 
 * @author Michael Green
 *
 */
public class NodeFactNum extends NodeFact {

    private String num;

    public NodeFactNum(String num) {
	this.num=num;
    }

    public double eval(Environment env) throws EvalException {
	return Double.parseDouble(num);
    }

}
