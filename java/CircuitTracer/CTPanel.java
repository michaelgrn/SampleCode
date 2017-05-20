import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * 
 * This generates the GUI for circuitTracer. It reads in an arraylist of
 * bestpaths from the main method and then casts that to a JList. Then
 * it populates a panel full of buttons to depict the trace states.
 * 
 * @author Michael Green
 *
 */
@SuppressWarnings("serial")
public class CTPanel extends JPanel {
	private JList<Object> pathList;
	private char[][] pathSquare;
	private JButton[][] pathSquareButtons;
	private JPanel rightPanel;
	private ArrayList<String> titles;
	private ArrayList<TraceState> bP;
	private int selectionIndex = 0;

	
	/**
	 * 
	 * The constructor for the GUI. Reads in the bestPaths to populate the
	 * JPanel full of buttons that depicts the trace state.
	 * 
	 * @param bestPaths the arraylist read in from main that has the shortest paths
	 */
	public CTPanel(ArrayList<TraceState> bestPaths)  {
		bP = bestPaths;
		String title = "";
		int count = 0;
		titles = new ArrayList<String>();
		for(TraceState T: bestPaths){
			title = " Route " + count+"  ";
			titles.add(title);
			count++;
		}
		setLayout(new BorderLayout());
		Object[] pro = titles.toArray();
		pathList = new JList<Object>(pro);//interesting piece of code, wonder if I could do this smoother
		ListListener listListener = new ListListener();
        pathList.setSelectedIndex(0);
        pathList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));

        JScrollPane scrollpane = new JScrollPane(pathList,//heard someone just added their arraylist to their scrollpane
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.add(scrollpane, BorderLayout.WEST);
    	
        /** This block of code populates the pathSquare which contains all the bestPaths. */
		
        pathSquare = bestPaths.get(0).getBoard().getBoard();
		int rowLen = bestPaths.get(0).getBoard().numRows();
		int colLen = bestPaths.get(0).getBoard().numCols();
		pathSquareButtons = new JButton[rowLen][colLen];
		char contents;
		String cVal = "";
		Font cFont = new Font(Font.MONOSPACED, Font.BOLD, 28);
		Font tFont = new Font(Font.MONOSPACED, Font.ITALIC, Font.BOLD);//using two styles (bold and italic) makes the text invisible
		Font xFont = new Font(Font.DIALOG, Font.BOLD, 22);

		for(int row = 0; row <rowLen; row++){
			for(int col = 0; col <colLen; col++){
				contents = pathSquare[row][col];
				
				pathSquareButtons[row][col] = new JButton(contents+"");
				pathSquareButtons[row][col].setEnabled(false);//changes the state of buttons to unpressable
				pathSquareButtons[row][col].setOpaque(true);//allows the buttons to be colored
				cVal = contents +"";
				pathSquareButtons[row][col].setName(cVal);
				

				if(pathSquareButtons[row][col].getName().contains("1")){
					pathSquareButtons[row][col].setBackground(Color.GREEN);
					pathSquareButtons[row][col].setFont(cFont);
				}
				if(pathSquareButtons[row][col].getName().contains("2")){
					pathSquareButtons[row][col].setBackground(Color.RED);
					pathSquareButtons[row][col].setFont(cFont);
				}
				if(pathSquareButtons[row][col].getName().contains("T")){
					pathSquareButtons[row][col].setBackground(Color.CYAN);
					pathSquareButtons[row][col].setFont(tFont);
				}
				if(pathSquareButtons[row][col].getName().contains("X")){
					pathSquareButtons[row][col].setBackground(Color.BLACK);
					pathSquareButtons[row][col].setFont(xFont);
					
				}
				if(pathSquareButtons[row][col].getName().contains("O")){
					pathSquareButtons[row][col].setBackground(Color.LIGHT_GRAY);
					pathSquareButtons[row][col].setFont(tFont);
					
				}

			}
		}

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(rowLen,colLen));
		for(int row = 0; row <rowLen; row++){
			for(int col = 0; col <colLen; col++){
				
				rightPanel.add(pathSquareButtons[row][col]);

			}
		}
	this.add(rightPanel, BorderLayout.CENTER);
	pathList.addListSelectionListener(listListener);
		
	}
	
	/**
	 * 
	 * The list listener that changes the pathSqure to match the Jlist selection
	 *
	 */
	private class ListListener implements ListSelectionListener{


//		@Override
		public void valueChanged(ListSelectionEvent event){
			if (!event.getValueIsAdjusting()){
				selectionIndex = pathList.getSelectedIndex();
				UpdateSquare(selectionIndex);
			}
		}
	}
	
	/**
	 * 
	 * Updates the pathSquare when the JList selection changes
	 * 
	 * @param index reads in the selected index from the JList
	 * 
	 */
	public void UpdateSquare(int index){
		this.remove(rightPanel);
		pathSquare = bP.get(index).getBoard().getBoard();
		int rowLen = bP.get(index).getBoard().numRows();
		int colLen = bP.get(index).getBoard().numCols();
		pathSquareButtons = new JButton[rowLen][colLen];
		char contents;
		String cVal = "";
		Font cFont = new Font(Font.MONOSPACED, Font.BOLD, 28);
		Font tFont = new Font(Font.MONOSPACED, Font.ITALIC, Font.BOLD);
		Font xFont = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		Font oFont = new Font(Font.DIALOG, Font.ITALIC, 16);
		for(int row = 0; row <rowLen; row++){
			for(int col = 0; col <colLen; col++){
				contents = pathSquare[row][col];
				
				
				pathSquareButtons[row][col] = new JButton(contents+"");
				pathSquareButtons[row][col].setEnabled(false);
				pathSquareButtons[row][col].setOpaque(true);
				cVal = contents +"";
				pathSquareButtons[row][col].setName(cVal);
				

				if(pathSquareButtons[row][col].getName().contains("1")){
					pathSquareButtons[row][col].setBackground(Color.GREEN);
					pathSquareButtons[row][col].setFont(cFont);
				}
				if(pathSquareButtons[row][col].getName().contains("2")){
					pathSquareButtons[row][col].setBackground(Color.RED);
					pathSquareButtons[row][col].setFont(cFont);
					
				}
				if(pathSquareButtons[row][col].getName().contains("T")){
					pathSquareButtons[row][col].setBackground(Color.CYAN);
					pathSquareButtons[row][col].setFont(tFont);
				}
				if(pathSquareButtons[row][col].getName().contains("X")){
					pathSquareButtons[row][col].setBackground(Color.BLACK);
					pathSquareButtons[row][col].setFont(xFont);
					
				}
				if(pathSquareButtons[row][col].getName().contains("O")){
					pathSquareButtons[row][col].setBackground(Color.LIGHT_GRAY);
					pathSquareButtons[row][col].setFont(tFont);
					
				}

			}
		}

		rightPanel = new JPanel();
		rightPanel.setLayout(new GridLayout(rowLen,colLen));
		for(int row = 0; row <rowLen; row++){
			for(int col = 0; col <colLen; col++){
				
				rightPanel.add(pathSquareButtons[row][col]);

			}
		}
		
		this.add(rightPanel, BorderLayout.CENTER);
		this.revalidate();
	}
	
}
