package view;

import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

/**
 * Represents an ImageButton
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class ImageButton extends Button {
	
	ImageView imgView;
	ImageView imgView2;
	
	/**
	 * Creates an instance of an ImageButton
	 */
	public ImageButton() {
		imgView = new ImageView();
		imgView.setId("imgView");
		this.setGraphic(imgView);
	}

}
