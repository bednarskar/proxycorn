package com.bednarskar.proxycorn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

@Getter
@Setter
public class AboutController {

    final static Logger LOGGER = Logger.getLogger(AboutController.class);

    @FXML
    private VBox aboutWindow;

    @FXML
    private ScrollPane scrollPaneAbout;

    @FXML
    void initialize () {

    }


}
