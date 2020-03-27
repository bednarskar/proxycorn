package com.bednarskar.proxycorn;


import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.util.Map;


public class ProxyCorn extends Application {
    final static Logger LOGGER = Logger.getLogger(ProxyCorn.class);

//    public static Callback<Class<?>, Object> controllerFactory;
    public static FXMLLoader loaderPortScene;
    public static FXMLLoader loaderFilterScene;
    public static FXMLLoader loaderLoadFilterScene;
    public static FXMLLoader mainLoader;
    public static FXMLLoader loaderPluginScene;

    @Override
    public void start(Stage stage) throws Exception {
        Map<String, ProxyCornPlugin> mapOfPlugins = PluginResolver.getInstance().getPlugins();
        LOGGER.debug("Number of plugins loaded: " + mapOfPlugins.size());
        mainLoader = new FXMLLoader(getClass().getResource(DynamicStyles.MAIN_SCENE));
        mainLoader.setRoot(new VBox());

        loaderPortScene= new FXMLLoader(getClass().getResource(DynamicStyles.PORT_LABELS_SCENE));
        loaderFilterScene= new FXMLLoader(getClass().getResource(DynamicStyles.SAVE_FILTER_SCENE));
        loaderLoadFilterScene = new FXMLLoader(getClass().getResource(DynamicStyles.LOAD_FILTER_SCENE));
        loaderPluginScene = new FXMLLoader(getClass().getResource(DynamicStyles.LOAD_PLUGIN_SCENE));

        Parent mainBox = (Pane) mainLoader.load();
        Scene mainScene = new Scene(mainBox);
        stage.setScene(mainScene);
        stage.setTitle(DynamicStyles.APPLICATION_NAME);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}