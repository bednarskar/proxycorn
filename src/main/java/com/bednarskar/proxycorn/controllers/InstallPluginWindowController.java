package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.menu.configurator.ConfigurePluginsMenu;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.pluginresolver.PluginState;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
@Getter
@Setter
public class InstallPluginWindowController {
	final FileChooser fileChooser = new FileChooser();
	final static org.apache.log4j.Logger LOGGER = Logger.getLogger(InstallPluginWindowController.class);

	@FXML
	public ScrollPane scrollPaneLoadPlugins;

	@FXML
	private Button choosePluginButton;
	@FXML
	private GridPane installPluginAnchor;

	@FXML
	private VBox installPluginWindow;

	@FXML
	public void initialize() throws Exception {
	}

	public void choosePlugin() throws Exception {
		File f = new File(DynamicStyles.PLUGINS_PATH);
		if (!(f.exists() && f.isDirectory())) {
			LOGGER.info("Directory " + DynamicStyles.PLUGINS_PATH + " not exists. creating directory. ");
			boolean status = new File(DynamicStyles.PLUGINS_PATH).mkdirs();
		}
		File file = fileChooser.showOpenDialog(null);
		FileChannel sourceChannel = null;
		FileChannel destChannel = null;
		if (file != null) {
			try {
				LOGGER.info("Started copying file " + file.getName() + " to plugins repository.");
				sourceChannel = new FileInputStream(file.getAbsoluteFile()).getChannel();
				destChannel = new FileOutputStream(DynamicStyles.PLUGINS_PATH + file.getName()).getChannel();
				destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
//				MainController controller = ProxyCorn.mainLoader.getController();
//				ConfigurePluginsMenu configurePluginsMenu = new ConfigurePluginsMenu();
//				MainController mainController = new MainController();
//
//				configurePluginsMenu.loadConfigurePluginsMenu(controller.getMenuBar(), mainController.getStageLoadPlugin());
				PluginResolver.getInstance().reloadPlugins();

			} finally {
				if(sourceChannel != null) {
					sourceChannel.close();
				}
				if (destChannel != null) {
					destChannel.close();
				}
			}
		}
	}


}
