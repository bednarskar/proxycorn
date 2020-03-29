package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.DynamicStyles;
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
                vBox = FXMLLoader.load(getClass().getResource(DynamicStyles.INSTALL_PLUGIN_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + DynamicStyles.INSTALL_PLUGIN_SCENE, e);
            }
            assert vBox != null;
            Scene installPluginScene = new Scene(vBox);
            stageInstallPluginsWindow.setScene(installPluginScene);
            stageInstallPluginsWindow.setTitle(DynamicStyles.INSTALL_PLUGIN);
            stageInstallPluginsWindow.show();
        });
    }

}
