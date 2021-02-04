/**
 * Node Statement Assignment, allows us to assign a value to variable. 
 * 
 * @author Michael Green
 *
 */
public class NodeStmtAssn extends NodeStmt {

    private NodeAssn assn;

    public NodeStmtAssn(NodeAssn assn) {
	this.assn=assn;
    }

    public double eval(Environment env) throws EvalException {
    return assn.eval(env);
    }

}
