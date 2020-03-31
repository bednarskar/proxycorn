package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
@Getter
@Setter
public class InstallPluginWindowController {
	final FileChooser fileChooser = new FileChooser();
	final static org.apache.log4j.Logger LOGGER = Logger.getLogger(InstallPluginWindowController.class);

	@FXML
	public Label fileNameLabel;

	@FXML
	public Button confirmInstallButton;

	@FXML
	public Label installationCompleteLabel;

	@FXML
	public Button exitButton;

	@FXML
	private Button choosePluginButton;

	@FXML
	private GridPane installPluginAnchor;

	@FXML
	private VBox installPluginWindow;

	@FXML
	private Label confirmLabel;

	private File file;

	@FXML
	public void initialize() {
	}

	public void choosePlugin() {
		setLabelsAndButtonsInvisible();
		createPluginsDir();
		file = fileChooser.showOpenDialog(null);
		if (file != null) {
				setVisible(confirmLabel);
				fileNameLabel.setText(file.getName() + "  ?");
				setVisible(fileNameLabel);
				setVisible(confirmInstallButton);
		}
	}

	private void setVisible(Label label) {
		label.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 12));
		label.setVisible(true);
	}
	private void setVisible(Button button) {
		button.setVisible(true);
	}
	private void setLabelsAndButtonsInvisible() {
		confirmLabel.setVisible(false);
		fileNameLabel.setVisible(false);
		confirmInstallButton.setVisible(false);
		installationCompleteLabel.setVisible(false);
		exitButton.setVisible(false);
	}

	private void createPluginsDir() {
		File f = new File(ProjectConstants.PLUGINS_PATH);

		if (!(f.exists() && f.isDirectory())) {
			LOGGER.info("Directory " + ProjectConstants.PLUGINS_PATH + " not exists. creating directory. ");
			boolean status = new File(ProjectConstants.PLUGINS_PATH).mkdirs();
		}
	}

	public void confirmAndInstallPlugin(MouseEvent event) throws Exception {
		FileChannel sourceChannel;
		LOGGER.info("Started copying file " + file.getName() + " to plugins repository.");
		sourceChannel = new FileInputStream(file.getAbsoluteFile()).getChannel();
		FileChannel destChannel = new FileOutputStream(ProjectConstants.PLUGINS_PATH + file.getName()).getChannel();
		try {
			destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
			PluginResolver.getInstance().reloadPlugins();
			Button confirmInstallationButton = (Button) event.getSource();
			confirmInstallationButton.setVisible(false);
			installationCompleteLabel.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 12));
			installationCompleteLabel.setVisible(true);
			exitButton.setVisible(true);
		} catch(IOException e) {
			LOGGER.error("Problem occured during installing plugin. ", e);
		} finally {
			if(sourceChannel != null) {
				sourceChannel.close();
			}
			destChannel.close();
		}
	}

	@FXML
	public void exit(MouseEvent event) {
		Button exit = (Button ) event.getSource();
		exit.getScene().getWindow().fireEvent(new Event(WindowEvent.WINDOW_CLOSE_REQUEST));
	}
}
