package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.FileIO;
import model.Player;
import model.Recorder;
import Helper.Utility;
import model.*;

public class View extends Application {

    Player player;
    Recorder recorder;
    FileIO fileIO;


    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            player = new Player();
            recorder = new Recorder(player.getOut(), player.getMinim());
            FileIO fileIO = new FileIO();
            BorderPane borderPane = new BorderPane();
            CenterContainer center = new CenterContainer(player, recorder);
            TopContainer top = new TopContainer(player, fileIO);

            borderPane.setTop(top);
            borderPane.setCenter(center);
            Scene scene = new Scene(borderPane);

            Utility.debugOn();

            String css = this.getClass().getResource("style.css").toExternalForm();
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



    public void stop() {
        player.quit();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
