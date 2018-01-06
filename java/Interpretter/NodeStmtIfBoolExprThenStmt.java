/**
 * Statement node that evaluates an If boolean expression then statement.
 * 
 * @author Michael Green
 *
 */
public class NodeStmtIfBoolExprThenStmt extends NodeStmt{
	NodeBoolExpr bX;
	NodeStmt st;
	
	NodeStmtIfBoolExprThenStmt(NodeBoolExpr bX, NodeStmt st){
		this.bX = bX;
		this.st = st;
	}
	
	public double eval(Environment env) throws EvalException {
    	double toDet = bX.eval(env);
    	if(toDet > 0.0){
    		st.eval(env);
    	}
    	return toDet;
    
    }
}
