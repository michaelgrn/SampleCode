import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;


/**
 * The <code>PlayList</code> class represents a playlist. Each one
 * contains an arraylist of <code>Song</code>, a string for its name
 * and a <code>Song</code> that is currently playing. 
 *
 * Here is an example of how an PlayList can be created.
 * <pre>
 *    String name = "Cool Playlist";
 *    playList = new PlayList(name);
 * </pre>
 *
 * Here is an example of how a PlayList can be used.
 * <pre>
 *     playList.addSong(song);
 *     System.out.println("Playing song: " + playList.getPlaying());
 * </pre>
 *
 * @author Michael Green
 */
public class PlayList implements MyTunesPlayListInterface{


	private ArrayList<Song> songList = new ArrayList<Song>();
	private ArrayList<Song> copy2 = new ArrayList<Song>();
	private Song playing;
	private String name; 

	
	/**
	 * Constructor: Builds a playlist using the given parameters.
	 * @param file file used to create the playlist
	 */
	
	public PlayList(File file){
		
		Scanner scan = null;
		try {
			scan = new Scanner(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line = null, title = null, artist = null, playtime, filePath, min, sec;
		int count = 0, playtimeconv = 0, minConv, secConv; 
		Song song;
		
		songList = new ArrayList<Song>();
		this.playing = null;
		
		while(scan.hasNextLine()){
			line = scan.nextLine();
			
			if(count == 0){
				this.name =line.trim();
			}
			if(count == 1){
				
				title = line.trim();
				
			}else if(count == 2){
			
				artist = line.trim();
				
			}else if(count == 3){
			
				playtime = line.trim();
				
				String delims = "[:]";
				String[] tokens = playtime.split(delims);
				sec = tokens[1];
				min = tokens[0];
				minConv = Integer.parseInt(min) * 60;
				secConv = Integer.parseInt(sec);
				playtimeconv = (minConv+secConv);
			
			}else if(count == 4){
				
				filePath = line;
				
				song = new Song(title,artist,playtimeconv,filePath);
				songList.add(song);
				
				count = 0;
						
				
			}
			count++;
		}
		
	}
	
	/**
	 * Constructor: Builds a playlist using the given parameters.
	 * @param name playlist's title
	 */
	
	public PlayList(String name){
		
		songList = new ArrayList<Song>();
		this.name = name;
		this.playing = null;
	
	}
	
	/**
	 *Adds a <code>Song</code> to the <code>PlayList</code>
	 * @param aSong song added to the playlist
	 */
	
	public void addSong(Song aSong){
		
		songList.add(aSong);
		
	}
	
	/**
	 * Removes a <code>Song</code> from the <code>PlayList</code>
	 * @param rSong song removed from the playlist
	 * @return the removed song
	 */
		
	public Song removeSong(int rSong){
		
		Song result = null;
		
		if(rSong < songList.size() && rSong >= 0){
			songList.get(rSong);
			result = songList.remove(rSong);
		
		}
		
		return result;
	
	}
	
	
	/**
	 * Returns the name of this <code>PlayList</code>.
	 * @return the name
	 */
	
	public String getName(){
		
		return name;
	
	}
	
	/**
	 * Sets the name of this <code>PlayList</code>.
	 * @param newName the new name of the playlist
	 */
	
	public void setName(String newName){
		
		name = newName;
		
	}
	
	/**
	 * Returns a song from the <code>PlayList</code>.
	 * @param gSong gets a song from the playlist
	 * @return the song
	 */
	
	public Song getSong(int gSong){
		Song result = null;
		
		if(gSong < songList.size() && gSong >= 0){
			
			result = songList.get(gSong);
			
		}
		
		return result;
	}
	
	/**
	 * Returns the number of songs in the  <code>PlayList</code>.
	 * @return the number of songs
	 */
	
	public int getNumSongs(){
		
		return songList.size();
	
	}
	
	/**
	 * Gets the total playtime of the <code>PlayList</code>.
	 * @return the total play time
	 */
	
	public int getTotalPlayTime(){
	
		int tpt = 0;
		
		for(Song s : songList){
			tpt += s.getPlayTime();
		}	
		
		return tpt;
		
	}
	
	/**
	 * Returns the arraylist <code>Song</code>s of this <code>PlayList</code>.
	 * @return the arraylist in the playlist
	 */
	
	public ArrayList<Song> getSongList(){
		
		return songList;
	
	}

	/**
	 * Returns the song playing in this <code>PlayList</code>.
	 * @return the song playing
	 */
	
	public Song getPlaying(){
	
		return playing;
	
	}
	
	/**
	 * Plays a song in the  <code>PlayList</code> by passing in
	 * an index value from the JList.
	 * @param index the song that is being played. 
	 */
	
	public void playSong(int index) {
		// TODO Auto-generated method stub
		
		if(index < songList.size() && index >= 0){
			
			songList.get(index).play();
			this.playing = songList.get(index);
			
		}
		
	}


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#playSong(Song)
	 */
	public void playSong(Song song) {
		int count = 0;
		
		for(Song s: songList){
			if(s.getTitle().contains(song.getTitle())){
			songList.get(count).play();
			this.playing = songList.get(count);
			}
			count++;
		}

	}


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#stop()
	 */
	
	public void stop() {
		this.playing.stop();
		this.playing = null;
	}


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#getSongArray()
	 */
	
	public Song[] getSongArray() {
	
		Song[] copy = new Song[songList.size()];
		int count = 0;
        for(Song s: songList){
            copy[count] = s;
            count++;
        }


		return copy;
	}


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#moveUp(int)
	 */
	
	public int moveUp(int index) {
		// TODO Auto-generated method stub
		Song[] copy = new Song[songList.size()];
	
		
		int count = 0;
		Song first = songList.get(index);
		first.skipPlay();
		if(index != 0){
			Song second = songList.get(index -1);
        for(Song s: songList){
	            if(count != index && count != (index-1)){
	            	copy[count] = s;
	            }else if(count == index-1){
	            	copy[count] = first;
	            }else if(count == index){
	            	copy[count] = second;
	            }
	            count++;
	        }
	        index = index-1;
			
	        for(Song s: copy){
				copy2.add(s);
			}
			
			if(count != 0){
				songList.clear();
				for(Song s: copy2){
					songList.add(s);
				}
				copy2.clear();
			}
			  
		}else if(index == 0){
			
		
			
			for(int x = 0; x < songList.size()-1; x++){
				copy[x] = songList.get(x+1);
			}
			
			copy[songList.size()-1] = first;
	       
			index = songList.size()-1;
			
	        for(Song s: copy){
				copy2.add(s);

			}
			
	        count = 1;
	        
			if(count != 0){
				songList.clear();
				for(Song s: copy2){
					songList.add(s);
				}
				copy2.clear();
			}
		}
		
	

		
		return index;
	}


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#moveDown(int)
	 */
	
	public int moveDown(int index) {
		Song[] copy = new Song[songList.size()];
		
		
		int count = 0;
		Song first = songList.get(index);
		first.skipPlay();
		if(index != (songList.size()-1)){
			Song second = songList.get(index +1);
	        for(Song s: songList){
	            if(count != index && count != (index+1)){
	            	copy[count] = s;
	           
	            }else if(count == index+1){
	            	copy[count] = first;
	            
	            }else if(count == index){
	            	copy[count] = second;
	            
	            }
	            count++;
	        }
	        index = index+1;
	        songList.clear();
	        
			for(Song s: copy){
				songList.add(s);
				
			}
		}else if(index == (songList.size()-1)){
			
		
			
			for(int x = 0; x < songList.size()-1; x++){
				copy[x+1] = songList.get(x);
			}
			
			copy[0] = first;
	       
			index = 0;
			
	        for(Song s: copy){
				copy2.add(s);

			}
			
	        count = 1;
	        
			if(count != 0){
				songList.clear();
				for(Song s: copy2){
					songList.add(s);
				}
				copy2.clear();
			}
		}
		
		

		
		return index;
	}
	


	/* (non-Javadoc)
	 * @see MyTunesPlayListInterface#getMusicSquare()
	 */
	
	public Song[][] getMusicSquare() {
		int size = (int) Math.ceil(Math.sqrt((double)songList.size()));
		Song[][] copy = new Song[size][size];
		int count = 0;
		
		for(int row = 0; row <size; row++){
			for(int col = 0; col <size; col++){
				if(count == songList.size()){
					count = 0;
				}
				copy[row][col]= songList.get(count);
				count++;
				
			}
		}
		return copy;
	}
	


}



