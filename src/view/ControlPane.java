package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ControlPane extends HBox {

    private Button playButton;
    private SliderBox volume;
    private SliderBox balance;
    private SliderBox echo;

    public ControlPane() {
        playButton = new Button("Play");
        volume = new SliderBox("volume");
        balance = new SliderBox("balance");
        echo = new SliderBox("echo");

        this.setStyle("-fx-background-color: red");
        this.setSpacing(30);

        this.getChildren().addAll(playButton, volume, balance, echo);
    }
}
