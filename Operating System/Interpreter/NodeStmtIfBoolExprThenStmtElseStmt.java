/**
 * Statement node that evaluates an If boolean expression then statement else statement.
 * 
 * @author Michael Green
 *
 */
public class NodeStmtIfBoolExprThenStmtElseStmt extends NodeStmt{
	NodeBoolExpr bX;
	NodeStmt st;
	NodeStmt st2;
	
	NodeStmtIfBoolExprThenStmtElseStmt(NodeBoolExpr bX, NodeStmt st, NodeStmt st2){
		this.bX = bX;
		this.st = st;
		this.st2 = st2;
	}
	
	public double eval(Environment env) throws EvalException {
    	double toDet = bX.eval(env);
    	if(toDet > 0.0){
    		st.eval(env);
    	}else{
    		st2.eval(env);
    	}
    	return toDet;
    
    }
}
