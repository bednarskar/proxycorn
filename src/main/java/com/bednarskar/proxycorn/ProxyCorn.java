package com.bednarskar.proxycorn;


import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.application.Application;
import javafx.application.HostServices;
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
    public static FXMLLoader loaderInstallPluginScene;
    public static FXMLLoader loaderAboutScene;
    public static FXMLLoader loaderConfigScene;


    @Override
    public void start(Stage stage) throws Exception {
        Map<String, ProxyCornPlugin> mapOfPlugins = PluginResolver.getInstance().getPlugins();
        LOGGER.debug("Number of plugins loaded: " + mapOfPlugins.size());
        mainLoader = new FXMLLoader(getClass().getResource(ProjectConstants.MAIN_SCENE));
        mainLoader.setRoot(new VBox());

        loaderPortScene= new FXMLLoader(getClass().getResource(ProjectConstants.PORT_LABELS_SCENE));
        loaderFilterScene= new FXMLLoader(getClass().getResource(ProjectConstants.SAVE_FILTER_SCENE));
        loaderLoadFilterScene = new FXMLLoader(getClass().getResource(ProjectConstants.LOAD_FILTER_SCENE));
        loaderPluginScene = new FXMLLoader(getClass().getResource(ProjectConstants.LOAD_PLUGIN_SCENE));
        loaderInstallPluginScene = new FXMLLoader(getClass().getResource(ProjectConstants.INSTALL_PLUGIN_SCENE));
        loaderAboutScene = new FXMLLoader(getClass().getResource(ProjectConstants.ABOUT_SCENE));
        loaderConfigScene = new FXMLLoader(getClass().getResource(ProjectConstants.CREDITS_SCENE));

        Parent mainBox = (Pane) mainLoader.load();
        Scene mainScene = new Scene(mainBox);
        stage.setScene(mainScene);
        stage.setTitle(ProjectConstants.APPLICATION_NAME);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public HostServices getWebServices() {
        HostServices hostServices;
        hostServices = super.getHostServices();
        return hostServices;
    }

}