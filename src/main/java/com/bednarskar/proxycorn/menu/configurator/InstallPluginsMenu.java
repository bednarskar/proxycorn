package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class InstallPluginsMenu {
    final static Logger LOGGER = Logger.getLogger(InstallPluginsMenu.class);

    public void loadInstallPluginsMenu(MenuBar menuBar, Stage stageInstallPluginsWindow) {
        menuBar.getMenus().get(0).getItems().get(3).setOnAction(event -> {
            Parent vBox = null;
            try {
                vBox = FXMLLoader.load(getClass().getResource(ProjectConstants.INSTALL_PLUGIN_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + ProjectConstants.INSTALL_PLUGIN_SCENE, e);
            }
            assert vBox != null;
            Scene installPluginScene = new Scene(vBox);
            stageInstallPluginsWindow.setScene(installPluginScene);
//            stageInstallPluginsWindow.setTitle(ProjectConstants.INSTALL_PLUGIN);
//            stageInstallPluginsWindow.setResizable(false);
            stageInstallPluginsWindow.toFront();
            stageInstallPluginsWindow.show();
        });
    }

}
