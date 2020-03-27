package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.events.GetProxies;
import com.bednarskar.proxycorn.events.RemoveAddPort;
import com.bednarskar.proxycorn.menu.configurator.ConfigureMenu;
import com.bednarskar.proxycorn.menu.configurator.ConfigurePluginsMenu;
import com.bednarskar.proxycorn.menu.configurator.LoadFiltersMenu;
import com.bednarskar.proxycorn.models.FilterCheckBox;
import com.bednarskar.proxycorn.models.PortNumberField;
import com.bednarskar.proxycorn.utils.CountryButtonsBuilder;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

@Getter
@Setter
public class MainController {

    final static Logger LOGGER = Logger.getLogger(MainController.class);

    private PortLabelsController portLabelsController;

    public MainController () {
        portLabelsController = new PortLabelsController();
    }

    @FXML
    private Label label;

    @FXML
    private ResourceBundle resources;

    @FXML
    private VBox GobblerProxy;

    @FXML
    private MenuBar menuBar;

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

    @FXML
    void initialize () {
        assert GobblerProxy != null : "fx:id=\"GobblerProxy\" was not injected: check your FXML file 'GoblerScene'.";
        assert menuBar != null : "fx:id=\"ProxyGobbler\" was not injected: check your FXML file 'GoblerScene'.";
        assert optionsGroup != null : "fx:id=\"optionsGroup\" was not injected: check your FXML file 'GoblerScene'.";
        assert https != null : "fx:id=\"https\" was not injected: check your FXML file 'GoblerScene'.";
        assert http != null : "fx:id=\"http\" was not injected: check your FXML file 'GoblerScene'.";
        assert socks4 != null : "fx:id=\"socks4\" was not injected: check your FXML file 'GoblerScene'.";
        assert socks5 != null : "fx:id=\"socks5\" was not injected: check your FXML file 'GoblerScene'.";
        assert getButton != null : "fx:id=\"getButton\" was not injected: check your FXML file 'GoblerScene'.";

        LOGGER.info("Starting ProxyCorn...");
        CountryButtonsBuilder countryButtonsBuilder = new CountryButtonsBuilder();
        countryButtonsBuilder.prepareCountryButtons(buttons);
        ConfigureMenu configureMenu = new ConfigureMenu();
        configureMenu.loadConfigureMenu(menuBar);
        LoadFiltersMenu loadFiltersMenu = new LoadFiltersMenu();
        loadFiltersMenu.loadLoadFiltersMenu(menuBar);
        ConfigurePluginsMenu configurePluginsMenu = new ConfigurePluginsMenu();
        configurePluginsMenu.loadConfigurePluginsMenu(menuBar);

    }

    @FXML
    public void addPortEvent () {
        RemoveAddPort removeAddPort = new RemoveAddPort();
        removeAddPort.addPortEvent(portNumbers,  choosenPorts);
    };

    public void preparePortLabelFromLoadedFilter() {
        RemoveAddPort removeAddPort = new RemoveAddPort();
        removeAddPort.preparePortLabelFromLoadedFilter(labelsForPort, choosenPorts);
    }

    @FXML
    public void removePortEvent () {
        RemoveAddPort removePort = new RemoveAddPort();
        removePort.removePortEvent(portNumbers, labelsForPort, choosenPorts);
    };

    @FXML
    public void getProxiesEvent() throws Exception {
        GetProxies getProxies = new GetProxies();
        getProxies.getProxiesEvent();
    }


}
