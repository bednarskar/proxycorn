package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.models.CountryButton;
import com.bednarskar.proxycorn.models.Filter;
import com.bednarskar.proxycorn.models.FilterCheckBox;
import com.bednarskar.proxycorn.models.PortNumberField;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.log4j.Logger;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.regex.Pattern;

@Getter
@Setter
public class SaveFilterWindowController {

    final static Logger LOGGER = Logger.getLogger(SaveFilterWindowController.class);

    @FXML
    VBox SaveFilterWindow;

    @FXML
    AnchorPane mainAnchor;

    @FXML
    Button saveFilterButton;

    @FXML
    TextField filterNameField;

    @FXML
    Label label;

    @FXML
    Button ok;


    public SaveFilterWindowController () throws IOException {
        this.filter = Filter.getInstance();
    }

    private Filter filter;

    @FXML
    void initialize () throws IOException {
        LOGGER.debug("Save filter window opened...");
        SaveFilterWindow.setDisable(false);

        SaveFilterWindow.requestLayout();
        SaveFilterWindow.layout();
    }

    @FXML
    public void saveFilter () {
        String filterName = filterNameField.getText();
        ObjectMapper mapper = new ObjectMapper();

        try {
            saveFilterButton.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle (WindowEvent event) {
                    clean();
                }
            });
            mapper.writeValue(new File(DynamicStyles.SAVED_FILTERS_PATH + filterName + DynamicStyles.UNDERSCORE + new Date().getTime()), Filter.getInstance());
            filterNameField.setVisible(false);
            saveFilterButton.setVisible(false);
            label.setText(DynamicStyles.FILTER_SAVED);
            ok.setVisible(true);
        } catch (IOException e) {
            LOGGER.error("Cannot save filter. Directory " + DynamicStyles.SAVED_FILTERS_PATH + " does not exist.");
        }

    };

    @FXML
    public void ok(){
        saveFilterButton.getScene().getWindow().fireEvent(new WindowEvent(saveFilterButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void clean(){
        filterNameField.setText(DynamicStyles.EMPTY);
        label.setText(DynamicStyles.SAVE_FILTER_AS);
        filterNameField.setVisible(true);
        saveFilterButton.setVisible(true);
        ok.setVisible(false);
    }
  }
