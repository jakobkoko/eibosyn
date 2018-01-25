package view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Player;

import java.util.ArrayList;

public class ToneCol extends VBox {

    private ArrayList<ToneButton> toneButtons;
    private ArrayList<EffectButton> effectButtons;
    private final String[] tones = {"C5", "C#5", "D5", "D#5", "E5", "F5", "F#5", "G5", "G#5", "A5", "B5", "H5"};
    private ToneButton activeTone;
    private VBox toneSelector;
    private GridPane effectSelector;
    private ArrayList<EffectButton> activeEffects;
    private String curTone;
    private Player player;
    private int index;
    private boolean active;

    public ToneCol(int index, Player player) {
        this.player = player;
        this.index = index;
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
                this.player.setToneList(this.index, activeTone.getTone());
                System.out.println(activeTone.getTone());
            } else {
                activeTone = new ToneButton("E");
            }
        };

        player.getLooper().getCurToneIndex().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.intValue() == index) {
                    ToneCol.this.setId("activeCol");
                } else if(oldValue.intValue() == index) {
                    ToneCol.this.setId("inactiveCol");
                }
            }
        });


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
