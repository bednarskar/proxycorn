package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.channels.FileChannel;
@Getter
@Setter
public class InstallPluginWindowController {
	final FileChooser fileChooser = new FileChooser();
	final static org.apache.log4j.Logger LOGGER = Logger.getLogger(InstallPluginWindowController.class);

	@FXML
	private Button choosePluginButton;
	@FXML
	private GridPane installPluginAnchor;

	@FXML
	private VBox installPluginWindow;

	@FXML
	public void initialize() {
	}

	public void choosePlugin() throws Exception {
		File f = new File(ProjectConstants.PLUGINS_PATH);
		if (!(f.exists() && f.isDirectory())) {
			LOGGER.info("Directory " + ProjectConstants.PLUGINS_PATH + " not exists. creating directory. ");
			boolean status = new File(ProjectConstants.PLUGINS_PATH).mkdirs();
		}
		File file = fileChooser.showOpenDialog(null);

		if (file != null) {
				installPluginAnchor.add(new Label("Do you want to install file: " + file.getName()), 0, 1);
				Button button = new Button("Yes, please, I love unicorns. ");
				button.setOnMouseClicked(new EventHandler<>() {
					@SneakyThrows
					@Override
					public void handle(MouseEvent event) {
						FileChannel sourceChannel;
						LOGGER.info("Started copying file " + file.getName() + " to plugins repository.");
						sourceChannel = new FileInputStream(file.getAbsoluteFile()).getChannel();
						FileChannel destChannel = new FileOutputStream(ProjectConstants.PLUGINS_PATH + file.getName()).getChannel();
						try {
							destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
							PluginResolver.getInstance().reloadPlugins();
							Button eventSource = (Button) event.getSource();
							eventSource.setVisible(false);
							eventSource.setId("installButton");
							GridPane gridPane = (GridPane) eventSource.getParent();
							gridPane.add(new Label("Plugin installed."), 0,3);
							Button exitButton = new Button("OK");
							exitButton.setId("exitButton");
							exitButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
								@Override
								public void handle(MouseEvent event) {
									Button exit = (Button ) event.getSource();
									exit.getScene().getWindow().fireEvent(new Event(WindowEvent.WINDOW_CLOSE_REQUEST));
								}
							});
							gridPane.add(exitButton, 0,4);
						} catch(IOException e) {
							LOGGER.error("Problem occured during installing plugin. ", e);
						} finally {
							if(sourceChannel != null) {
								sourceChannel.close();
							}
							destChannel.close();
						}
					}
				});
				installPluginAnchor.add(button, 0, 2);
		}
	}


}
