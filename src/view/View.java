package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import Helper.FileIO;
import model.Player;
import model.Recorder;
import Helper.Utility;
import model.*;

public class View extends Application {

    private Player player;
    private Recorder recorder;
    private FileIO fileIO;
    
    private BorderPane borderPane;
    private CenterContainer center;
    private TopContainer top;   
    private Scene scene;

    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        	
        	// Model
            player = new Player();
            recorder = new Recorder(player.getOut(), player.getMinim());
                        
            Utility.debugOn();
            
            // Stylesheet
            String css = this.getClass().getResource("style.css").toExternalForm();
            
            // View
            borderPane = new BorderPane();
            center = new CenterContainer(player, recorder);
            top = new TopContainer(player, fileIO);
            fileIO = new FileIO(center);

            borderPane.setTop(top);
            borderPane.setCenter(center);

            scene = new Scene(borderPane);
            scene.getStylesheets().add(css);
            scene.addEventHandler(KeyEvent.KEY_PRESSED, center.getControlPane().getKeyListener());
            primaryStage.setTitle("Hello World");
            primaryStage.setMinWidth(850);
            primaryStage.setMinHeight(700);
            primaryStage.setFullScreen(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CenterContainer getCenter() {
		return center;
	}

	public void stop() {
        player.quit();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
