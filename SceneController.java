package application;

import java.awt.Desktop;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import javafx.scene.control.cell.PropertyValueFactory;
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
	private TableView<Song> table;
	@FXML
	private Label label;
	@FXML
	private ProgressBar proBar;
	@FXML
	private TableColumn<Song, String> songCol,artistCol,albumCol;
	
	
	ObservableList<Song> songList = FXCollections.observableArrayList();
	List<File> list;
	FileChooser fileChooser = new FileChooser();
	Song mySong;
	Song current;
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		songCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("title"));
		artistCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("artist"));
		albumCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("album"));
		createVolume();
		playBtn.setDisable(true);
		stopBtn.setDisable(true);
		volume.setDisable(true);
		
	}
	
	public void createVolume() {
		volume.setMin(0);
		volume.setMax(100);
		volume.setValue(100);
		volume.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				current.getMP().setVolume(volume.getValue()/100.0);
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
		list = fileChooser.showOpenMultipleDialog(theStage);
		if(list!=null){
			for(File x: list) {
				mySong = new Song(x);
				songList.add(mySong);
			}
		}
		table.setItems(songList);
		playBtn.setDisable(false);
		stopBtn.setDisable(false);
		volume.setDisable(false);
	}
	
	@FXML
	public void playSong(ActionEvent event) {
		current = table.getSelectionModel().getSelectedItem();
		ImageView img = new ImageView(current.getCover());
		img.setFitHeight(120);
		img.setFitWidth(200);
		label.setGraphic(img);
		current.play();
	}
	
	@FXML
	public void stopSong(ActionEvent event) {
		current.pause();
		
	}
	

}
