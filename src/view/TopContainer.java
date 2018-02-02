package view;

import java.io.File;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import Helper.FileIO;
import model.Player;

/**
 * Represents a TopContainer
 * @author Nick Hafkemeyer, Jakob Kohlhas, Paul Schade
 * 
 */
public class TopContainer extends HBox {

	private MenuBar menuBar;
	private Menu fileMenu;
	private MenuItem openMenuItem;
	private MenuItem saveMenuItem;
	
	/**
	 * Creates an instance of TopContainer
	 * @param player
	 * @param fileIO
	 */
	public TopContainer(Player player, FileIO fileIO) {
		this.setWidth(Double.MAX_VALUE);
		
		menuBar = new MenuBar();
		HBox.setHgrow(menuBar, Priority.ALWAYS);
		
		// OpenMenuItem
		fileMenu = new Menu("File");
		openMenuItem = new MenuItem("Open Preset..", new ImageView(new Image("resources/preset.png")));
		openMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Open Preset");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
				File file = fileChooser.showOpenDialog(null);
				if (file != null) {
					fileIO.openFile(file, player);
				}
			}
		});
		
		// SaveMenuItem
		saveMenuItem = new MenuItem("Save Preset..", new ImageView(new Image("resources/save.png")));	
		saveMenuItem.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Preset");
				fileChooser.getExtensionFilters().add(new ExtensionFilter("Text File", "*.txt"));
				File file = fileChooser.showSaveDialog(null);
				if (file != null) {
					fileIO.saveFile(file, player);
				}
			}
		});
		
		fileMenu.getItems().addAll(openMenuItem, saveMenuItem);
		menuBar.getMenus().add(fileMenu);
		
		this.getChildren().add(menuBar);
	}
	
	
}
