
/**
 * This node performs a relational operation between two doubles and returns 1.0
 * if the statement is true, or 0.0 if the statement is false.
 * 
 * @author Michael Green
 *
 */
public class NodeRelop extends Node{
	
	private String relop;

    public NodeRelop(int pos, String relop) {
	this.pos=pos;
	this.relop=relop;
    }

    public double op(double d, double e) throws EvalException {
	if( relop.equals("<"))
		if(d<e){
			return 1.0;
		}else{
			return 0.0;
		}
	    
	if( relop.equals("<="))
		if(d<=e){
			return 1.0;
		}else{
			return 0.0;
		}
	if( relop.equals(">"))
		if(d>e){
			return 1.0;
		}else{
			return 0.0;
		}
	if( relop.equals(">="))
		if(d>=e){
			return 1.0;
		}else{
			return 0.0;
		}
	if( relop.equals("<>"))
		if(d!=e){
			return 1.0;
		}else{
			return 0.0;
		}
	if( relop.equals("=="))
		if(d==e){
			return 1.0;
		}else{
			return 0.0;
		}

	throw new EvalException(pos,"bogus relop: "+relop);
    }

}
