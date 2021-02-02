/**
 * Part of the Interpreter assignment for CS 354
 * This is the evaluation exception class
 * Its purpose is to catch evaluation exceptions.
 * 
 * @author Michael Green
 *
 */

@SuppressWarnings("serial")
public class EvalException extends Exception {

    private int pos;
    private String msg;

    public EvalException(int pos, String msg) {
	this.pos=pos;
	this.msg=msg;
    }

    public String toString() {
	return "eval error"
	    +", pos="+pos
	    +", "+msg;
    }

}
