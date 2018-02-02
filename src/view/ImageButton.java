package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

public class ImageButton extends Button {
	
	ImageView imgView;
	ImageView imgView2;
	
	public ImageButton() {
		imgView = new ImageView();
		imgView.setId("imgView");
		this.setGraphic(imgView);
	}

}
