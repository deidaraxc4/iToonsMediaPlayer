package application;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Window;

public class SceneController implements Initializable{
	@FXML
	private Button stopBtn;
	@FXML
	private Slider volume;
	@FXML
	private Button loadBtn;
	@FXML
	private Button playBtn;
	@FXML
	private TableView table;
	@FXML
	private Label label;
	@FXML
	private ProgressBar proBar;
	private TableColumn songCol,artistCol,albumCol;
	List<File> list;
	FileChooser fileChooser = new FileChooser();
	Desktop desktop;
	Song mySong;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		volume.setMin(0);
		volume.setMax(100);
		volume.setValue(100);
		volume.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				mySong.getMP().setVolume(volume.getValue()/100.0);
			}
			
		});
		
	}
	
	// Event Listener on Button[#loadBtn].onAction
	@FXML
	public void loadFile(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Window theStage = source.getScene().getWindow();
	    //set fileChooser filter
	    FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("MP3 files", "*.mp3");
	    fileChooser.getExtensionFilters().add(extFilter);
		fileChooser.setTitle("Select MP3 files");
		File file = fileChooser.showOpenDialog(theStage);
		mySong = new Song(file);
		/*list = fileChooser.showOpenMultipleDialog(theStage);
		if(list!=null){
			for(File x: list) {
				mySong = new Song(x);
			}
		}*/
	}
	
	@FXML
	public void playSong(ActionEvent event) {
		mySong.play();
	}
	
	@FXML
	public void stopSong(ActionEvent event) {
		//mySong.pause();
		System.out.println("song title: "+mySong.getArtist());
		ImageView img = new ImageView(mySong.getCover());
		//img.fitWidthProperty().bind(label.widthProperty());
		//img.fitHeightProperty().bind(label.heightProperty());
		img.setFitHeight(120);
		img.setFitWidth(200);
		label.setGraphic(img);
		//label.setGraphic(new ImageView(mySong.getCover()));
	}
	
	@FXML
	
	
	public void updateTable(){
		//songCol.s
	}
	

	

}
