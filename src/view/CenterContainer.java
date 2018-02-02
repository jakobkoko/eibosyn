package view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import model.*;

public class CenterContainer extends VBox {

    private SequencePane sequencePane;
    private ControlPane controlPane;
    private Player player;
    private Recorder recorder;

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

    public ControlPane getControlPane() {
        return controlPane;
    }
    
    public SequencePane getSequencePane() {
    	return this.sequencePane;
    }
}
