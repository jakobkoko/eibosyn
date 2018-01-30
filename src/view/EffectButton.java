package view;

import javafx.scene.control.Button;

public class EffectButton extends Button {

    private int effectNumber;

    public EffectButton(int i) {
        this.setText("FX");
        this.effectNumber = i;
    }

    public int getEffectNumber() {
        return effectNumber;
    }
}
