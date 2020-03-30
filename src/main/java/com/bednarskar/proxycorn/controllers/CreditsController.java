package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.application.HostServices;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
    private Label thanks;

    @FXML
    private Label madeby;

    @FXML
    private Button freepik;

    @FXML
    private Button pixelperfect;

    @FXML
    private Button flaticon2;

    @FXML
    private Group labeledCredits;

    @FXML
    void initialize () {

    }

    @FXML
    public void openWebFreepik(MouseEvent event) {
        ProxyCorn proxyCorn = new ProxyCorn();
        HostServices hostServices = proxyCorn.getHostServices();
        hostServices.showDocument(ProjectConstants.FLATICON_FREEPIK_LINK);
    }


    @FXML
    public void openWebFlaticon(MouseEvent event) {
        ProxyCorn proxyCorn = new ProxyCorn();
        HostServices hostServices = proxyCorn.getHostServices();
        hostServices.showDocument(ProjectConstants.FLATICON_LINK);
    }

    @FXML
    public void openWebPixelPerfect(MouseEvent event) {
        ProxyCorn proxyCorn = new ProxyCorn();
        HostServices hostServices = proxyCorn.getHostServices();
        hostServices.showDocument(ProjectConstants.FLATICON_PIXEL_PERFECT_LINK);
    }

    @FXML
    public void changeStyle(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_ENTERED)) {
            Button button = (Button ) event.getSource();
            button.setFont(Font.font(null, FontWeight.BOLD, FontPosture.REGULAR, 12));
        }
    }

    @FXML
    public void changeToNormal(MouseEvent event) {
        if (event.getEventType().equals(MouseEvent.MOUSE_EXITED)) {
            Button button = ( Button ) event.getSource();
            button.setFont(Font.font(null, FontWeight.NORMAL, FontPosture.REGULAR, 12));
        }
    }
}
