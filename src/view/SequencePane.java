package view;

import javafx.scene.control.Label;
import javafx.scene.layout.*;

import java.util.ArrayList;

public class SequencePane extends HBox {

    private ArrayList<ToneCol> toneSequence;
    private int octave;
    private ArrayList<Label> toneNames;
    private VBox toneNamesBox;

    public SequencePane(int octave, int toneRowNumber) {
        this.octave = octave;
        toneSequence = new ArrayList<>();
        toneNames = new ArrayList<>();
        toneNamesBox = new VBox();
        toneNames.add(new Label("C"));
        toneNames.add(new Label("C#"));
        toneNames.add(new Label("D"));
        toneNames.add(new Label("D#"));
        toneNames.add(new Label("E"));
        toneNames.add(new Label("F"));
        toneNames.add(new Label("F#"));
        toneNames.add(new Label("G"));
        toneNames.add(new Label("G#"));
        toneNames.add(new Label("A"));
        toneNames.add(new Label("A#"));
        toneNames.add(new Label("H"));

        for(Label toneLabel : toneNames) {
            toneNamesBox.getChildren().addAll(toneLabel);
            toneLabel.setPrefHeight(33);
        }
        this.getChildren().add(toneNamesBox);

        for (int i = 0; i < toneRowNumber; i += 1) {
            toneSequence.add(new ToneCol());
            this.getChildren().add(new ToneCol());
        }

    }

    // getoctave
    // setoctave
    // toneSequence zusammenbauen

}
