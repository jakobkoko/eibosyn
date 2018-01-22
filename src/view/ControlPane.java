package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;

public class ControlPane extends HBox {

    Button playButton;
    Slider volumeSlider;


    public ControlPane() {
        playButton = new Button("Play");
        volumeSlider = new Slider();
        volumeSlider.setOrientation(Orientation.VERTICAL);

        this.getChildren().addAll(playButton, volumeSlider);
    }
}
