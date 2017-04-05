package application;

import java.io.File;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.MapChangeListener;
import javafx.collections.MapChangeListener.Change;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Song {
	private String title;
	private String artist;
	private String album;
	private SimpleStringProperty pTitle;
	private SimpleStringProperty pArtist;
	private SimpleStringProperty pAlbum;
	private Media music;
	private MediaPlayer mp;
	private Image coverArt;
	
	public Song(File file) {
		music = new Media(file.toURI().toString());
		music.getMetadata().addListener((Change<? extends String, ? extends Object> c) -> {
	        if (c.wasAdded()) {
	            if ("artist".equals(c.getKey())) {
	            	System.out.println(c.getKey()+":"+c.getValueAdded());
	            	this.pArtist = new SimpleStringProperty(c.getValueAdded().toString());
	            	//pArtist.set(c.getValueAdded().toString());
	                artist = c.getValueAdded().toString();	
	            } else if ("title".equals(c.getKey())) {
	                title = c.getValueAdded().toString();
	                System.out.println(c.getKey()+":"+c.getValueAdded());
	            } else if ("album".equals(c.getKey())) {
	                album = c.getValueAdded().toString();
	                System.out.println(c.getKey()+":"+c.getValueAdded());
	            } else if ("image".equals(c.getKey())) {
	            	coverArt = (Image) c.getValueAdded();
	            }
	        }
	    });
		mp = new MediaPlayer(music);
		System.out.println(pArtist);
		System.out.println(artist);
		//artist = (String) mp.getMedia().getMetadata().get("artist");
		//title = (String) music.getMetadata().get("title");
		//album = (String) music.getMetadata().get("album");
		//artist = "test";
		//album = "test";
		//title = "test";
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
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public void setArtist(String artist){
		this.artist = artist;
	}
	
	public String getAlbum(){
		return album;
	}
	
	public void setAlbum(String album){
		this.album = album;
	}
	
	public Image getCover(){
		return coverArt;
	}
	
	public MediaPlayer getMP(){
		return mp;
	}
	
	
	
	
	
}
