/**
 * Statement node that evaluates a Read Id command. This reads in a value for an ID and 
 * assigns that value to it. 
 * 
 * @author Michael Green
 *
 */
public class NodeStmtRdId extends NodeStmt {
	
    private String id;
    private Double dbl;

    

    public double eval(Environment env) throws EvalException {
	return env.put(id,dbl);
    }
    
	public NodeStmtRdId(String id, Double toRead) {
		this.id = id;
		this.dbl = toRead;
	}

}
