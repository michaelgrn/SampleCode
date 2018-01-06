/**
 * Statement node that evaluates wr expr command. It evaluates an expr's value and writes it out
 * to the command line.
 * 
 * @author Michael Green
 *
 */
public class NodeStmtWrExpr extends NodeStmt{

	private NodeFact fact;
	
	public NodeStmtWrExpr(NodeFact fact) {
		this.fact = fact;
	}
    public double eval(Environment env) throws EvalException {
    	System.out.println(fact.eval(env));
    	return fact.eval(env);
    }
}
