package model;

import java.util.ArrayList;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import view.ToneButton;
import view.EffectButton;
import view.ToneButton;

public class ToneCol extends VBox {

    private ArrayList<ToneButton> toneButtons;
    private ArrayList<EffectButton> effectButtons;
    private final String[] tones = {"B5", "A#5", "A5", "G#5", "G5", "F#5", "F5", "E5", "D#5", "D5", "C#5", "C5"};
    private SimpleObjectProperty<Tone> activeTone_;
    private ToneButton activeTone;
    private VBox toneSelector;
    private GridPane effectSelector;
    private ArrayList<EffectButton> activeEffects;
    private String curTone;
    private Player player;
    /* Index der ToneCol im SequencePane */
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
                selectButton((ToneButton) event.getSource());
                this.player.getToneFromToneList(index).unmute();
                this.player.setToneList(this.index, activeTone.getTone());
            } else {
                this.player.getToneFromToneList(index).mute();
                activeTone = new ToneButton("E");
            }
        };

        this.player.getToneList().getTones().addListener(new ListChangeListener<Tone>() {
            @Override
            public void onChanged(Change<? extends Tone> c) {

            }
        });

        player.getLooper().getCurToneIndex().addListener(new ChangeListener<Number>() {
            public String oldId = "col0";

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                int value = newValue.intValue();
            	if(value == index) {
                    this.oldId = ToneCol.this.getId();
                  
                    ToneCol.this.setId("activeCol");
                } else if(oldValue.intValue() == index) {
                    ToneCol.this.setId(oldId);
                }
            }
        });

        EventHandler<MouseEvent> effectButtonListener = (EventHandler<MouseEvent>) event -> {
            EffectButton source = (EffectButton) event.getSource();
            if(activeEffects.contains(source)) {
                activeEffects.remove(source);
                source.setId("inactiveEffect");
            } else {
                activeEffects.add(source);
                source.setId("activeEffect");
            }
            this.player.getToneFromToneList(index).switchFilters(source.getEffectType());
        };

        // Instantiate ToneButtons
        for (String tone : tones) {
            ToneButton toneButton = new ToneButton(tone);
            toneButton.setPrefWidth(99);
            this.toneButtons.add(toneButton);
            toneButton.setPrefHeight(33);
            this.toneSelector.getChildren().add(toneButton);
            toneButton.setOnMouseClicked(buttonListener);
        }

        EffectButton lowPassButton = new EffectButton(EffectType.LOWPASS);
        effectButtons.add(lowPassButton);
        EffectButton highPassButton = new EffectButton(EffectType.HIGHPASS);
        effectButtons.add(highPassButton);
        EffectButton bitCrushButton = new EffectButton(EffectType.BITCRUSH);
        effectButtons.add(bitCrushButton);
        
        // Instantiate EffectButtons
        for(int i = 0; i < 3; i++) {
        	EffectButton e = effectButtons.get(i);
        	e.getStyleClass().add("effectButton" + e.getEffectType().toString());
        	e.setId("effectButton" + e.getEffectType().toString());
            e.setPrefWidth(33);
            e.setOnMouseClicked(effectButtonListener);
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
//            if(colIndex == 3) {
//                rowIndex++;
//                colIndex = 0;
//            }
        }
        effectSelector.setAlignment(Pos.CENTER);

        this.getChildren().addAll(toneSelector, effectSelector);

    }
    
    public void selectButton(ToneButton button) {
    	activeTone = button;
        activeTone.setId("active");
    }
    
    public ArrayList<ToneButton> getToneButtons() {
    	return toneButtons;
    }

}
