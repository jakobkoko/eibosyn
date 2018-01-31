package view;

import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.Player;

public class SliderBox extends VBox {

    private Slider slider;
    private Label label;
    private ImageView imgView;
    private Player player;
    private int max;
    private int min;
    private float cur;

    public SliderBox(String text, Player player, int max, int min) {
        this.max = max;
        this.min = min;
        slider = new Slider();
        slider.setId("slider");
        label = new Label(text);
        label.setId("label");
        imgView = new ImageView();
        imgView.setFitWidth(12);
        imgView.setFitHeight(15);
        imgView.setId("imageView");
        
        slider.setMax(max);
        slider.setMin(min);
        slider.setValue(min);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double n) {
                if(label.getText().equalsIgnoreCase("balance")) {
                    if (n == -1) return "L";
                    if (n == 1) return "R";
                } else if(label.getText().equalsIgnoreCase("beattype")) {
                    slider.setSnapToTicks(true);
                    slider.setMajorTickUnit(5);
                    if (n == 0) return "1/1";
                    if (n == 1) return "1/2";
                    if (n == 2) return "1/4";
                    if (n == 3) return "1/8";
                    if (n == 4) return "1/16";
                    if (n == 5) return "1/32";
                } else if(label.getText().equalsIgnoreCase("bpm")) {
                    slider.setMajorTickUnit(100);
                    if (n == 2400) return "2400";
                    if (n == 1200) return "1200";
                    if (n == 120) return "120";
                } else {
                    if(n < 1) return "0";
                    else if( n > 99) return String.valueOf(n);
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
        this.getChildren().addAll(slider, label, imgView);
    }

    public SliderBox(String text, Player player, int max, int min, float cur) {
        this(text, player, max, min);
        this.cur = cur;
        slider.setValue(cur);
    }



    public Slider getSlider() {
        return slider;
    }
}
