/**
 * Statement node that evaluates a While boolExpr do statment node.
 * 
 * @author Michael Green
 *
 */
public class NodeStmtWhileBoolExprDoStmt extends NodeStmt{
	NodeBoolExpr bX;
	NodeStmt st;
	
	NodeStmtWhileBoolExprDoStmt(NodeBoolExpr bX, NodeStmt st){
		this.bX = bX;
		this.st = st;
	}
	
	public double eval(Environment env) throws EvalException {
    	double toDet = bX.eval(env);
    	
    	while(toDet > 0.0){
    		st.eval(env);
    		toDet = bX.eval(env);
    	}
    	
    	return toDet;
    
    }
}
