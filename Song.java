package application;

import java.io.File;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Song {
	private File file;
	private String title;
	private String artist;
	private String album;
	private Media music;
	private MediaPlayer mp;
	
	public Song(File file) {
		music = new Media(file.toURI().toString());
		mp = new MediaPlayer(music);
		artist = (String) music.getMetadata().get("artist");
		title = (String) music.getMetadata().get("title");
		album = (String) music.getMetadata().get("album");
	}
	
	public void play() {
		mp.play();
	}
	
	public void pause() {
		mp.pause();
	}
	
	public void stop() {
		mp.stop();
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getAlbum(){
		return album;
	}
	
	
	
	
	
}
