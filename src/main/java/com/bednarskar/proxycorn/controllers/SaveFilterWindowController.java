package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.api.model.Filter;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.util.*;

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
        File f = new File(ProjectConstants.SAVED_FILTERS_PATH);
        if (!(f.exists() && f.isDirectory())) {
            LOGGER.info("Directory " + ProjectConstants.SAVED_FILTERS_PATH + " not exists. creating directory. ");
            boolean created = new File(ProjectConstants.SAVED_FILTERS_PATH).mkdirs();
            LOGGER.info("Directory created? : " + created);
        }
        try {
            saveFilterButton.getScene().getWindow().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle (WindowEvent event) {
                    clean();
                }
            });
            mapper.writeValue(new File(ProjectConstants.SAVED_FILTERS_PATH + filterName + ProjectConstants.UNDERSCORE + new Date().getTime()), Filter.getInstance());
            filterNameField.setVisible(false);
            saveFilterButton.setVisible(false);
            label.setText(ProjectConstants.FILTER_SAVED);
            ok.setVisible(true);
        } catch (IOException e) {
            LOGGER.error("Cannot save filter. Directory " + ProjectConstants.SAVED_FILTERS_PATH + " does not exist.");
        }

    };

    @FXML
    public void ok(){
        saveFilterButton.getScene().getWindow().fireEvent(new WindowEvent(saveFilterButton.getScene().getWindow(), WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    private void clean(){
        filterNameField.setText(ProjectConstants.EMPTY);
        label.setText(ProjectConstants.SAVE_FILTER_AS);
        filterNameField.setVisible(true);
        saveFilterButton.setVisible(true);
        ok.setVisible(false);
    }
  }
