import java.awt.BorderLayout;
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
 * This is the scaled version of the MyTunes GUI, containing everything but the songSquare. While this
 * class is able to access and change information on the main PlayList this functionality is vestigal.
 * The goal was to make only the MyTunesGUIMax have access to the PlayList, or ideally to have MyTunes
 * contain all the Jlist information with each class merely being a reflection of it. Ran out of time.
 * 
 * Examples of use:
 * 
 * 			file = new File("/Users/candlewolf/Desktop/Ω/workspace/Project5/src/playlist.txt");
 *			playList = new PlayList(file);
 * 
 * 		    MyTunesGUIPanel2 = new MyTunesGUIMid(playList);
 * 
 * 			JTabbedPane tp = new JTabbedPane();	
 * 			tp.addTab("Scale", MyTunesGUIPanel2);
 * 	
 * @author Michael Green
 *
 */
@SuppressWarnings("serial")
public class MyTunesGUIMid extends JPanel {

		private PlayList playList;
		private JList<Song> songList;
		
		private ImageIcon switchUpIcon = new ImageIcon("images/move-up-24.gif");
		private ImageIcon switchDownIcon = new ImageIcon("images/move-down-24.gif");
		private ImageIcon playIcon = new ImageIcon("images/play-32.gif");
		private ImageIcon stopIcon = new ImageIcon("images/stop-32.gif");
		private ImageIcon fastForwardIcon = new ImageIcon("images/media-skip-forward-32.gif");
		private ImageIcon rewindIcon = new ImageIcon("images/media-skip-backward-32.gif");
		
		private JButton switchUpButton, switchDownButton, addSongButton, removeSongButton, fastForwardButton, rewindButton, stopButton, playButton, saveListButton, loadListButton;
		private JPanel topPanel, topPanelTop, topPanelBottom, centerPanel, leftPanel, leftPanelTop, leftPanelBottom, buttonPanelLeft, buttonPanelBottom, controlPanel, controlPanelTop, 
					   controlPanelBottom;
		private JLabel playListNameLabel, playListSongLabel, playListTimeLabel, songLabelTitle, songLabel, songLabelArtist;
		
		private JButton[] buttonArray = new JButton[10];
		private DecimalFormat decForm;
		private Double TPT;
		
		public static buttons playButtons;
	
		/**
		 * Constructor: Builds medium sized panel with all the interactive features of the MyTunesGUIMax class
		 * 				without the songSquare
		 *
		 *@param pList PlayList read in from the MyTunes class
		 */
		
		public MyTunesGUIMid(PlayList pList){
			
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