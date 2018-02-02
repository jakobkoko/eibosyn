package view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import model.*;

/**
 * Represents a CenterContainer
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class CenterContainer extends VBox {

    private SequencePane sequencePane;
    private ControlPane controlPane;
    private Player player;
    private Recorder recorder;

    /**
     * Creates an instance of CenterContainer
     * @param p
     * @param r
     */
    public CenterContainer(Player p, Recorder r) {

        player = p;
        recorder = r;
        sequencePane = new SequencePane(5,8, player);
        controlPane = new ControlPane(player, recorder);

        sequencePane.setAlignment(Pos.BASELINE_CENTER);
        controlPane.setAlignment(Pos.BASELINE_CENTER);

        sequencePane.setId("sequencePane");
        controlPane.setId("controlPane");

        this.getChildren().addAll(sequencePane, controlPane);
    }
    
    /**
     * Gets the ControlPane
     * @return
     */
    public ControlPane getControlPane() {
        return controlPane;
    }
    
    /**
     * Gets the SequencePane
     * @return
     */
    public SequencePane getSequencePane() {
    	return this.sequencePane;
    }
}
