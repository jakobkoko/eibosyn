package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.EffectType;

public class EffectButton extends Button {

    private EffectType effectType;

    public EffectButton(EffectType effectType) {
//      this.setGraphic();
        this.effectType = effectType;
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
