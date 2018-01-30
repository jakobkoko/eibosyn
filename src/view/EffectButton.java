package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import model.EffectType;

public class EffectButton extends Button {

    private EffectType effectType;
    private ImageView imgView;

    public EffectButton(EffectType effectType) {
//      this.setGraphic();
        this.effectType = effectType;
        imgView = new ImageView();
        
        this.getChildren().add(imgView);
    }

    public EffectType getEffectType() {
        return effectType;
    }
}
