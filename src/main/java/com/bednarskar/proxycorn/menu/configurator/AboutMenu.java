package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AboutMenu {
    final static Logger LOGGER = Logger.getLogger(AboutMenu.class);

    public void loadAboutMenu(MenuBar menuBar, Stage stageInstallPluginsWindow) {
        menuBar.getMenus().get(1).getItems().get(0).setOnAction(event -> {
            Parent vBox = null;
            try {
                vBox = FXMLLoader.load(getClass().getResource(DynamicStyles.ABOUT_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + DynamicStyles.ABOUT_SCENE, e);
            }
            assert vBox != null;
            Scene aboutScene = new Scene(vBox);
            stageInstallPluginsWindow.setScene(aboutScene);
            stageInstallPluginsWindow.setTitle(DynamicStyles.ABOUT);
            stageInstallPluginsWindow.show();
        });
    }

}
