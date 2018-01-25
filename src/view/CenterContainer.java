package view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import model.Player;

public class CenterContainer extends VBox {

    private SequencePane sequencePane;
    private ControlPane controlPane;
    private Player player;

    public CenterContainer(Player p) {
        player = p;
        sequencePane = new SequencePane(5,8, player);
        controlPane = new ControlPane(player);

        this.setStyle("-fx-background-color: green");
        sequencePane.setAlignment(Pos.BASELINE_CENTER);
        controlPane.setAlignment(Pos.BASELINE_CENTER);

        this.setSpacing(30);

        this.getChildren().addAll(sequencePane, controlPane);
    }
}
