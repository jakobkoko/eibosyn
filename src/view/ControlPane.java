package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Player;

public class ControlPane extends HBox {

    private Button playButton;
    private SliderBox volume;
    private SliderBox balance;
    private SliderBox echo;
    private SliderBox bpm;
    private Player player;

    public ControlPane(Player player) {
        playButton = new Button("Play");
        volume = new SliderBox("volume", player,100,0);
        balance = new SliderBox("balance", player, 100, 0);
        echo = new SliderBox("echo", player, 100, 0);
        bpm = new SliderBox("bpm", player, 3600, 120);
        this.player = player;

        this.setStyle("-fx-background-color: red");
        this.setSpacing(30);

        bpm.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setBpm(newValue.floatValue());
            }
        });

        this.getChildren().addAll(playButton, volume, balance, echo, bpm);
    }
}
