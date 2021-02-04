
/**
 * This node is the head of all programs. There is are not multiple types of a
 * prog node because prog node only ever contains a block. When the parser runs
 * it initially creates one of these nodes and proceeds to parse the entire program.
 * 
 * @author Michael Green
 *
 */
public class NodeProg extends Node{
	
	private NodeBlock block;
	
	public NodeProg(NodeBlock block){
		this.block = block;
	}
	
	public double eval(Environment env) throws EvalException{
		return block.eval(env);
		
	}
}
