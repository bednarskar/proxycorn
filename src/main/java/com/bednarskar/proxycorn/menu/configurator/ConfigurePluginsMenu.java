package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ConfigurePluginsMenu {
    final static Logger LOGGER = Logger.getLogger(ConfigurePluginsMenu.class);

    public void loadConfigurePluginsMenu (MenuBar menuBar, Stage stagePluginsWindow) {
        menuBar.getMenus().get(0).getItems().get(2).setOnAction(event -> {
            Parent vBox = null;
            try {
                vBox = FXMLLoader.load(getClass().getResource(ProjectConstants.LOAD_PLUGIN_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + ProjectConstants.LOAD_PLUGIN_SCENE, e);
            }

            assert vBox != null;
            Scene mainPluginScene = new Scene(vBox);
            stagePluginsWindow.setScene(mainPluginScene);
            stagePluginsWindow.toFront();
            stagePluginsWindow.show();
        });
    }

}
