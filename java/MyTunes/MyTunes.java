import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * An interesting class, incorporates a lot of methodology of the orginal project with some new
 * implementations to ensure a smoothly running Tabbed Pane. Wanted to make this the only class with
 * the JList and PlayList and just pass information into the other JPanels. Lacked the time and the 
 * foresight to implement this earlier. Runs perfect, but could be better. 
 * 
 * 
 * @author Michael Green
 *
 */
@SuppressWarnings("serial")
public class MyTunes extends JFrame{
		
	private JFrame frame;
	private PlayList playList;
	private int index = 0;
	private MyTunesGUIMax MyTunesGUIPanel1;
	private MyTunesGUIMid MyTunesGUIPanel2;
	private MyTunesGUIMin MyTunesGUIPanel3;
	private Timer timer;
	private static final int MILLIS_PER_SEC = 1000;
	private int delay = 0;
	private File loadFile, file;
	private SongSquareListener songSquareListener;
	
	/**
	 * Constructor: Assembles all the GUI classes and turns them into a tabbedPane.
	 * If a Jframe so requires a driver to assemble itself; could be written to
	 * implement itself.
	 * 
	 * Example of use:
	 * 
	 * 		MyTunes frame = new MyTunes();
			frame.pack();
	 *
	 */
		public MyTunes(){
			
			try {
				UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
			} catch (Exception e) {
				e.printStackTrace();
			}

			frame = new JFrame("myTunes");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setResizable(false);
			
			file = new File("/Users/candlewolf/Desktop/Ω/workspace/Project5B/src/playlist.txt");
			playList = new PlayList(file);

			/*
			 * Instantiates all the panels and adds them to the tabbed pane
			 */
			
			MyTunesGUIPanel1 = new MyTunesGUIMax(playList);
		    MyTunesGUIPanel2 = new MyTunesGUIMid(playList);
		    MyTunesGUIPanel3 = new MyTunesGUIMin();
			
			
			JTabbedPane tp = new JTabbedPane();
			tp.addTab("Maximize", MyTunesGUIPanel1);
			tp.addTab("Scale", MyTunesGUIPanel2);
			tp.addTab("Minimize", MyTunesGUIPanel3);
			
			/*
			 * Instantiates all the listeners and adds them to the panels
			 */
			
			songListListener1 songListListener1 = new songListListener1();
			songListListener2 songListListener2 = new songListListener2();
			buttonListener buttonListener = new buttonListener();
			
			songSquareListener = new SongSquareListener();
			MyTunesGUIPanel1.setJList(songListListener1);
			MyTunesGUIPanel2.setJList(songListListener2);
			MyTunesGUIPanel1.setButtonListener(buttonListener);
			MyTunesGUIPanel2.setButtonListener(buttonListener);
			MyTunesGUIPanel3.setButtonListener(buttonListener);
			MyTunesGUIPanel1.setSquareListener(songSquareListener);
			
			
			frame.getContentPane().add(tp);
			
			
			changeListener changeListener = new changeListener();
			tp.addChangeListener(changeListener);
			
			frame.setPreferredSize(new Dimension(1200, 650));
			frame.pack();
			frame.setVisible(true);
			
		
		
		}

		/* 
		 * Dectects the change in the tabbed pane and changes it to the proper pane
		 */
		private class changeListener implements ChangeListener{

			@Override
			public void stateChanged(ChangeEvent e) {
				
				  JTabbedPane sourceTabbedPane = (JTabbedPane) e.getSource();
		          index = sourceTabbedPane.getSelectedIndex();

		          if(index == 0){
		      		frame.setPreferredSize(new Dimension(1200, 650));
		      		frame.pack();
		          }else if(index == 1){
		      		frame.setPreferredSize(new Dimension(602, 650));
		      		frame.pack();
		          }else if(index == 2){
		      		frame.setPreferredSize(new Dimension(400, 275));
			      	frame.pack();
		          }
		         
				
			}
			
		}

		
		/* 
		 * The index selector for the JList in MyTunesGUIPanel1, receives index selection
		 * from songListListener2. Only one that plays songs upon selection.
		 * 
		 */
		private class songListListener1 implements ListSelectionListener{


			@Override
			public void valueChanged(ListSelectionEvent event){

				if (!event.getValueIsAdjusting()){
					
				if(event != null){
					int toPlay = MyTunesGUIPanel1.getSongIndex();
					MyTunesGUIPanel2.setListIndex(toPlay);

					if(playList.getPlaying()!=null){
									
						playList.stop();
						stopTimer();
					
					}
		           
					playList.playSong(toPlay);
		           	UpdatePlaying();

		            if(playList.getPlaying() != null){

		            	startTimer();
		            
		            }
		            
		            UpdateSquare();
		            
					}
				}
			}
		}
	
		/*
		 * This listener is used only for the MyTunesGUIPanel2 and sets the 
		 * index on the first. 
		 */
		private class songListListener2 implements ListSelectionListener{

			@Override
			public void valueChanged(ListSelectionEvent event)
			{

				if (!event.getValueIsAdjusting()){
					
				if(event != null){
					int toPlay = MyTunesGUIPanel2.getSongIndex();
					MyTunesGUIPanel1.setListIndex(toPlay);
					}
				}
			}
		}

		/*
		 * This is the listener used to read in button commands from the various MyTunes
		 * classes. Runs a method which compares the buttonarray location to the enum ordinal
		 * and returns the appropriate object.
		 * 
		 */
		public class buttonListener implements ActionListener{

			@SuppressWarnings("static-access")
			public void actionPerformed(ActionEvent event)
			{
				int index = MyTunesGUIPanel1.getSongIndex();
				
				if(MyTunesGUIPanel3.isButton(event.getSource(), MyTunesGUIPanel3.playButtons.fButton) 
				|| MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.fButton) 
				|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.fButton)){//ff
	            
					if(MyTunesGUIPanel1.checkList() == false){
						setListIndex(index+1);
	            	}
					
				}else if(MyTunesGUIPanel3.isButton(event.getSource(), MyTunesGUIPanel3.playButtons.rButton )
						|| MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.rButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.rButton)){//rw
        	
					if(MyTunesGUIPanel1.checkList() == false){
						setListIndex(index-1);
					}
							
				}else if(MyTunesGUIPanel3.isButton(event.getSource(), MyTunesGUIPanel3.playButtons.pButton)
						|| MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.pButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.pButton)){//play
					
					if(MyTunesGUIPanel1.checkList() == false){
		            	if(playList.getPlaying() != null){
			            	playList.stop();
			            	stopTimer();
			            }
						
		            	playList.playSong(index);
						startTimer();
						UpdatePlaying();
						UpdateSquare();
		            }else if(MyTunesGUIPanel1.checkList() == true && playList.getNumSongs() >0 ){//if no song is selected, play first song
		            	
		            	setListIndex(0);
		            	
		            }
					
				}else if(MyTunesGUIPanel3.isButton(event.getSource(), MyTunesGUIPanel3.playButtons.sButton) 
						|| MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.sButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.sButton)){//stop
	            
					if(playList.getPlaying() != null){
		            
						playList.stop();
		            	stopTimer();
		            	UpdatePlaying();
	            	
	            	}
				
				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.suButton) 
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.suButton)){//switchup
					
					if(MyTunesGUIPanel1.checkList() == false){
			        	index = playList.moveUp(index);
			        	setListData();
			        	setListIndex(index);
			        	playList.stop();
				        stopTimer();
				        UpdatePlaying();
				    
			        }
					
				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.sdButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.sdButton)){//switchdown
	            	
					if(MyTunesGUIPanel1.checkList() == false){
		            	index = playList.moveDown(index);
		            	setListData();
		            	setListIndex(index);
				        playList.stop();
				        stopTimer();
				        UpdatePlaying();
				        
	            	}
					
				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.asButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.asButton)){//addsong
					
					if(playList.getPlaying() != null){
		            	playList.stop();
		            	stopTimer();
		            }
	            	
					try {
						showForm();
					} catch (UnsupportedAudioFileException | IOException e) {

					}
					
	            	UpdatePlaying();
	            	setListData();
	            	UpdateSquare();
			
				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.rsButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.rsButton)){//removesong
	            	
					if(MyTunesGUIPanel1.checkList() == false){
		            	
						if(playList.getPlaying() != null){
			            	playList.stop();
			            	stopTimer();
			            }
	            	
	            	deleteForm();
	            	setListData();
	            	setClearSelection();
	            	UpdatePlaying();
	            	setListData();
	            	UpdateSquare();
	            	}

				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.spButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.spButton)){//savelist
	            	
					if(playList.getPlaying() != null){
		            	playList.stop();
		            	stopTimer();
		            }
	            	
					saveForm();
					            	
				}else if(MyTunesGUIPanel2.isButton(event.getSource(), MyTunesGUIPanel2.playButtons.lpButton)
						|| MyTunesGUIPanel1.isButton(event.getSource(), MyTunesGUIPanel1.playButtons.lpButton)){//loadlist
	            	
					if(playList.getPlaying() != null){
		            	playList.stop();
		            	stopTimer();
		            }
	            
					loadForm();
	            	UpdatePlaying();
	            	UpdateSquare();
					
				}
			}
		}
		
		/*
		 * Listener used on the songsquare. Reads in the name of the square button
		 * (which corresponds to the index of the song on the playlist) and modifies
		 * that value if it is one of the sonq square buttons that is a repeat entry.
		 *  
		 */
		private class SongSquareListener implements ActionListener{

			@Override


			public void actionPerformed(ActionEvent event){
				JButton ish = (JButton) event.getSource();
				int index = Integer.parseInt(ish.getName());

				if(index >= playList.getNumSongs()){
					index = index - playList.getNumSongs();
				}
				if(index == MyTunesGUIPanel1.getSongIndex()){
					MyTunesGUIPanel1.setClearSelection();
				}
				MyTunesGUIPanel1.setListIndex(index);

			}
		}
		
		/*
		 *This listener is used on the timer. When activated it stops the timer
		 *and the song playing.
		 *
		 */
		private class TimerListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent event){
				
				if(playList.getPlaying() != null){
					playList.stop();
				}
			
				timer.stop();
			}
		}
	
		/**
		 * Updates the song square to be both proper color and playlist content
	     */
		private void UpdateSquare(){
			MyTunesGUIPanel1.UpdateSquare(playList);
			MyTunesGUIPanel1.setSquareListener(songSquareListener);
			frame.pack();
			
		}
	
		/**
		 * Synchronizes the playlist between panels
		 */
		private void UpdatePlaying(){
			
			MyTunesGUIPanel1.setUpdatePlaying(playList);
			MyTunesGUIPanel2.setUpdatePlaying(playList);
			MyTunesGUIPanel3.setUpdatePlaying(playList);
			
			frame.pack();
			
		}
		
		/**
		 * Updates the playList in the panels to the current playList 
		 */
		private void updatePlayList(){
			
			MyTunesGUIPanel1.UpdatePlayList(playList);
			MyTunesGUIPanel2.UpdatePlayList(playList);
		
		}
		
    	
		/**
		 * Sets the JList info in the panels to the current playList
		 */
		private void setListData(){

			MyTunesGUIPanel1.setListData(playList);
        	MyTunesGUIPanel2.setListData(playList);
		
		}
		
		/**
		 * Passes in current index selection and sets that index on both panels
		 * 
		 * @param index current index selection
		 */
		private void setListIndex(int index){
		
			MyTunesGUIPanel1.setListIndex(index);
        	MyTunesGUIPanel2.setListIndex(index);
		
		}
		
		/**
		 * Clears the JList selection
		 */
		private void setClearSelection(){
			
        	MyTunesGUIPanel1.setClearSelection();
        	MyTunesGUIPanel2.setClearSelection();
		
		}

		/**
		 * Stops the timer. (May be extraneous)
		 */
		private void stopTimer(){
			timer.stop();

		}

		/**
		 * Sets the timer and the delay proper
		 */
		private void startTimer(){

       	delay = (playList.getPlaying().getPlayTime()*MILLIS_PER_SEC);
       	timer = new Timer(delay, new TimerListener());
       	timer.start();

		}
		
		/**
		 * Form that is used to add songs. Uses a JFilechooser in order to select
		 * which wav file will be used. Includes fail safes if the user does not add
		 * information. 
		 * @throws IOException 
		 * @throws UnsupportedAudioFileException 
		 */
		private void showForm() throws UnsupportedAudioFileException, IOException{
			
			JFileChooser chooser = new JFileChooser(".");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Wav Files",
					"*.wav", "wav");
				chooser.setFileFilter(filter);

			int status = chooser.showOpenDialog(null);

			if (status == JFileChooser.APPROVE_OPTION) {
				
				File file = chooser.getSelectedFile();

				JPanel formInputPanel = new JPanel();
				formInputPanel.setLayout(new BoxLayout(formInputPanel, BoxLayout.Y_AXIS));

				JTextField titleField = new JTextField(20);
				JTextField artistField = new JTextField(20);
				JTextField playTimeField = new JTextField(20);
				JTextField filePathField = new JTextField(20);

				/*
				 *This code is used to set up the input panel. The user
				 *has already selected a file to be read in as the .wav
				 *and the rest requires user input.
				 * 
				 */

				formInputPanel.add(new JLabel("Title of the Song:"));
				formInputPanel.add(titleField);
				formInputPanel.add(new JLabel("Name of the Artist: "));
				formInputPanel.add(artistField);
				formInputPanel.add(new JLabel("Playtime of the song: (mm:ss) "));
				formInputPanel.add(playTimeField);
				formInputPanel.add(new JLabel("File path of the song: "));
				formInputPanel.add(filePathField);
				filePathField.setText(file.getPath());
			
				int result = JOptionPane.showConfirmDialog(null, formInputPanel, "Add User",
		    			JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

				if (result == JOptionPane.OK_OPTION)
				{
					
					
					/*
					 * This part of the code reads the song in and adds it to the 
					 * playList. If the user should neglect to put in any information
					 * then the defaults are used, (Unknown Song),(Unknown Artist).
					 * Code for using the length of the .wav file as the unknown 
					 * was taken from:
					 * 
					 * http://stackoverflow.com/questions/3009908/how-do-i-get-a-sound-files-total-time-in-java 
					 
					 */
					
					String title = titleField.getText();

					if(title.equals("")){
						title = "Unknown Song";
					}

					String artist = artistField.getText();

					if(artist.equals("")){
						artist = "Unknown Artist";
					}
					String playTime = playTimeField.getText();

					if(playTime.equals("")){
					    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
					    AudioFormat format = audioInputStream.getFormat();
					    long audioFileLength = file.length();
					    int frameSize = format.getFrameSize();
					    float frameRate = format.getFrameRate();
					    float durationInSeconds = (audioFileLength / (frameSize * frameRate));
					    int sec = (int)durationInSeconds % 60;
					    int min = (int)durationInSeconds / 60;
					    DecimalFormat decForm = new DecimalFormat("00");
					    playTime = decForm.format(min) + ":" + decForm.format(sec);
					    
					}

					String newFile = filePathField.getText();

					String delims = "[:]";
					String[] tokens = playTime.split(delims);
					String sec = tokens[1];
					String min = tokens[0];
					int minConv = Integer.parseInt(min) * 60;
					int secConv = Integer.parseInt(sec);
					int playtimeconv = (minConv+secConv);
					Song song = new Song(title,artist,playtimeconv,newFile);
					playList.addSong(song);
					setListData();

				}

			}
		}
	
		/**
		 * Present the option to remove a song from the playlist.
		 */
		private void deleteForm(){
			
			JPanel formInputPanel = new JPanel();
			formInputPanel.setLayout(new BoxLayout(formInputPanel, BoxLayout.Y_AXIS));
			formInputPanel.add(new JLabel("Would you like to remove " +
			playList.getSong(MyTunesGUIPanel1.getSongIndex()).getTitle()+ " from your playlist?"));

			int result = JOptionPane.showConfirmDialog(null, formInputPanel, "Remove Song",
	    			JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION)
			{
				playList.removeSong(MyTunesGUIPanel1.getSongIndex());
			}

		}
		
		/**
		 * Used to load a Playlist from file. Throws an error if attempting to load a bad playList file
		 * allowing the user to reselect or revert back to previous playlist. 
		 */
		private void loadForm(){
			JFileChooser chooser = new JFileChooser(".");
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Playlist Files",
					"*.txt", "txt");
				chooser.setFileFilter(filter);

			int status = chooser.showOpenDialog(null);
			if (status == JFileChooser.APPROVE_OPTION) {
				
				loadFile = chooser.getSelectedFile();
			try{	
				playList = new PlayList(loadFile);
			}catch(ArrayIndexOutOfBoundsException e){
				JPanel formInputPanel = new JPanel();
				formInputPanel.setLayout(new BoxLayout(formInputPanel, BoxLayout.Y_AXIS));
				formInputPanel.add(new JLabel("Unable to Load PlayList."));
				formInputPanel.add(new JLabel("Click okay to choose different file"));
				formInputPanel.add(new JLabel("Click cancel to revert to original playlist."));
				
				int result = JOptionPane.showConfirmDialog(null, formInputPanel, "Error",
		    			JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == JOptionPane.OK_OPTION)
				{
					loadForm();
				}else{
					playList = new PlayList(file);
				}

			}
			setListData();
			updatePlayList();
        	
			}
			
		}
		
		/**
		 * Method used to save the playList. Executes quite like the constructor but in reverse,
		 * writing out all its lines to a txt file.
		 * 
		 */
		private void saveForm(){
			
			JFrame parentFrame = new JFrame();
			JFileChooser fileChooser = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter("Playlist Files",
					"*.txt", "txt");
			fileChooser.setFileFilter(filter);
			fileChooser.setDialogTitle("Save File As:");   
			 
			int userSelection = fileChooser.showSaveDialog(parentFrame);
			 
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
			    BufferedWriter writer = null;
		        try {
		        	
					String delims = "[/]";
					String[] tokens = fileToSave.getAbsolutePath().split(delims);
					int pTitle = tokens.length-1;
					String tosave = tokens[pTitle]+"\n";
		            
		            for(int x = 0; x < playList.getNumSongs(); x++){
			           
		            	tosave += playList.getSong(x).getTitle() + "\n";
			            tosave += playList.getSong(x).getArtist() + "\n";
			            DecimalFormat decForm = new DecimalFormat("00");
			            int playTime = playList.getSong(x).getPlayTime();
			            int min = playTime/60;
			            int sec = playTime % 60;
			            
			            if(sec == 0){
			            	sec = 00;
			            }
			            
			            tosave += decForm.format(min) + ":" + decForm.format(sec) + "\n";
			            tosave += playList.getSong(x).getFilePath() + "\n";
		            }
		            
		            File logFile = new File(fileToSave.getAbsolutePath() + ".txt");
		            writer = new BufferedWriter(new FileWriter(logFile));
		            writer.write(tosave);
		       
		        } catch (Exception e) {
		            e.printStackTrace();
		        } finally {
		            try {
		                writer.close();
		            } catch (Exception e) {
		            
	            }
	        }
		}
	}
}

