package view;

import javafx.scene.control.Button;

/**
 * Represents a ToneButton
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class ToneButton extends Button {

    private String tone;

    /**
     * Creates an instance of ToneButton
     * @param tone
     */
    public ToneButton(String tone) {
        this.tone = tone;
        this.setText(tone);
    }

    /**
     * Returns the tone of this Button as a String
     * @return
     */
    public String getTone() {
        return tone;
    }
}
