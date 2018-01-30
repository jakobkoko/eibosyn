package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Player;

public class View extends Application {
    Player player;
    @Override
    public void start(Stage primaryStage) throws Exception{

        try {
            Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
            player = new Player();
            BorderPane borderPane = new BorderPane();
            CenterContainer center = new CenterContainer(player);
            TopContainer top = new TopContainer();

            borderPane.setTop(top);
            borderPane.setCenter(center);
            Scene scene = new Scene(borderPane);

            String css = this.getClass().getResource("style.css").toExternalForm();
            scene.getStylesheets().add(css);
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
