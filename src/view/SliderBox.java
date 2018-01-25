package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Player;

public class SliderBox extends VBox {

    private Slider slider;
    private Label label;
    private Player player;
    private int max;
    private int min;

    public SliderBox(String text, Player player, int max, int min) {
        this.max = max;
        this.min = min;
        slider = new Slider();
        label = new Label(text);
        slider.setMax(max);
        slider.setMin(min);
        slider.setValue(min);
        slider.setShowTickLabels(true);
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if(label.getText().equalsIgnoreCase("balance")) {
                    if (n < 0) return "L";
                    else if (n > 0) return "R";
                } else {
                    if(n < 1) return "0";
                    else if( n > 99) return String.valueOf(n+1);
                }
                return null;
            }

            @Override
            public Double fromString(String string) {
                return null;
            }

        });

        slider.setOrientation(Orientation.VERTICAL);
        this.setAlignment(Pos.CENTER);
        this.getChildren().addAll(slider, label);
    }

    public Slider getSlider() {
        return slider;
    }
}
