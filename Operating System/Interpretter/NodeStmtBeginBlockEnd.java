/**
 * Statement node that evaluates a Begin block End statement.
 * 
 * @author Michael Green
 *
 */
public class NodeStmtBeginBlockEnd extends NodeStmt {

    private NodeBlock block;

    public NodeStmtBeginBlockEnd(NodeBlock block) {
	this.block=block;
    }

    public double eval(Environment env) throws EvalException {
    return block.eval(env);
    }

}
