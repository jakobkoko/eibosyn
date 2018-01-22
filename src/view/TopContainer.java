package view;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TopContainer {

	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem openMenuItem;
	private MenuItem saveMenuItem;
	
	public TopContainer() {
		
		menuBar = new MenuBar();
		fileMenu = new Menu();
		openMenuItem = new MenuItem("Open Preset..", new ImageView(new Image("resources/preset.png")));
		saveMenuItem = new MenuItem("Save Preset..");	
		
		fileMenu.getItems().addAll(openMenuItem, saveMenuItem);
		menuBar.getMenus().add(fileMenu);
		
		int x;
	}
	
	
}
