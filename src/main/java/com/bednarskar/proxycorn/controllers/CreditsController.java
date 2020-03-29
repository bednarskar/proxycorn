package com.bednarskar.proxycorn.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

@Getter
@Setter
public class CreditsController {

    final static Logger LOGGER = Logger.getLogger(CreditsController.class);


    @FXML
    public VBox creditsWindow;

    @FXML
    private ScrollPane scrollPaneCredits;

    @FXML
    void initialize () {

    }


}
