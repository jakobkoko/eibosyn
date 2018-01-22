package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class View extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        BorderPane borderPane = new BorderPane();
        CenterContainer center = new CenterContainer();

        borderPane.setCenter(center);
        Scene scene = new Scene(borderPane);

        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);

        primaryStage.setTitle("Hello World");
        primaryStage.setWidth(900);
        primaryStage.setHeight(600);
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
