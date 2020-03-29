package com.bednarskar.proxycorn.menu.configurator;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class LoadFiltersMenu {
    final static Logger LOGGER = Logger.getLogger(LoadFiltersMenu.class);

    private Parent parentLoadFilters;
    private Stage stageLoadFilterWindow;

    public void loadLoadFiltersMenu (MenuBar menuBar) {
        menuBar.getMenus().get(0).getItems().get(1).setOnAction(event -> {
            File file = new File(DynamicStyles.SAVED_FILTERS_PATH);
            if (!file.exists() || !file.isDirectory()) {
                new File(DynamicStyles.SAVED_FILTERS_PATH).mkdirs();
            }
            if (stageLoadFilterWindow  == null ) {
                stageLoadFilterWindow = new Stage();
                ProxyCorn.loaderLoadFilterScene.setRoot(new VBox());
                try {
                    parentLoadFilters = (VBox) ProxyCorn.loaderLoadFilterScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene loadFilterScene = new Scene(parentLoadFilters);
                loadFilterScene.setFill(Paint.valueOf(DynamicStyles.DEFAULT_LIGHT_BG_COL));
                stageLoadFilterWindow.setScene(loadFilterScene);
                stageLoadFilterWindow.setAlwaysOnTop(true);
                stageLoadFilterWindow.setMinWidth(700);
                stageLoadFilterWindow.setMinHeight(300);
                stageLoadFilterWindow.setResizable(false);
                stageLoadFilterWindow.toFront();
                stageLoadFilterWindow.setTitle(DynamicStyles.LOAD_FILTER);
            }
            stageLoadFilterWindow.show();
        });
    }

}
