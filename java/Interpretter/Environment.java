import java.util.HashMap;
/**
 * Part of the Interpreter assignment for CS 354
 * This is the environment class
 * Its purpose is mostly to store variable values and allow the program to have
 * more than one variable. It implements a hashmap that allows for this functionality.
 * All nodes pass their eval method through this.
 * 
 * @author Michael Green
 *
 */
public class Environment {
	 @SuppressWarnings("rawtypes")
	HashMap map = new HashMap();

	    @SuppressWarnings("unchecked")
		public double put(String var, double d) {
	    	map.put(var, d);
	    	return d;
	    }
	    public double get(int pos, String var) throws EvalException {
	    	Object value = map.get(var);
	    	pos++;
	    	if(value == null){
	    		throw new EvalException(pos, var + " is undefined");
	    	}
	       	return (double)(value);
	    }

}
