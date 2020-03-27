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

import java.io.IOException;

public final class ConfigureMenu {
    final static Logger LOGGER = Logger.getLogger(ConfigureMenu.class);

    private static Stage stageFiltersWindow;
    private static Parent parentFilters;


    public void loadConfigureMenu (MenuBar menuBar) {
        menuBar.getMenus().get(0).getItems().get(0).setOnAction(event -> {
            if (stageFiltersWindow  == null ) {
                stageFiltersWindow = new Stage();
                ProxyCorn.loaderFilterScene.setRoot(new VBox());
                try {
                    parentFilters = (VBox) ProxyCorn.loaderFilterScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene mainFilterScene = new Scene(parentFilters);
                mainFilterScene.setFill(Paint.valueOf(DynamicStyles.DEFAULT_LIGHT_BG_COL));
                stageFiltersWindow.setScene(mainFilterScene);
                stageFiltersWindow.setAlwaysOnTop(true);
                stageFiltersWindow.toFront();
                stageFiltersWindow.setTitle(DynamicStyles.SAVE_FILTER);
            }
            stageFiltersWindow.show();
        });
    }
}
