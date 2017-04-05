package application;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
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
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import javafx.util.Duration;

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
	private Label timeLabel;
	@FXML
	private TableColumn<Song, String> songCol,artistCol,albumCol;
	
	
	ObservableList<Song> songList = FXCollections.observableArrayList();
	List<File> list;
	FileChooser fileChooser = new FileChooser();
	Song mySong;
	Song current;
	Image img = new Image(getClass().getResourceAsStream("/application/resources/blank.png"));
	ImageView blank = new ImageView(img);
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {		
		songCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("title"));
		artistCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("artist"));
		albumCol.setCellValueFactory(
				new PropertyValueFactory<Song,String>("album"));
		createVolume();
		//createTracker();
		//updateValues();
		playBtn.setDisable(true);
		stopBtn.setDisable(true);
		volume.setDisable(true);
		//tracker.setDisable(true);
		blank.setFitHeight(150);
		blank.setFitWidth(200);
		label.setGraphic(blank);
		
	}
	
	public void createVolume() {
		volume.setMin(0);
		volume.setMax(100);
		volume.setValue(100);
		volume.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				table.getSelectionModel().getSelectedItem().getMP().setVolume(volume.getValue()/100.0);
			}
			
		});
	}
	
	/*protected void updateValues() {
		Platform.runLater(new Runnable() {
			public void run() {
				Duration currentTime = table.getSelectionModel().getSelectedItem().getMP().getCurrentTime();
				timeLabel.setText(formatTime(currentTime,table.getSelectionModel().getSelectedItem().getDuration()));
				tracker.setDisable(table.getSelectionModel().getSelectedItem().getDuration().isUnknown());
				if(!tracker.isDisable() 
					&& !tracker.isValueChanging()
					&& table.getSelectionModel().getSelectedItem().getDuration().greaterThan(Duration.ZERO)) {
					//double x = table.getSelectionModel().getSelectedItem().getDuration().toMillis();
					//tracker.setValue(x*100.0);
				}
			}
			
		});
	}*/
	
	private static String formatTime(Duration elapsed, Duration duration) {
		   int intElapsed = (int)Math.floor(elapsed.toSeconds());
		   int elapsedHours = intElapsed / (60 * 60);
		   if (elapsedHours > 0) {
		       intElapsed -= elapsedHours * 60 * 60;
		   }
		   int elapsedMinutes = intElapsed / 60;
		   int elapsedSeconds = intElapsed - elapsedHours * 60 * 60 
		                           - elapsedMinutes * 60;
		 
		   if (duration.greaterThan(Duration.ZERO)) {
		      int intDuration = (int)Math.floor(duration.toSeconds());
		      int durationHours = intDuration / (60 * 60);
		      if (durationHours > 0) {
		         intDuration -= durationHours * 60 * 60;
		      }
		      int durationMinutes = intDuration / 60;
		      int durationSeconds = intDuration - durationHours * 60 * 60 - 
		          durationMinutes * 60;
		      if (durationHours > 0) {
		         return String.format("%d:%02d:%02d/%d:%02d:%02d", 
		            elapsedHours, elapsedMinutes, elapsedSeconds,
		            durationHours, durationMinutes, durationSeconds);
		      } else {
		          return String.format("%02d:%02d/%02d:%02d",
		            elapsedMinutes, elapsedSeconds,durationMinutes, 
		                durationSeconds);
		      }
		      } else {
		          if (elapsedHours > 0) {
		             return String.format("%d:%02d:%02d", elapsedHours, 
		                    elapsedMinutes, elapsedSeconds);
		            } else {
		                return String.format("%02d:%02d",elapsedMinutes, 
		                    elapsedSeconds);
		            }
		        }
		    }
	
	/*public void createTracker() {
		tracker.setMin(0);
		tracker.valueProperty().addListener(new InvalidationListener() {
			@Override
			public void invalidated(Observable observable) {
				if(tracker.isValueChanging()) {
					table.getSelectionModel().getSelectedItem().getMP().seek(
							table.getSelectionModel().getSelectedItem().getDuration().multiply(tracker.getValue()/100.0));
				}
			}
		});
	}*/
	
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
		//tracker.setDisable(false);
	}
	
	@FXML
	public void playSong(ActionEvent event) {
		Status status = table.getSelectionModel().getSelectedItem().getMP().getStatus();
		if(status == Status.HALTED || status == Status.UNKNOWN) {
			//don't do anything
			return;
		}
		if(status == Status.PAUSED
			|| status == Status.READY
			|| status == Status.STOPPED) {
			if(table.getSelectionModel().getSelectedItem().isEnd()) {
				table.getSelectionModel().getSelectedItem().getMP().seek(
						table.getSelectionModel().getSelectedItem().getMP().getStartTime());
				table.getSelectionModel().getSelectedItem().setIsEnd(false);
			}
			table.getSelectionModel().getSelectedItem().play();
		} else {
			table.getSelectionModel().getSelectedItem().pause();
		}
		
		
		ImageView img = new ImageView(table.getSelectionModel().getSelectedItem().getCover());
		img.setFitHeight(150);
		img.setFitWidth(200);
		label.setGraphic(img);
	}
	
	@FXML
	public void stopSong(ActionEvent event) {
		table.getSelectionModel().getSelectedItem().getMP().stop();
	}
	

}