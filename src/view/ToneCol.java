package view;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class ToneCol extends VBox {

    private ArrayList<ToneButton> toneButtons;
    private ArrayList<EffectButton> effectButtons;
    private final String[] tones = {"C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "B", "H"};
    private ToneButton activeTone;
    private VBox toneSelector;
    private GridPane effectSelector;
    private ArrayList<EffectButton> activeEffects;

    public ToneCol() {

        toneButtons = new ArrayList<>();
        activeTone = new ToneButton("E");
        effectButtons = new ArrayList<>();
        toneSelector = new VBox();
        effectSelector = new GridPane();
        activeEffects = new ArrayList<>();
        this.setPrefWidth(99);

        EventHandler<MouseEvent> buttonListener = (EventHandler<MouseEvent>) event -> {
            activeTone.setId("inactive");
            if(event.getSource() != activeTone) {
                activeTone = (ToneButton) event.getSource();
                activeTone.setId("active");
                System.out.println(activeTone.getTone());
            } else {
                activeTone = new ToneButton("E");
            }
        };

        EventHandler<MouseEvent> effectButtonListener = (EventHandler<MouseEvent>) event -> {
            EffectButton source = (EffectButton) event.getSource();
            if(activeEffects.contains(event.getSource())) {
                activeEffects.remove(event.getSource());
                source.setId("inactiveEffect");
            } else {
                activeEffects.add((EffectButton) event.getSource());
                source.setId("activeEffect");
            }

        };

        for (String tone : tones) {
            ToneButton toneButton = new ToneButton(tone);
            toneButton.setPrefWidth(99);
            this.toneButtons.add(toneButton);
            toneButton.setPrefHeight(33);
            this.toneSelector.getChildren().add(toneButton);
            toneButton.setOnMouseClicked(buttonListener);
        }

        for(int i = 0; i<6; i+=1) {
            EffectButton btn = new EffectButton();
            effectButtons.add(btn);
            btn.setPrefWidth(33);
            btn.setOnMouseClicked(effectButtonListener);
        }

        ColumnConstraints col1 = new ColumnConstraints();
        ColumnConstraints col2 = new ColumnConstraints();
        ColumnConstraints col3 = new ColumnConstraints();
        effectSelector.getColumnConstraints().addAll(col1, col2, col3);
        int colIndex = 0;
        int rowIndex = 0;
        for(EffectButton b: effectButtons) {
            effectSelector.add(b, colIndex, rowIndex);
            colIndex++;
            if(colIndex == 3) {
                rowIndex++;
                colIndex = 0;
            }
        }
        effectSelector.setAlignment(Pos.CENTER);

        this.getChildren().addAll(toneSelector, effectSelector);




    }

}
