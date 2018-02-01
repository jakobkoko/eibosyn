package view;

import com.sun.javafx.font.freetype.HBGlyphLayout;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import model.Player;
import model.Recorder;

public class ControlPane extends HBox {

    private Button playButton;
    private Button recordButton;
    private SliderBox volume;
    private SliderBox balance;
    private SliderBox echo;
    private SliderBox bpm;
    private SliderBox beattype;
    private Player player;

    public ControlPane(Player player, Recorder recorder) {
        playButton = new ImageButton();
        playButton.setId("playbutton");
        recordButton = new Button();
        recordButton.setId("recordbutton");
        volume = new SliderBox("volume", player,14,-80, player.getOut().getGain());
        volume.setId("volumeslider");
        balance = new SliderBox("balance", player, 1, -1);
        balance.setId("balanceslider");
        echo = new SliderBox("echo", player, 100, 0);
        echo.setId("echoslider");
        bpm = new SliderBox("bpm", player, 2400, 120);
        bpm.setId("bpmslider");
        beattype = new SliderBox("beattype", player, 5, 0);
        beattype.setId("beattypeslider");
        this.player = player;

        this.setSpacing(30);

        bpm.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setBpm(newValue.floatValue());
            }
        });

        balance.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setBalance(newValue.floatValue());
            }
        });

        beattype.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.setBeattype(newValue.intValue());
            }
        });

        volume.getSlider().valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                player.getOut().setGain(newValue.floatValue());
            }
        });

        playButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                player.play();
            }
        });

        recordButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(recordButton.getId().equals("activerecordbutton")) {
                    recordButton.setId("recordbutton");
                } else {
                    recordButton.setId("activerecordbutton");
                }
                recorder.toggleRecord();
            }
        });

        this.getChildren().addAll(recordButton, playButton, volume, balance, echo, bpm, beattype);
    }
}
