package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class CreditsMenu {
    final static Logger LOGGER = Logger.getLogger(CreditsMenu.class);

    public void loadCreditsMenu(MenuBar menuBar, Stage stageCreditsWindow) {
        menuBar.getMenus().get(1).getItems().get(1).setOnAction(event -> {
            Parent vBox = null;
            try {
                vBox = FXMLLoader.load(getClass().getResource(ProjectConstants.LOAD_FILTER_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + ProjectConstants.LOAD_FILTER_SCENE, e);
            }
            assert vBox != null;
            Scene creditsScene = new Scene(vBox);
            stageCreditsWindow.setScene(creditsScene);
            stageCreditsWindow.toFront();
            stageCreditsWindow.show();
        });
    }

}
