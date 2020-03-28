package com.bednarskar.proxycorn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

@Getter
@Setter
public class PortLabelsController {
    final static Logger LOGGER = Logger.getLogger(MainController.class);

    @FXML
    ScrollPane choosenPorts;

    @FXML
    GridPane labelsForPort;

    @FXML
    void initialize() {
        assert choosenPorts != null : "fx:id=\"choosenPorts\" was not injected: check your FXML file 'PortLabels'.";
        assert labelsForPort != null : "fx:id=\"labelsForPort\" was not injected: check your FXML file 'PortLabels'.";
    }

}
