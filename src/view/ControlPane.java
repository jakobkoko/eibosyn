package view;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import model.*;


public class ControlPane extends HBox {

    private Button playButton;
    private Button recordButton;
    private SliderBox volume;
    private SliderBox balance;
    private SliderBox echo;
    private SliderBox bpm;
    private SliderBox beattype;
    private Recorder recorder;
    private Player player;
    final ObjectProperty<Color> recordButtonColor = new SimpleObjectProperty<>(Color.RED);
    final StringProperty colorStringProperty = createRecordButtonColorStringProperty(recordButtonColor);

    public ControlPane(Player player, Recorder recorder) {
        playButton = new ImageButton();
        playButton.setId("playbutton");
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
        this.recorder = recorder;

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

        this.getChildren().addAll(createRecordButton(recordButtonColor, colorStringProperty), playButton, volume, balance, echo, bpm, beattype);
    }

    private StringProperty createRecordButtonColorStringProperty(final ObjectProperty<Color> recordButtonColor) {
        final StringProperty colorStringProperty = new SimpleStringProperty();
        setColorStringFromColor(colorStringProperty, recordButtonColor);
        recordButtonColor.addListener(new ChangeListener<Color>() {
            @Override
            public void changed(ObservableValue<? extends Color> observableValue, Color oldColor, Color newColor) {
                setColorStringFromColor(colorStringProperty, recordButtonColor);
            }


        });

        return colorStringProperty;
    }

    private Button createRecordButton(ObjectProperty<Color> recordButtonColor, StringProperty colorStringProperty) {


        Button recordbutton = new Button();
        recordbutton.setId("recordbutton");
        Timeline flash = new Timeline();
        KeyValue kv = new KeyValue(recordButtonColor, Color.valueOf("ff7e74"));
        KeyFrame kf = new KeyFrame(Duration.millis(300), kv);
        flash.getKeyFrames().add(kf);

        recordbutton.styleProperty().bind(
                new SimpleStringProperty("-fx-background-color:")
                    .concat(colorStringProperty).concat(";")
        );

        recordbutton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(flash.getStatus().equals(Animation.Status.RUNNING)) {
                    flash.stop();
                } else {
                    flash.setCycleCount(Timeline.INDEFINITE);
                    flash.setAutoReverse(true);
                    flash.play();
                }
                recorder.toggleRecord();
            }
        });

        return recordbutton;
    }

    private void setColorStringFromColor(StringProperty colorStringProperty, ObjectProperty<Color> color) {

        colorStringProperty.set(
                "rgba("
                        + ((int) (color.get().getRed() * 255)) + ","
                        + ((int) (color.get().getGreen() * 255)) + ","
                        + ((int) (color.get().getBlue() * 255)) + ","
                        + color.get().getOpacity() +
                ")"
        );

    }
}
