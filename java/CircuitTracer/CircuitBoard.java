import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Represents a 2D circuit board as read from an input file.
 * 
 * Written by Michael:
 * 
 * Built the constructor that reads in .dat files and added numerous
 * Exception Handlers to ensure the file is valid.
 *  
 * @author mvail
 * @author Michael Green
 */
public class CircuitBoard {
	private char[][] board;
	private Point startingPoint;
	private Point endingPoint;

	//constants you may find useful
	private int ROWS; //initialized in constructor
	private int COLS; //initialized in constructor
	private final char OPEN = 'O'; //capital 'o'
	private final char CLOSED = 'X';
	private final char TRACE = 'T';
	private final char START = '1';
	private final char END = '2';
	private final String ALLOWED_CHARS = "OXT12";

	/** Construct a CircuitBoard from a given board input file, where the first
	 * line contains the number of rows and columns as ints and each subsequent
	 * line is one row of characters representing the contents of that position.
	 * Valid characters are as follows:
	 *  'O' an open position
	 *  'X' an occupied, unavailable position
	 *  '1' first of two components needing to be connected
	 *  '2' second of two components needing to be connected
	 *  'T' is not expected in input files - represents part of the trace
	 *   connecting components 1 and 2 in the solution
	 * 
	 * @param filename
	 * 		file containing a grid of characters
	 * @throws FileNotFoundException if Scanner cannot read the file
	 * @throws InvalidFileFormatException for any other format or content issue that prevents reading a valid input file
	 */
	public CircuitBoard(String filename) throws FileNotFoundException {

		Scanner fileScan = new Scanner(new File(filename));

		//TODO: parse the given file to populate the char[][]
		// throw FileNotFoundException if Scanner cannot read the file
		// throw InvalidFileFormatException if any formatting or parsing issues are encountered
		
		ROWS = 0; //replace with initialization statements using values from file
		COLS = 0;
		Boolean foundS = false;//found starting point
		Boolean foundE = false;//found ending point
		int cRow = 0; //used to navigate through grid to populate
		int cCol = 0; 
		
		int count = 0;//Counter used to read in first line
		String line = "";
		
		while(fileScan.hasNextLine()){
			line = fileScan.nextLine();
			
			if(count == 0){
			
				String delims = "[ ]";
				String[] tokens = line.split(delims);
				try{
				ROWS = Integer.parseInt(tokens[0]);
				COLS = Integer.parseInt(tokens[1]);
				}catch (NumberFormatException e){
					throw new NumberFormatException("Unable to parse initial parameters");
					//Thrown if either of the intiliazers are invalid
				}
				board = new char[ROWS][COLS];
				count++;//incremented to move past the initial part of the file
			
			}else{
				
				String delims = "[ ]";
				String[] tokens = line.split(delims);//delimits/tokenizes line to parse 
				
				for(String s : tokens){
						
					if(!ALLOWED_CHARS.contains(s)){//thrown if current entry contains any character besides those permitted
						throw new InvalidFileFormatException("Contains extraneous characters");
						
					}
					char present = s.charAt(0);
					board[cRow][cCol] = present;
					if(present == START && foundS == false){//establishes starting point
						Point sP = new Point(cRow, cCol);
						startingPoint = sP;
						foundS = true;
					}else if(present == START && foundS == true){//throws error if file contains more than one starting point
						throw new InvalidFileFormatException("Contains too many starting points");
					}
					
					if(present == END && foundE == false) {//establishes ending point
						Point eP = new Point(cRow, cCol);
						endingPoint = eP;
						foundE = true;
					}else if(present == END && foundE == true){//thrown if file contains too many ending points
						throw new InvalidFileFormatException("Contains too many ending points");
					}
					
					cCol++;
				
				}
				if(cCol != COLS){//thrown if file has too many or too few colums, kept track in cCol
					throw new InvalidFileFormatException("Number of columns does not match expected amount");
				}
				cRow++;
				cCol = 0;
			
			}
		
		}
		if(cRow != ROWS){//thrown if file has too many or not enough rows, kept track in cRow
			throw new InvalidFileFormatException("Number of rows does not match expected amount");
		}
		
		if(foundS == false){//thrown if no starting point is found
			throw new InvalidFileFormatException("No starting point found");
		}
		if(foundE == false){//thrown if no ending point is found
			throw new InvalidFileFormatException("No ending point found");
		}
		
				
		
		fileScan.close();
	}
	
	/** Copy constructor - duplicates original board
	 * 
	 * @param original board to copy
	 */
	public CircuitBoard(CircuitBoard original) {
		board = original.getBoard();
		startingPoint = new Point(original.startingPoint);
		endingPoint = new Point(original.endingPoint);
		ROWS = original.numRows();
		COLS = original.numCols();
	}

	/** utility method for copy constructor
	 * @return copy of board array */
	public char[][] getBoard() {
		char[][] copy = new char[board.length][board[0].length];
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				copy[row][col] = board[row][col];
			}
		}
		return copy;
	}
	
	/** Return the char at board position x,y
	 * @param row row coordinate
	 * @param col col coordinate
	 * @return char at row, col
	 */
	public char charAt(int row, int col) {
		return board[row][col];
	}
	
	/** Return whether given board position is open
	 * @param row
	 * @param col
	 * @return true if position at (row, col) is open 
	 */
	public boolean isOpen(int row, int col) {
		if (row < 0 || row >= board.length || col < 0 || col >= board[row].length) {
			return false;
		}
		return board[row][col] == OPEN;
	}
	
	/** Set given position to be a 'T'
	 * @param row
	 * @param col
	 * @throws OccupiedPositionException if given position is not open
	 */
	public void makeTrace(int row, int col) {
		if (isOpen(row, col)) {
			board[row][col] = TRACE;
		} else {
			throw new OccupiedPositionException("row " + row + ", col " + col + "contains '" + board[row][col] + "'");
		}
	}
	
	/** @return starting Point */
	public Point getStartingPoint() {
		return new Point(startingPoint);
	}
	
	/** @return ending Point */
	public Point getEndingPoint() {
		return new Point(endingPoint);
	}
	
	/** @return number of rows in this CircuitBoard */
	public int numRows() {
		return ROWS;
	}
	
	/** @return number of columns in this CircuitBoard */
	public int numCols() {
		return COLS;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++) {
				str.append(board[row][col] + " ");
			}
			str.append("\n");
		}
		return str.toString();
	}
	
}// class CircuitBoard
