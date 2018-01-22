package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

public class SliderBox extends VBox {

    private Slider slider;
    private Label label;

    public SliderBox(String text) {
        slider = new Slider();
        label = new Label(text);

        slider.setShowTickLabels(true);
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if(label.getText().equalsIgnoreCase("jakob")) {
                    if (n < 1) return "L";
                    else if (n > 99) return "R";
                } else {
                    if(n < 1) return "0";
                    else if( n > 99) return "100";
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
}
