package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.IOException;

public final class SaveFilterMenu {
    final static Logger LOGGER = Logger.getLogger(SaveFilterMenu.class);

    public void loadConfigureMenu (MenuBar menuBar, Stage stageFiltersWindow) {
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> {
                Parent vBox = null;
                try {
                    vBox = FXMLLoader.load(getClass().getResource(ProjectConstants.SAVE_FILTER_SCENE));
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'save filter' ", e);
                }
                assert vBox != null;
                Scene mainFilterScene = new Scene(vBox);
//                mainFilterScene.setFill(Paint.valueOf(ProjectConstants.DEFAULT_LIGHT_BG_COL));
                stageFiltersWindow.setScene(mainFilterScene);
//                stageFiltersWindow.setAlwaysOnTop(true);
                stageFiltersWindow.toFront();
//                stageFiltersWindow.setTitle(ProjectConstants.SAVE_FILTER);
            stageFiltersWindow.show();
        });
    }
}
