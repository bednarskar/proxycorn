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

public class ConfigurePluginsMenu {
    final static Logger LOGGER = Logger.getLogger(ConfigurePluginsMenu.class);
    private Stage stagePluginsWindow;
    private Parent parentPlugins;

    public void loadConfigurePluginsMenu (MenuBar menuBar) {
        menuBar.getMenus().get(0).getItems().get(2).setOnAction(event -> {
            if (stagePluginsWindow  == null ) {
                stagePluginsWindow = new Stage();
                ProxyCorn.loaderPluginScene.setRoot(new VBox());
                try {
                    parentPlugins = (VBox) ProxyCorn.loaderPluginScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene mainPluginScene = new Scene(parentPlugins);
                mainPluginScene.setFill(Paint.valueOf(DynamicStyles.DEFAULT_LIGHT_BG_COL));
                stagePluginsWindow.setScene(mainPluginScene);
                stagePluginsWindow.setAlwaysOnTop(true);
                stagePluginsWindow.setResizable(false);
                stagePluginsWindow.setMinHeight(400);
                stagePluginsWindow.setMinWidth(700);
                stagePluginsWindow.toFront();
                stagePluginsWindow.setTitle(DynamicStyles.SAVE_FILTER);
            }
            stagePluginsWindow.show();
        });
    }

}
