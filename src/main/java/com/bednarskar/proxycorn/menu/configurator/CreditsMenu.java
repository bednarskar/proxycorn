package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.DynamicStyles;
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
                vBox = FXMLLoader.load(getClass().getResource(DynamicStyles.CREDITS_SCENE));
            } catch(IOException e) {
                LOGGER.error("Cannot load view " + DynamicStyles.CREDITS_SCENE, e);
            }
            assert vBox != null;
            Scene creditsScene = new Scene(vBox);
            stageCreditsWindow.setScene(creditsScene);
            stageCreditsWindow.setTitle(DynamicStyles.CREDITS);
            stageCreditsWindow.show();
        });
    }

}
