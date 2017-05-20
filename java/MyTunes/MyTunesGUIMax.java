import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionListener;




/**
 * The Maximum sized window. Contains all functionality required of the program.
 * Could be used by itself as the project. Lacking some of the essential methods
 * of manipulation found in the MyTunes class, though. 
 * 
 * Examples of Use:
 * 
 * 			file = new File("/Users/candlewolf/Desktop/Ω/workspace/Project5/src/playlist.txt");
 *			playList = new PlayList(file);
 * 
 * 			MyTunesGUIPanel1 = new MyTunesGUIMax(playList);
 * 
 * 			JTabbedPane tp = new JTabbedPane();
 *			tp.addTab("Maximize", MyTunesGUIPanel1);
 * 
 * 
 * @author Michael Green
 *
 */
@SuppressWarnings("serial")
public class MyTunesGUIMax extends JPanel {

		private PlayList playList;
		private JList<Song> songList;
		private JButton[] buttonArray = new JButton[10];
		private Song[][] songSquare;
		private JButton[][] songSquareButtons;
		private DecimalFormat decForm;
		private Double TPT;
		private static final int MAX_PLAYS = 50;
		public static buttons playButtons;
		
		private ImageIcon switchUpIcon = new ImageIcon("images/move-up-24.gif");
		private ImageIcon switchDownIcon = new ImageIcon("images/move-down-24.gif");
		private ImageIcon playIcon = new ImageIcon("images/play-32.gif");
		private ImageIcon stopIcon = new ImageIcon("images/stop-32.gif");
		private ImageIcon fastForwardIcon = new ImageIcon("images/media-skip-forward-32.gif");
		private ImageIcon rewindIcon = new ImageIcon("images/media-skip-backward-32.gif");
		
		private JButton switchUpButton, switchDownButton, addSongButton, removeSongButton, fastForwardButton, rewindButton, stopButton, playButton, songSquareButton,
						saveListButton, loadListButton;
		private JPanel  topPanel, topPanelTop, topPanelBottom, centerPanel, leftPanel, leftPanelTop, leftPanelBottom, rightPanel, buttonPanelLeft, buttonPanelBottom,
						controlPanel, controlPanelTop, controlPanelBottom;
		private JLabel playListNameLabel, playListSongLabel, playListTimeLabel, songLabelTitle, songLabel, songLabelArtist;
		
		
		/**
		 * Constructor: Builds a JPanel from the PlayList of the MyTunes class. Has all the functionality of the original program.
		 * SongSquare, JList, and buttons. Essentially the original program turned into an object for the sake of tabbed panes. 
		 * 
		 * @param pList PlayList read in from the MyTunes class
		 */
		public MyTunesGUIMax(PlayList pList){


			/*
			 * This block of code lays out the overall panel. Is broken down into a top panel (for
			 * the name of the playList) and the center panel which contains left and right panels. 
			 * Will add further details to each of the panels further on. Each name seems pretty intuitive.
			 * Top panel top is name of PlayList top panel bottom is playList details.
			 * 
			 */
			
			playList = pList;

        	setLayout(new BorderLayout());
        	topPanel = new JPanel();
        	topPanel.setLayout(new BorderLayout());

        	centerPanel = new JPanel();
        	centerPanel.setLayout(new GridLayout(1,2));

            add(centerPanel, BorderLayout.CENTER);
            add(topPanel, BorderLayout.NORTH);

            topPanelTop = new JPanel();
            topPanelBottom = new JPanel();

            topPanel.add(topPanelTop, BorderLayout.NORTH);
            topPanel.add(topPanelBottom, BorderLayout.SOUTH);

			playListNameLabel = new JLabel( " "+playList.getName());
			playListNameLabel.setFont(new Font ("Helvetica", Font.BOLD, 36));
			playListSongLabel = new JLabel("Number of Songs: " +playList.getNumSongs());
			TPT = (double)playList.getTotalPlayTime()/60;
			decForm = new DecimalFormat("0.00");
			playListTimeLabel = new JLabel(" Total Playtime: " + decForm.format(TPT) + "minutes");

			topPanelTop.add(playListNameLabel);
			topPanelBottom.add(playListSongLabel);
			topPanelBottom.add(playListTimeLabel);
			
			/*
			 * This is where we start to lay out the left panel. We break left Panel into top and bottom. 
			 * Top contains the JList, bottom containst he song information.
			 * 
			 */
			
			leftPanel = new JPanel();
			leftPanel.setLayout(new BorderLayout());

			centerPanel.add(leftPanel);

			leftPanelTop = new JPanel();
			leftPanelTop.setLayout(new BorderLayout());

			leftPanelBottom = new JPanel();
			leftPanelBottom.setLayout(new BorderLayout());

			leftPanel.add(leftPanelTop, BorderLayout.CENTER);
			leftPanel.add(leftPanelBottom, BorderLayout.SOUTH);

	        songList = new JList<Song>(playList.getSongArray());
	        songList.setSelectedIndex(0);
	        songList.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));

	        JScrollPane scrollpane = new JScrollPane(songList,
	                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	        leftPanelTop.add(scrollpane, BorderLayout.CENTER);

	        /*
	         * This code is use to create the buttons and assign their respective icons.
	         * Set icons global rather than local so that changing them is easier.
	         * Also proceeded to add each button to the buttonArray for use in the
	         * isButton command.
	         */
	     	        
			fastForwardButton = new JButton();;
			fastForwardButton.setIcon(fastForwardIcon);
	       	rewindButton = new JButton();
			rewindButton.setIcon(rewindIcon);
			stopButton = new JButton();
			stopButton.setIcon(stopIcon);
	        playButton = new JButton();
	        playButton.setIcon(playIcon);
			switchUpButton = new JButton();
	        switchUpButton.setIcon(switchUpIcon);
	        switchDownButton = new JButton();
	        switchDownButton.setIcon(switchDownIcon);
			addSongButton = new JButton("Add Song");
			removeSongButton = new JButton("Remove Song");
			saveListButton = new JButton("Save Playlist");
			loadListButton = new JButton("Load Playlist");

	        buttonArray[0] = fastForwardButton;
	        buttonArray[1] = rewindButton;
	        buttonArray[2] = stopButton;
	        buttonArray[3] = playButton;
	        buttonArray[4] = switchUpButton;
	        buttonArray[5] = switchDownButton;
	        buttonArray[6] = addSongButton;
	        buttonArray[7] = removeSongButton;
	        buttonArray[8] = saveListButton;
	        buttonArray[9] = loadListButton;


	        /*
	         * This the code used to create the buttons around the JList. 
	         * (Move Up[4], Move Down[5])
	         * (Add Song[6], Remove Song[7], Save Playlist[8], Load Playlist[9]) 
	         */

	        buttonPanelLeft = new JPanel();
	        buttonPanelLeft.setLayout(new BoxLayout(buttonPanelLeft, BoxLayout.Y_AXIS));
	        switchUpButton.setAlignmentY(Component.CENTER_ALIGNMENT);
	        switchDownButton.setAlignmentY(Component.CENTER_ALIGNMENT);
	        buttonPanelLeft.add(Box.createVerticalGlue());
	        buttonPanelLeft.add(buttonArray[4]);
	        buttonPanelLeft.add(buttonArray[5]);
	        buttonPanelLeft.add(Box.createVerticalGlue());

	        leftPanelTop.add(buttonPanelLeft, BorderLayout.WEST);

	        buttonPanelBottom = new JPanel();

	        buttonPanelBottom.add(buttonArray[6]);
		    buttonPanelBottom.add(buttonArray[7]);
	        buttonPanelBottom.add(buttonArray[8]);
	        buttonPanelBottom.add(buttonArray[9]);

	        leftPanelTop.add(buttonPanelBottom, BorderLayout.SOUTH);

	        /*
	         * And this block of code is used to create the control panel, all the play manipulations
	         * and commands. Control panel top displays the name and artist of the song, bottom panel contains
	         * buttons for playList manipulation.  (Rewind[1], Stop[2], Play[3], FastForward[0])
	         */
	        
	        controlPanel = new JPanel();
	        
	        Font font = new Font("Helvetica", Font.BOLD, 16);
	        controlPanel.setLayout(new BorderLayout());
	        controlPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLoweredBevelBorder(), "Now Playing", 0, 0, font));
	        
	        controlPanelTop = new JPanel();
	        songLabelTitle = new JLabel("none");
	        songLabelTitle.setFont(new Font ("Helvetica", Font.BOLD, 30));
	        songLabel = new JLabel(" by: ");
	        songLabelArtist = new JLabel("no one");
	        songLabelArtist.setFont(new Font ("Helvetica", Font.BOLD, 30));
	        controlPanelTop.add(songLabelTitle);
			controlPanelTop.add(songLabel);
			controlPanelTop.add(songLabelArtist);
			controlPanelTop.add(Box.createVerticalStrut(80));
			controlPanel.add(controlPanelTop, BorderLayout.NORTH);

			controlPanelBottom = new JPanel();
	       
			controlPanelBottom.add(buttonArray[1]);
	        controlPanelBottom.add(buttonArray[2]);
	        controlPanelBottom.add(buttonArray[3]);
	        controlPanelBottom.add(buttonArray[0]);

	        controlPanel.add(controlPanelBottom, BorderLayout.SOUTH);

	        leftPanelBottom.add(controlPanel);

	        /*
	         * This block of code is used to implement the song square code. Though this 
	         * each square is assigned a number via count that corresponds to the corresponding
	         * index in the playList. After assigning all the relevant commands to each 
	         * songSquareButton it adds them to the right panel.
	         * 
	         */
	       
			songSquare = playList.getMusicSquare();
			songSquareButtons = new JButton[songSquare.length][songSquare.length];
			Song s;
			int count = 0;

			for(int row = 0; row <songSquare.length; row++){
				for(int col = 0; col <songSquare.length; col++){
					s = songSquare[row][col];
			
					Color color = getHeatMapColor(s.getPlayCount());
					songSquareButtons[row][col]= new JButton(s.getTitle());
					songSquareButtons[row][col].setBackground(color);
					songSquareButtons[row][col].setName(""+ count);

					count++;
				}
			}

			rightPanel = new JPanel();
			rightPanel.setLayout(new GridLayout(songSquare.length, songSquare.length));

			for(int row = 0; row <songSquare.length; row++){
				for(int col = 0; col <songSquare.length; col++){
					
					rightPanel.add(songSquareButtons[row][col]);

				}
			}

			centerPanel.add(rightPanel);

		}
		
		/**
		 * Method provided by Luke Hindman, passing Enum ordinals into the main Method in order to 
		 * determine which button was pressed.
		 * 
		 * @author Luke Hindman
		 * @author Michael Green
		 */
		public static enum buttons {
			
			fButton, rButton, sButton, pButton, suButton, sdButton, asButton, rsButton, spButton, lpButton;
			
		}
		
		/**
		 * See enum above. This method passes in the ordinal value of an enum as well as the object
		 * which was pessed in order to determine which button was used. Compares index in buttonArray[]
		 * to ordinal value of buttons.
		 * 
		 * @param o the event passed in from MyTunes.java, is a button
		 * @param button enum used to determine which JButton was pressed
		 * @return boolean value which tells MyTunes.java if a cetain button was pressed by comparison
		 */
		
		public boolean isButton(Object o, buttons button){
			
			return o == buttonArray[button.ordinal()];
					
		}
		
		/**
		 * Clears the selection on the JList 
		 */
	
		public void setClearSelection(){
		
			songList.clearSelection();
		
		}
		
		/**
		 * Gets the currently slected index of the JList
		 * 
		 * @return the currently selected index of the JList
		 */
		public int getSongIndex(){
			
			int index = songList.getSelectedIndex();
			return index;
			
		}
		
		/**
		 * Used to set the playList data after changes to it
		 * 
		 * @param playList reads in a playList from the MyTunes class and sets it as the playList in this class
		 */
		public void setListData(PlayList playList){
			
			songList.setListData(playList.getSongArray());
			
		}
		
		/**
		 * Sets selected index on the Jlist
		 * 
		 * @param index index value read in from MyTunes class
		 */
		public void setListIndex(int index){
			
			songList.setSelectedIndex(index);
		}
		
		/**
		 * Sets list selection listener on the jList
		 * 
		 * @param songListListener listener passed in from MyTunes
		 */
		public void setJList(ListSelectionListener songListListener){
			
			 songList.addListSelectionListener(songListListener);
			
		}
		
		/**
		 * Sets button listener to buttons in this class
		 * 
		 * @param buttonListener listener read in from MyTunes class
		 */
		public void setButtonListener(ActionListener buttonListener){
			
			saveListButton.addActionListener(buttonListener);
			loadListButton.addActionListener(buttonListener);
			switchUpButton.addActionListener(buttonListener);
			switchDownButton.addActionListener(buttonListener);
			addSongButton.addActionListener(buttonListener);
			removeSongButton.addActionListener(buttonListener);
			fastForwardButton.addActionListener(buttonListener);
			rewindButton.addActionListener(buttonListener);
			stopButton.addActionListener(buttonListener);
	        playButton.addActionListener(buttonListener);
		
		}
		
		/**
		 * Adds squareListener to each entry in the songSquare
		 * 
		 * @param squareListener listener passed in from MyTunes class
		 */
		public void setSquareListener(ActionListener squareListener){
			for(int row = 0; row <songSquare.length; row++){
				for(int col = 0; col <songSquare.length; col++){

					songSquareButtons[row][col].addActionListener(squareListener);
				
				}
			}
		}
				
		/**
		 * Makes sure the Jlist has a selection
		 * 
		 * @return boolean of Jlist selection
		 */
		
		public boolean checkList(){
			
			boolean list = true;
			if(songList.isSelectionEmpty() == false){
			
				list = false;
			
			}
			
			return list;
		}


		 /**
	     * Given the number of times a song has been played, this method will
	     * return a corresponding heat map color.
	     *
	     * Sample Usage: Color color = getHeatMapColor(song.getTimesPlayed());
	     *
	     * This algorithm was borrowed from:
	     * http://www.andrewnoske.com/wiki/Code_-_heatmaps_and_color_gradients
	     *
	     * @param plays The number of times the song that you want the color for has been played.
	     * @return The color to be used for your heat map.
	     */
	    private Color getHeatMapColor(int plays){
	    	
	         double minPlays = 0, maxPlays = MAX_PLAYS;    // upper/lower bounds
	         double value = (plays - minPlays) / (maxPlays - minPlays); // normalize play count

	         // The range of colors our heat map will pass through. This can be modified if you
	         // want a different color scheme.
	         Color[] colors = { Color.CYAN, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED };
	         int index1, index2; // Our color will lie between these two colors.
	         float dist = 0;     // Distance between "index1" and "index2" where our value is.

	         if (value <= 0) {
	              index1 = index2 = 0;
	         } else if (value >= 1) {
	              index1 = index2 = colors.length - 1;
	         } else {
	              value = value * (colors.length - 1);
	              index1 = (int) Math.floor(value); // Our desired color will be after this index.
	              index2 = index1 + 1;              // ... and before this index (inclusive).
	              dist = (float) value - index1; // Distance between the two indexes (0-1).
	         }

	         int r = (int)((colors[index2].getRed() - colors[index1].getRed()) * dist)
	                   + colors[index1].getRed();
	         int g = (int)((colors[index2].getGreen() - colors[index1].getGreen()) * dist)
	                   + colors[index1].getGreen();
	         int b = (int)((colors[index2].getBlue() - colors[index1].getBlue()) * dist)
	                   + colors[index1].getBlue();

	         return new Color(r, g, b);
	    }

	    
	    
	    /**
	     * Method used to update the song square once songs have been removed, added, or changed
	     * 
	     * @param playList reads in the playlist from the Mytunes class
	     */
	    public void UpdateSquare(PlayList playList){
	    	if (rightPanel != null && playList.getNumSongs() != 0){
				this.centerPanel.remove(rightPanel);
				
				songSquare = playList.getMusicSquare();
				songSquareButtons = new JButton[songSquare.length][songSquare.length];
				Song s;
				int count = 0;

				for(int row = 0; row <songSquare.length; row++){
					for(int col = 0; col <songSquare.length; col++){
						s = songSquare[row][col];
						Color color = getHeatMapColor(s.getPlayCount());
						songSquareButtons[row][col]= new JButton(s.getTitle());
						songSquareButtons[row][col].setBackground(color);
						songSquareButtons[row][col].setName(""+ count);

						count++;

					}
				}

				rightPanel = new JPanel();
				rightPanel.setLayout(new GridLayout(songSquare.length, songSquare.length));
				
		
				for(int row = 0; row <songSquare.length; row++){
					for(int col = 0; col <songSquare.length; col++){

						rightPanel.add(songSquareButtons[row][col]);

					}
				}
				
				/*
				 * Notable portion of code. Whenever the song square is removed of all songs
				 * it displays "No Songs in Playlist". Would like to add a listener to this
				 * that when clicked causes the addSong form to be displayed. Alas, ran out 
				 * of time.
				 */
	    	
	    	}else{
					this.centerPanel.remove(rightPanel);
					songSquareButton = new JButton("No songs in Playlist");
					rightPanel = new JPanel();
					rightPanel.setLayout(new GridLayout(songSquare.length, songSquare.length));
					rightPanel.add(songSquareButton);
					this.centerPanel.add(rightPanel);
					this.revalidate();
			}

			this.centerPanel.add(rightPanel);
			this.revalidate();
			
	    }

	    

	    /**
	     * Reads in the playlist and updates the currently playing song
	     * 
	     * @param playList passed in from MyTunes
	     */
	    public void setUpdatePlaying(PlayList playList){
	    	if(playList.getPlaying() != null){
	    		if(songLabel != null){
		    		
	    			songLabelTitle.setText( ""+playList.getPlaying().getTitle());
		    		songLabelArtist.setText(""+playList.getPlaying().getArtist());
		    		controlPanelTop.revalidate();
		    		
				}
	    	}else{
	    		if(songLabel != null){
	    			
	    			songLabelTitle.setText( "none");
		    		songLabelArtist.setText("no one");
		    		controlPanelTop.revalidate();
		    		
	    		}
	    	}
	    }


	    /**
	     * Updates the title and information about the playList
	     * 
	     * @param playList passed in from Mytunes
	     */
	    public void UpdatePlayList(PlayList playList){

	    	playListNameLabel.setText(""+playList.getName());
			playListSongLabel.setText("Number of Songs: " +playList.getNumSongs());
			TPT = (double)playList.getTotalPlayTime()/60;
			playListTimeLabel.setText(" Total Playtime: " + decForm.format(TPT) + " minutes");

			this.revalidate();
					
	    }
	
}
