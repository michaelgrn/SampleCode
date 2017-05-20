import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * This is the smallest window of the tabbed pane, containing no actual information of the playList
 * or the Jlist, drawing on the other classes for that information. As a JPanel it can be used as
 * the following:
 * 
 * 		    MyTunesGUIPanel3 = new MyTunesGUIMin();
 * 
 * 			JTabbedPane tp = new JTabbedPane();
 * 			tp.addTab("Minimize", MyTunesGUIPanel3);
 * 
 * @author Michael Green
 *
 */
@SuppressWarnings("serial")
public class MyTunesGUIMin extends JPanel {

		private ImageIcon playIcon = new ImageIcon("images/play-32.gif");
		private ImageIcon stopIcon = new ImageIcon("images/stop-32.gif");
		private ImageIcon fastForwardIcon = new ImageIcon("images/media-skip-forward-32.gif");
		private ImageIcon rewindIcon = new ImageIcon("images/media-skip-backward-32.gif");
		
		private JButton fastForwardButton,  rewindButton, stopButton, playButton;
		private JLabel playListNameLabel,  playListSongLabel, playListTimeLabel, songLabelTitle, songLabel, songLabelArtist;
		private JPanel controlPanel, controlPanelTop, controlPanelBottom;
		
		private JButton[] buttonArray = new JButton[4];
		public static buttons playButtons;
		private DecimalFormat decForm;
		private Double TPT;
		
		/**
		 * Constructor: Builds minimized MyTunes panel. Does not contain a PlayList, 
		 * draws information from the main PlayList file and other JLists for song details.
		 */
		public MyTunesGUIMin(){
			
			
			/*
	         * And this block of code is used to create the control panel, all the play manipulations
	         * and commands. Control panel top displays the name and artist of the song, bottom panel contains
	         * buttons for playList manipulation.  (Rewind[1], Stop[2], Play[3], FastForward[0]).
	         * 
	         * This is a relatively small class and very derivative of MyTunesGUIMax. Only needs to display the
	         * control panel. Draws all information from the MyTunes class. 
	         * 
	         */
			
			fastForwardButton = new JButton();
			fastForwardButton.setIcon(fastForwardIcon);
			rewindButton = new JButton();
			rewindButton.setIcon(rewindIcon);
			stopButton = new JButton();
			stopButton.setIcon(stopIcon);
			playButton = new JButton();
	    	playButton.setIcon(playIcon);
 
	      
	    	buttonArray[0] = fastForwardButton;
	    	buttonArray[1] = rewindButton;
	    	buttonArray[2] = stopButton;
	    	buttonArray[3] = playButton;

	    	/*
	         * ControlPanel is made up of two Panels, controlPanelTop and controlPanelBottom.
	         * controlPanelTop displays the song information and controlPanelBottom provides the
	         * user with buttons to interface with the program. 
	         */

	        controlPanel = new JPanel();
	        controlPanel.setPreferredSize(new Dimension(400, 275));
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
	
			controlPanel.add(controlPanelTop, BorderLayout.NORTH);
			controlPanelTop.add(Box.createVerticalStrut(100));
			controlPanelBottom = new JPanel();
						
			/*
			 * (Rewind[1], Stop[2], Play[3], FastForward[0]).
			 */
	       
	        controlPanelBottom.add(buttonArray[1]);
	        controlPanelBottom.add(buttonArray[2]);
	        controlPanelBottom.add(buttonArray[3]);
	        controlPanelBottom.add(buttonArray[0]);
	        
	        controlPanel.add(controlPanelBottom, BorderLayout.CENTER);
	        
	        add(controlPanel);
		}
		
		/**
		 * Method provided by Luke Hindman, passing Enum ordinals into the main Method in order to 
		 * determine which button was pressed.
		 * 
		 * @author Luke Hindman
		 * @author Michael Green
		 */
		public static enum buttons {
		
			fButton, rButton, sButton, pButton;
			
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
		 * Sets button listener to buttons in this class
		 * 
		 * @param buttonListener listener read in from MyTunes class
		 */
		public void setButtonListener(ActionListener buttonListener){
			

			fastForwardButton.addActionListener(buttonListener);
			rewindButton.addActionListener(buttonListener);
			stopButton.addActionListener(buttonListener);
	        playButton.addActionListener(buttonListener);
		
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
