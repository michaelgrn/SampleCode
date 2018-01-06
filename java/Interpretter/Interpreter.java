// This is the main class/method for the interpreter.
// Each command-line argument is a complete program,
// which is scanned, parsed, and evaluated.
// All evaluations share the same environment,
// so they can share variables.



/**
 * Very little modification done to this class. Primarily made sure that 
 * it did not print out the result of parsing each of the statements read
 * in, leaving that to the write node.
 * 
 * @author Michael Green
 *
 */
public class Interpreter {

    public static void main(String[] args) {
	Parser parser=new Parser();
	Environment env=new Environment();
	for (String stmt: args)
	    try {
		parser.parse(stmt).eval(env);
	    } catch (SyntaxException e) {
		System.err.println(e);
	    } catch (EvalException e) {
		System.err.println(e);
	    }
    }

}
