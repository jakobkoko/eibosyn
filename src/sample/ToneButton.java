package sample;

import javafx.scene.control.Button;

public class ToneButton extends Button {

    private String tone;

    public ToneButton(String tone) {
        this.tone = tone;
        this.setText(tone);
    }

    public String getTone() {
        return tone;
    }
}
