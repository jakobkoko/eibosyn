package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class TopContainer extends HBox {

	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem openMenuItem;
	private MenuItem saveMenuItem;
	
	public TopContainer() {
		this.setWidth(Double.MAX_VALUE);
		
		menuBar = new MenuBar();
		HBox.setHgrow(menuBar, Priority.ALWAYS);
		
		fileMenu = new Menu("File");
		openMenuItem = new MenuItem("Open Preset..", new ImageView(new Image("resources/preset.png")));
		saveMenuItem = new MenuItem("Save Preset..", new ImageView(new Image("resources/save.png")));	
		
		fileMenu.getItems().addAll(openMenuItem, saveMenuItem);
		menuBar.getMenus().add(fileMenu);
		
		this.getChildren().add(menuBar);
	}
	
	
}
