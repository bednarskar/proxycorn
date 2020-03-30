package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class LoadFiltersMenu {

    final static Logger LOGGER = Logger.getLogger(LoadFiltersMenu.class);

    public void loadLoadFiltersMenu(MenuBar menuBar, Stage stageLoadFilterWindow) {
        menuBar.getMenus().get(0).getItems().get(1).setOnAction(event -> {
            File file = new File(ProjectConstants.SAVED_FILTERS_PATH);
            if(! file.exists() || ! file.isDirectory()) {
                new File(ProjectConstants.SAVED_FILTERS_PATH).mkdirs();
            }
            Parent vBox = null;

            try {
                vBox = FXMLLoader.load(getClass().getResource(ProjectConstants.LOAD_FILTER_SCENE));
            } catch(IOException e) {
                LOGGER.error("Could not load menu 'configure' ", e);
            }
            assert vBox != null;
            Scene loadFilterScene = new Scene(vBox);
            loadFilterScene.setFill(Paint.valueOf(ProjectConstants.DEFAULT_LIGHT_BG_COL));
            stageLoadFilterWindow.setScene(loadFilterScene);
            stageLoadFilterWindow.setAlwaysOnTop(false);
            stageLoadFilterWindow.toFront();
            stageLoadFilterWindow.setTitle(ProjectConstants.LOAD_FILTER);

            stageLoadFilterWindow.show();
        });
    }

}
