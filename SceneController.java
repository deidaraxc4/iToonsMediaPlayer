package application;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
	private TableColumn songCol,artistCol,albumCol;
	List<File> list;
	FileChooser fileChooser = new FileChooser();
	Desktop desktop;
	Song mySong;
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}
	
	// Event Listener on Button[#loadBtn].onAction
	@FXML
	public void loadFile(ActionEvent event) {
		Node source = (Node) event.getSource();
	    Window theStage = source.getScene().getWindow();
		fileChooser.setTitle("Select MP3 files");
		//File file = fileChooser.showOpenDialog(theStage);
		//mySong = new Song(file);
		list = fileChooser.showOpenMultipleDialog(theStage);
		if(list!=null){
			for(File x: list) {
				mySong = new Song(x);
			}
		}
		updateTable();
	}
	
	@FXML
	public void playSong(ActionEvent event) {
		mySong.play();
	}
	
	@FXML
	public void stopSong(ActionEvent event) {
		mySong.pause();
	}
	
	public void updateTable(){
		songCol.setText("hi");
	}
	

	

}
