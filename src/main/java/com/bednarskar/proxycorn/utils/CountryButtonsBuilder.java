package com.bednarskar.proxycorn.utils;

import com.bednarskar.proxycorn.models.CountryButton;
import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class CountryButtonsBuilder {
    private int rowIndex = 0;
    private int columnIndex = 0;

    public void prepareCountryButtons(GridPane buttonsGrid) {
        Reflections reflections = new Reflections(DynamicStyles.IMG_FLAGS_URL, new ResourcesScanner());
        List<String> sorted = new ArrayList<>(reflections.getResources(Pattern.compile(DynamicStyles.ALL_PNG_FILES_REGEX)));
        Collections.sort(sorted);
        buttonsGrid.setPadding(new Insets(5));

        for (String filename : sorted) {
            if (columnIndex % 4 == 0 && columnIndex != 0) {
                columnIndex = 0;
                rowIndex += 1;
            }
            ToggleButton countryButton = new CountryButton(filename);
            buttonsGrid.add(countryButton, columnIndex, rowIndex);
            columnIndex += 1;
        }
    }
}
