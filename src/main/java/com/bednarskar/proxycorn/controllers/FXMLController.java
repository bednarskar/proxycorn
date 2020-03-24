package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.models.CountryButton;
import com.bednarskar.proxycorn.models.Filter;
import com.bednarskar.proxycorn.models.FilterCheckBox;
import com.bednarskar.proxycorn.models.PortNumberField;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.*;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

@Getter
@Setter
public class FXMLController {

    final static Logger LOGGER = Logger.getLogger(FXMLController.class);

    public static final String ALL_PNG_FILES_REGEX = ".*\\.png";

    @FXML
    PortLabelsController portLabelsController;

    public FXMLController () throws IOException {
        portLabelsController = new PortLabelsController();
        this.filter = Filter.getInstance();
        this.rowIndex = 0;
        this.columnIndex = 0;
    }

    private Filter filter;
    private int rowIndex;
    private int columnIndex;

    @FXML
    private Label label;

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox GobblerProxy;

    @FXML
    private MenuBar ProxyGobbler;

    @FXML
    private Group optionsGroup;

    @FXML
    private FilterCheckBox https;

    @FXML
    private FilterCheckBox http;

    @FXML
    private FilterCheckBox socks4;

    @FXML
    private FilterCheckBox socks5;

    @FXML
    private Button getButton;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ScrollPane choosenPorts;

    @FXML
    private Button addPortButton;

    @FXML
    private PortNumberField portNumbers;

    @FXML
    private GridPane buttons;

    @FXML
    private GridPane labelsForPort;

    final int[] i = {0};
    private Parent parentFilters;
    private Parent parentLoadFilters;
    private Parent parentPlugins;

    private Stage stageFiltersWindow;
    private Stage stageLoadFilterWindow;
    private Stage stagePluginsWindow;

    @FXML
    void initialize () throws IOException {
        assert GobblerProxy != null : "fx:id=\"GobblerProxy\" was not injected: check your FXML file 'GoblerScene'.";
        assert ProxyGobbler != null : "fx:id=\"ProxyGobbler\" was not injected: check your FXML file 'GoblerScene'.";
        assert optionsGroup != null : "fx:id=\"optionsGroup\" was not injected: check your FXML file 'GoblerScene'.";
        assert https != null : "fx:id=\"https\" was not injected: check your FXML file 'GoblerScene'.";
        assert http != null : "fx:id=\"http\" was not injected: check your FXML file 'GoblerScene'.";
        assert socks4 != null : "fx:id=\"socks4\" was not injected: check your FXML file 'GoblerScene'.";
        assert socks5 != null : "fx:id=\"socks5\" was not injected: check your FXML file 'GoblerScene'.";
        assert getButton != null : "fx:id=\"getButton\" was not injected: check your FXML file 'GoblerScene'.";

        LOGGER.info("Starting ProxyCorn...");
        configureMenuEvents();
        prepareCountryButtons();
        loadFiltersMenuEvents();
        configurePlugins();

    }

    private void prepareCountryButtons() {
        Reflections reflections = new Reflections(DynamicStyles.IMG_FLAGS_URL, new ResourcesScanner());
        List<String> sorted = new ArrayList<>(reflections.getResources(Pattern.compile(ALL_PNG_FILES_REGEX)));
        Collections.sort(sorted);
        buttons.setPadding(new Insets(5));

        for (String filename : sorted) {
            if (columnIndex % 4 == 0 && columnIndex != 0) {
                columnIndex = 0;
                rowIndex += 1;
            }
            ToggleButton countryButton = new CountryButton(filename);
            buttons.add(countryButton, columnIndex, rowIndex);
            columnIndex += 1;
        }
    }

    @FXML
    public void addPortEvent () {
        if (! portNumbers.getStyle().contains(DynamicStyles.RED_TEXT) && !portNumbers.getText().equals("")) {
            String port = portNumbers.getText();
            Label portLabel = new Label();
            portLabel.setText(port);
            portLabel.setId("port" + port);
            if (! filter.getPortNumbers().contains(port)) {
                labelsForPort = (GridPane) choosenPorts.getContent();
                labelsForPort.add(new Label(port), 0, i[0]);
                filter.addPort(port);
                i[0] = i[0] + 1;
            }
            portNumbers.clear();
            LOGGER.debug(filter);
            choosenPorts.setContent(labelsForPort);
        }
    };

    public void preparePortLabelFromLoadedFilter() {
        List<String> ports = Filter.getInstance().getPortNumbers();
        labelsForPort = (GridPane) choosenPorts.getContent();
        labelsForPort.getChildren().clear();
        int[] no = {0};
        i[0] = 0;
        ports.forEach(p -> {
            labelsForPort.add(new Label(p), 0, no[0]);
            i[0] = i[0] + 1;
            no[0] = no[0] +1;
        });
        choosenPorts.setContent(labelsForPort);

    }
    @FXML
    public void removePortEvent () {
        if (! portNumbers.getStyle().contains(DynamicStyles.RED_TEXT)) {
            String port = portNumbers.getText();
            if (filter.getPortNumbers().contains(port)) {
                filter.getPortNumbers().removeIf(el -> el.equals(port));
                List<String> portNumbers = filter.getPortNumbers();
                // generate labels again:
                labelsForPort = new GridPane();
                int[] i={0};
                for (String portInFilter : portNumbers) {
                    Label portLabel = new Label();
                    portLabel.setText(portInFilter);
                    portLabel.setId("port" + portInFilter);
                    labelsForPort.add(portLabel, 0, i[0]);
                    i[0] = i[0] + 1;
                }
            }
            portNumbers.clear();
            choosenPorts.setContent(labelsForPort);
            portLabelsController.setChoosenPorts(choosenPorts);
            LOGGER.debug(filter);
        }
    };

    public void configureMenuEvents () {
        ProxyGobbler.getMenus().get(0).getItems().get(0).setOnAction(event -> {
            if (stageFiltersWindow  == null ) {
                stageFiltersWindow = new Stage();
                ProxyCorn.loaderFilterScene.setRoot(new VBox());
                try {
                    parentFilters = (VBox) ProxyCorn.loaderFilterScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene mainFilterScene = new Scene(parentFilters);
                mainFilterScene.setFill(Paint.valueOf("#ffe1f5"));
                stageFiltersWindow.setScene(mainFilterScene);
                stageFiltersWindow.setAlwaysOnTop(true);
                stageFiltersWindow.toFront();
                stageFiltersWindow.setTitle(DynamicStyles.SAVE_FILTER);
            }
            stageFiltersWindow.show();
        });
    }
    public void configurePlugins () {
        ProxyGobbler.getMenus().get(0).getItems().get(2).setOnAction(event -> {
            if (stagePluginsWindow  == null ) {
                stagePluginsWindow = new Stage();
                ProxyCorn.loaderPluginScene.setRoot(new VBox());
                try {
                    parentPlugins = (VBox) ProxyCorn.loaderPluginScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene mainPluginScene = new Scene(parentPlugins);
                mainPluginScene.setFill(Paint.valueOf("#ffe1f5"));
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

    public void loadFiltersMenuEvents () {
        ProxyGobbler.getMenus().get(0).getItems().get(1).setOnAction(event -> {
            if (stageLoadFilterWindow  == null ) {
                stageLoadFilterWindow = new Stage();
                ProxyCorn.loaderLoadFilterScene.setRoot(new VBox());
                try {
                    parentLoadFilters = (VBox) ProxyCorn.loaderLoadFilterScene.load();
                } catch (IOException e) {
                    LOGGER.error("Could not load menu 'configure' ", e);
                }
                Scene loadFilterScene = new Scene(parentLoadFilters);
                loadFilterScene.setFill(Paint.valueOf("#ffe1f5"));
                stageLoadFilterWindow.setScene(loadFilterScene);
                stageLoadFilterWindow.setAlwaysOnTop(true);
                stageLoadFilterWindow.toFront();
                stageLoadFilterWindow.setTitle(DynamicStyles.LOAD_FILTER);
            }
            stageLoadFilterWindow.show();
        });
    }


}
