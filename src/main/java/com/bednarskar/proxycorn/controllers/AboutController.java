package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Window;
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
    private AnchorPane scrollPaneAbout;

    @FXML
    Button githublink;

    @FXML
    void initialize () {

    }

    @FXML
    public void githubLink() {
        ProxyCorn proxyCorn = new ProxyCorn();
        HostServices hostServices = proxyCorn.getHostServices();
        hostServices.showDocument("https://github.com/bednarskar");
    }


}
