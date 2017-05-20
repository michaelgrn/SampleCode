import java.awt.Point;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Stack;

import javax.swing.JFrame;

/**
 * Search for shortest paths between start and end points on a circuit board
 * as read from an input file using either a stack or queue as the underlying
 * search state storage structure and displaying output to the console or to
 * a GUI according to options specified via command-line arguments.
 * 
 * @author mvail
 * 
 * Built the printUsage() and CircuitTracer() methods.
 * 
 * @author Michael Green
 */
public class CircuitTracer {
	private CircuitBoard board;
	private Storage<TraceState> stateStore;
	private ArrayList<TraceState> bestPaths;
	private String output;

	/** launch the program
	 * @param args three required arguments:
	 *  first arg: -s for stack or -q for queue
	 *  second arg: -c for console output or -g for GUI output
	 *  third arg: input file name 
	 */
	public static void main(String[] args) {
		if (args.length != 3) {
			printUsage();
			System.exit(1);
		}
		try {
			new CircuitTracer(args); //create this with args
		} catch (Exception e) {//removed stack tracer, instead prints out error message only
			System.out.println(e);
			System.exit(1);
		}
	}

	/** Print instructions for running CircuitTracer from the command line. */
	private static void printUsage() {
		System.out.println("Sorry Dave, I can't do that...");
		System.out.println("Correct format for program usage is:");
		System.out.println("first arg: -s for stack or -q for queue");
		System.out.println("second arg: -c for console output or -g for GUI output");
		System.out.println("third arg: input file name ");
		
	}
	
	/** 
	 * Set up the CircuitBoard and all other components based on command
	 * line arguments.
	 * 
	 * @param args command line arguments passed through from main()
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("static-access")
	private CircuitTracer(String[] args) throws FileNotFoundException {

	/*  parses the first argument and returns an exception if command is not recognized	 */
		
		if(args[0].contains("-s")){
			stateStore = stateStore.getStackInstance();
		}else if(args[0].contains("-q")){
			stateStore = stateStore.getQueueInstance();
		}else{
			printUsage();
			throw new IllegalArgumentException(args[0]+ " is not recognized as a valid command for first arg"); 
		}
		
	/*  parses the second argument and returns an exception if command is not recognized	 */
		
		if(args[1].contains("-c")){
			output = args[1];
		}else if(args[1].contains("-g")){
			output = args[1];
		}else{
			printUsage();
			throw new IllegalArgumentException(args[1]+ " is not recognized as a valid command for second arg"); 
		}
		

	/*  parses the third argument (the file) and returns an exception if command is not recognized	 */

		String fileBoard = args[2];
		try {
			board = new CircuitBoard(fileBoard);
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(args[2]+ " was not Found. Please try again." );
		}
		
	/*  initializes the search algorithm, establishes which points around the 
	 * 	starting point are empty, and then starts a trace at each one of those and
	 *  adds them to the storage.	 */

		bestPaths = new ArrayList<TraceState>();
		int count = 0;
		
		while(count < 1){
			
			Point point = board.getStartingPoint();//starting point
			
			if(board.isOpen(point.x-1, point.y)){//searches left
				TraceState left = new TraceState(board, point.x-1, point.y);
				stateStore.store(left);
				
			}
			if(board.isOpen(point.x+1, point.y)){//searches right
				TraceState right = new TraceState(board, point.x+1, point.y);
				stateStore.store(right);
				
			}
			if(board.isOpen(point.x, point.y-1)){//
				TraceState down = new TraceState(board, point.x, point.y-1);
				stateStore.store(down);
				
			}
			if(board.isOpen(point.x, point.y+1)){
				TraceState up = new TraceState(board, point.x, point.y+1);
				stateStore.store(up);
				
			}
			
			count++;
			
		}
		
		/* Pulls all the saved trace states from the stateStorage (stack/queue) that
		 *  were placed there in the initial search and runs the search on them. If
		 *  they have found the end point of the circuit they compare the path length 
		 *  to the current path length of the tracestates being stored in bestPath. If
		 *  best path doesn't have any paths in it yet, it initialized bestPaths with
		 *  that TraceState, if the bestPaths is empty, then initialize it and store
		 *  that TraceState. Otherwise if the path is shorter than the current one
		 *  stored it reinitializes best paths, and if the path is equal to the stored path
		 *  it adds it to best paths. */
		
		while(!stateStore.isEmpty()){
			TraceState retrieved = stateStore.retreive();//current TraceSate being pulled from stack
			if(retrieved.isComplete()){
				
				if(bestPaths.isEmpty()){//if empty then add retrieved
					bestPaths.add(retrieved);
				}else if(retrieved.pathLength() < bestPaths.get(0).pathLength()){//if retrieved is shortest path, reinitialize bestPaths and add
					bestPaths.clear();
					bestPaths.add(retrieved);
				}else if(retrieved.pathLength() == bestPaths.get(0).pathLength()){//if same size, just add to bestPaths
					bestPaths.add(retrieved);
				}
		
			}else{
				
				Point current = new Point(retrieved.getRow(), retrieved.getCol());//current location of the trace in retrieved
				
				
				if(retrieved.isOpen(current.x-1, current.y)){
					TraceState left = new TraceState(retrieved, current.x-1, current.y);
					stateStore.store(left);
				}
				if(retrieved.isOpen(current.x+1, current.y)){
					TraceState right = new TraceState(retrieved, current.x+1, current.y);
					stateStore.store(right);
				}
				if(retrieved.isOpen(current.x, current.y-1)){
					TraceState down = new TraceState(retrieved, current.x, current.y-1);
					stateStore.store(down);
				}
				if(retrieved.isOpen(current.x, current.y+1)){
					TraceState up = new TraceState(retrieved, current.x, current.y+1);
					stateStore.store(up);
				}
				
			}
		}
		
		if(bestPaths.isEmpty()){
			throw new InvalidFileFormatException("No path was found");//if no path was found, throw this error
		}
		
		if(output.contains("-c")){// if user chose to output file to console
			for(TraceState T: bestPaths){

			System.out.println(T.toString());
			}
		}else{// if user chose to output to GUI
			JFrame frame = new JFrame("Circuit Tracer");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new CTPanel(bestPaths));
			frame.pack();
			frame.setResizable(false);
			frame.setVisible(true);
		}

	}
	

	
	
} // class CircuitTracer
