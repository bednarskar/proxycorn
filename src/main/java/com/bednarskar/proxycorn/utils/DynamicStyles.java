package com.bednarskar.proxycorn.utils;

import java.awt.*;

public class DynamicStyles {
    // styles
    public static final String RED_TEXT = "-fx-text-fill: #cc0000";
    public static final String NORMAL_TEXT = "-fx-text-fill: #000000";
    public static final String PINK_SHADOW = "-fx-effect: dropshadow( one-pass-box , #fc49ab , 8 , 3.0 , 2 , 1 )";
    public static final String IMG_FLAGS_URL = "flags/";
    public static final String BUTTON_COUNTRY_STYLE = "-fx-background-color: -fx-outer-border, -fx-inner-border, -fx-body-color; \n" +
            "    -fx-background-insets: 0, 1, 2;\n" +
            "    -fx-background-radius: 5, 4, 3;";
    public static final String NONE_EFFECT_STYLE = "-fx-effect: none";
    // plugins
    public static final String PLUGINS_PATH = System.getProperty("user.home") + "/proxycorn/plugins/";
    public static final String PLUGIN_PATH_ATTRIBUTE = "Plugin-Path";
    public static final String PLUGIN_NAME_ATTRIBUTE = "Plugin-Name";
    public static final String JAR = ".jar";
    // fxml
    public static final String MAIN_SCENE = "/MainScene.fxml";
    public static final String PORT_LABELS_SCENE = "/PortLabels.fxml";
    public static final String APPLICATION_NAME = "ProxyCorn";
    public static final String SAVE_FILTER_SCENE = "/SaveFilter.fxml";
    public static final String SAVE_FILTER = "Save filter";
    public static final String SAVED_FILTERS_PATH = System.getProperty("user.home") + "/proxycorn/filters/";
    public static final String SAVE_FILTER_AS = "Save filter as...";
    public static final String FILTER_SAVED = "Filter Saved";
    public static final String EMPTY = "";
    public static final String UNDERSCORE = "_";
    public static final String LOAD_FILTER = "Load filter";
    public static final String LOAD_FILTER_SCENE = "/LoadFilter.fxml";
    public static final String LOAD_PLUGIN_SCENE = "/LoadPlugin.fxml";
    public static final String INSTALL_PLUGIN_SCENE = "/InstallPlugin.fxml";
    public static final String ABOUT_SCENE = "/About.fxml";
    public static final String ABOUT = "About";
    public static final String CREDITS_SCENE = "/Credits.fxml";
    public static final String CREDITS = "Credits";
    public static final String INSTALL_PLUGIN = "Install plugin";
    public static final String SETUP_PLUGIN = "Setup plugin";
    public static final String PROPERTIES_FILE_PATH =  System.getProperty("user.home") + "/proxycorn/settings/";

    public static final String DEFAULT_LIGHT_BG_COL = "#ffe1f5";
    public static final String ALL_PNG_FILES_REGEX = ".*\\.png";
    public static final int SMALL_WINDOW_WIDTH = 700;
    public static final int SMALL_WINDOW_HEIGHT = 300;


}
