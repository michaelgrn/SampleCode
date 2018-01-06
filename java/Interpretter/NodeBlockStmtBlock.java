/**
 * This is the Node Block statement ; block class, the type of Block node that
 * reads in a statement and a block and executes them in order. This node is 
 * part of the Block class.
 * 
 * @author Michael Green
 *
 */
public class NodeBlockStmtBlock extends NodeBlock{
	
	private NodeStmt stmt;
	private NodeBlock block;
	
	
	public NodeBlockStmtBlock(NodeStmt stmt, NodeBlock block){
		this.stmt = stmt;
		this.block = block;
	
	}

	public double eval(Environment env) throws EvalException{
			Double temp = stmt.eval(env);
			if(this.block != null){
				block.eval(env);
			}
			return temp;

	}
	

}
