package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.models.CountryButton;
import com.bednarskar.proxycorn.models.Filter;
import com.bednarskar.proxycorn.models.FilterCheckBox;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.WindowEvent;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@Getter
@Setter
public class LoadFilterWindowController {

    final static Logger LOGGER = Logger.getLogger(LoadFilterWindowController.class);

    @FXML
    private VBox LoadFilterWindow;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private Label label;

    @FXML
    private Button load;

    @FXML
    private GridPane labelsForFilter;

    @FXML
    private ScrollPane filtersAll;

    @FXML
    private TextArea prevArea;

    @FXML
    private CheckBox http;

    private Map<String, Filter> filters;

    public LoadFilterWindowController () throws IOException {
        this.filter = Filter.getInstance();
    }

    private Filter filter;
    final int[] i = {0};

    @FXML
    void initialize () throws IOException {
        LOGGER.debug("Load filter window opened...");
        LoadFilterWindow.setDisable(false);
        LoadFilterWindow.requestLayout();
        LoadFilterWindow.layout();
        Map<String, Filter> filters = getFilters();
        load.setOnMouseClicked(event -> {
            String idToLoad = prevArea.getId();
            Filter.getInstance().setCountryCodes(filters.get(idToLoad).getCountryCodes());
            Filter.getInstance().setPortNumbers(filters.get(idToLoad).getPortNumbers());
            Filter.getInstance().setProtocols(filters.get(idToLoad).getProtocols());
            ProxyCorn.mainLoader.setRoot(new VBox());
            FXMLController controller = ProxyCorn.mainLoader.getController();
            List<String> codesCountry = filters.get(idToLoad).getCountryCodes();
                controller.getButtons().getChildren().forEach(child -> {
                    ToggleButton childT = (CountryButton) child;
                    if ((codesCountry.contains(childT.getId()) && childT.isSelected()) ||
                            (codesCountry.contains(childT.getId()) && !childT.isSelected())) {
                        // leave on list or add to list
                        childT.setSelected(false);
                        childT.fireEvent(new MouseEvent(MouseEvent.MOUSE_PRESSED, childT.getLayoutX(), childT.getLayoutY(), childT.getLayoutX(), childT.getLayoutY(), MouseButton.PRIMARY, 1,
                                true, true, true, true, true, true, true, true, true, true, null));
                        childT.setSelected(true);
                    }  else if (!codesCountry.contains(childT.getId()) && childT.isSelected()) {
                        // unclick
                        childT.fireEvent(new MouseEvent(MouseEvent.MOUSE_PRESSED, childT.getLayoutX(), childT.getLayoutY(), childT.getLayoutX(), childT.getLayoutY(), MouseButton.PRIMARY, 1,
                                true, true, true, true, true, true, true, true, true, true, null));
                        childT.setSelected(false);

                    }
                    Filter.getInstance().setCountryCodes(filters.get(idToLoad).getCountryCodes());
                });
                 controller.getOptionsGroup().getChildren().forEach(ch -> {
                     if (ch.getClass().getSimpleName().equals("FilterCheckBox")) {
                         CheckBox protocolCheckbox = ( FilterCheckBox ) ch;
                         if (filter.getProtocols().contains(ch.getId())) {
                             protocolCheckbox.setSelected(true);
                         } else {
                             protocolCheckbox.setSelected(false);
                         }
                     }

                 });
                 controller.preparePortLabelFromLoadedFilter();
            LoadFilterWindow.fireEvent(new Event(WindowEvent.WINDOW_CLOSE_REQUEST));
        });
        filters.forEach((k, v) -> {
            LOGGER.info("Initializing buttons");
            Button button = new Button(k);
            button.setId(k);
            button.setVisible(true);
            button.setMinWidth(285);
            button.setBorder(Border.EMPTY);
            button.setRotate(0);
            button.setOnMouseClicked(event -> {
                Button buttonChoosen = ( Button ) event.getSource();
                String id = buttonChoosen.getId();
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String jsonString = mapper.writeValueAsString(filters.get(id));
                    jsonString = jsonString
                            .replaceAll("\\{", "{\n")
                            .replaceAll("\",", "\",\n")
                            .replaceAll("\\}", "\n}\n")
                            .replaceAll("\\},", "\n},\n")
                            .replaceAll("\\[", "{\n")
                            .replaceAll("\\]", "\n}")
                            .replace(",\"", ",\n\"")
                            .replaceAll("\n\"", "\n \"")
                            .replaceAll("},", "  },")
                            .replace("}\n}", "  }\n}");
                    prevArea.setText(jsonString);
                    prevArea.setId(id);
                    LOGGER.info("This is id clicked:" + id);
                } catch (JsonProcessingException e) {
                    LOGGER.error("Generating preview of filter - error occured.", e);
                }

            });
            labelsForFilter.add(button,0, i[0]);
            i[0] = i[0] + 1;
        });
        mainAnchor.getChildren().set(0,labelsForFilter);

    }

    private Map<String, Filter> getFilters() throws IOException {
        filters = new HashMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(DynamicStyles.SAVED_FILTERS_PATH))) {
            paths
                    .filter(Files::isRegularFile)
                    .forEach(f -> {
                        byte[] mapData = new byte[0];
                        try {
                            mapData = Files.readAllBytes(Paths.get(f.toUri()));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        ObjectMapper objectMapper = new ObjectMapper();
                        try {
                            Filter filter = objectMapper.readValue(mapData, Filter.class);
                            filters.put(f.getFileName().toString().replaceFirst("\\_[0-9]{1,}", ""), filter);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
            return filters;
        }

    }

  }
