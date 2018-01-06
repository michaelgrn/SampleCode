
/**
 * This is the Node Block statement class, the type of Block node that
 * only reads in a statement and executes that. This node is part of the
 * Block class.
 * 
 * @author Michael Green
 *
 */
public class NodeBlockStmt extends NodeBlock{
	
	private NodeStmt stmt;
	
	public NodeBlockStmt(NodeStmt stmt){
		this.stmt = stmt;
	}

	public double eval(Environment env) throws EvalException{
			Double temp = stmt.eval(env);

			return temp;

	}

}
