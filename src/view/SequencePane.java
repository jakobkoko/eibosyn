package view;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Player;
import model.ToneCol;

/**
 * Represents a ControlPane
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class SequencePane extends HBox {

    private ArrayList<ToneCol> toneSequence;
    private int octave;
    private ArrayList<Label> toneNames;
    private VBox toneNamesBox;
    private Player player;

    /**
     * Creates an instance of SequencePane
     * @param octave
     * @param toneRowNumber
     * @param p
     */
    public SequencePane(int octave, int toneRowNumber, Player p) {
        player = p;
        this.octave = octave;
        toneSequence = new ArrayList<>();
        toneNames = new ArrayList<>();
        toneNamesBox = new VBox();
        toneNamesBox.setId("toneNamesBox");
        toneNames.add(new Label("B"));
        toneNames.add(new Label("A#"));
        toneNames.add(new Label("A"));
        toneNames.add(new Label("G#"));
        toneNames.add(new Label("G"));
        toneNames.add(new Label("F#"));
        toneNames.add(new Label("F"));
        toneNames.add(new Label("E"));
        toneNames.add(new Label("D#"));
        toneNames.add(new Label("D"));
        toneNames.add(new Label("C#"));

        for(Label toneLabel : toneNames) {
            toneNamesBox.getChildren().addAll(toneLabel);
        }
        this.getChildren().add(toneNamesBox);

        for (int i = 0; i < toneRowNumber; i += 1) {
            toneSequence.add(new ToneCol(i, player));
            this.getChildren().add(toneSequence.get(i));
            toneSequence.get(i).setId("col"+i);
        }

    }

    /**
     * Gets toneSequence
     * @return
     */
	public ArrayList<ToneCol> getToneSequence() {
		return toneSequence;
	}

}
