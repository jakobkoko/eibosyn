package view;

import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class CenterContainer extends VBox {

    private SequencePane sequencePane;
    private ControlPane controlPane;

    public CenterContainer() {
        sequencePane = new SequencePane(5,8);
        controlPane = new ControlPane();

        this.setStyle("-fx-background-color: green");
        sequencePane.setAlignment(Pos.BASELINE_CENTER);
        controlPane.setAlignment(Pos.BASELINE_CENTER);

        this.setSpacing(30);

        this.getChildren().addAll(sequencePane, controlPane);
    }
}
