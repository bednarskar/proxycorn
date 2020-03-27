package com.bednarskar.proxycorn.models;

import com.bednarskar.proxycorn.api.model.Filter;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import com.neovisionaries.i18n.CountryCode;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextAlignment;
import org.apache.log4j.Logger;

import java.io.InputStream;

/**
 * Prepare ToggleButton with specific formatting.
 */
public class CountryButton extends ToggleButton {

    final static Logger LOGGER = Logger.getLogger(CountryButton.class);
    public static final int MAX_HEIGHT = 30;
    public static final int PREF_WIDTH = 150;
    public static final int GRAPHIC_TEXT_GAP = 10;
    public static final boolean WRAP_TEXT = true;
    public static final String PNG = ".png";
    public static final String EMPTY_STRING = "";
    public static final String SLASH = "/";

    final EventHandler<MouseEvent> countryFilterAddEventHandler = ME -> {
        ToggleButton obj = ( ToggleButton ) ME.getSource();
        //&& ! obj.isSelected()
        if (obj.isPressed() && !obj.isSelected()) {
            Filter.getInstance().addCountry(obj.getId());
            obj.setStyle(DynamicStyles.PINK_SHADOW);
            obj.setSelected(false);
            LOGGER.debug(Filter.getInstance());
        } else {
            Filter.getInstance().removeCountry(obj.getId());
            obj.setStyle(DynamicStyles.NONE_EFFECT_STYLE);
            LOGGER.debug(Filter.getInstance());
        }
    };

    public CountryButton(String fileName) {
        String code = fileName.replace(PNG, EMPTY_STRING).replace(DynamicStyles.IMG_FLAGS_URL, EMPTY_STRING);
        this.setId(code);
        InputStream is = getClass().getResourceAsStream(SLASH + fileName);
        Image image = new Image(is);
        this.setGraphic(new ImageView(image));
        this.setAlignment(Pos.TOP_LEFT);

        CountryCode countryName = CountryCode.getByCode(code, false);
        if (countryName != null && countryName.getName() != null) {
            this.setText(countryName.getName());
        } else {
            this.setText(code);
        }

        this.setMaxHeight(MAX_HEIGHT);
        this.setPrefWidth(PREF_WIDTH);
        this.setGraphicTextGap(GRAPHIC_TEXT_GAP);
        this.setTextAlignment(TextAlignment.LEFT);
        this.setWrapText(WRAP_TEXT);
        this.setStyle(DynamicStyles.BUTTON_COUNTRY_STYLE);
        this.setOnMousePressed(countryFilterAddEventHandler);
    }

}
