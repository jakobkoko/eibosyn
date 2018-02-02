package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.EffectType;

/**
 * Represents an EffectButton
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class EffectButton extends Button {

    private EffectType effectType;

    /**
     * Creates an instance of EffectButton
     * @param effectType
     */
    public EffectButton(EffectType effectType) {
//      this.setGraphic();
        this.effectType = effectType;
    }
    
    /**
     * Gets the effectType
     * @return
     */
    public EffectType getEffectType() {
        return effectType;
    }
}
