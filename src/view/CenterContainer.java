package view;

import javafx.scene.layout.VBox;

public class CenterContainer extends VBox {

    private SequencePane sequencePane;
    private ControlPane controlPane;

    public CenterContainer() {
        sequencePane = new SequencePane(5,8);
        controlPane = new ControlPane();


        this.getChildren().addAll(sequencePane, controlPane);
    }
}
