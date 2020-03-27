package com.bednarskar.proxycorn.models;

import com.bednarskar.proxycorn.api.model.Filter;
import javafx.event.EventHandler;
import javafx.scene.control.CheckBox;
import javafx.scene.input.MouseEvent;
import org.apache.log4j.Logger;

public class FilterCheckBox extends CheckBox {
    final static Logger LOGGER = Logger.getLogger(FilterCheckBox.class);

    public FilterCheckBox() {
        final EventHandler<MouseEvent> protocolFilter = ME ->  {
            CheckBox checkBox = ( CheckBox ) ME.getSource();
            String id = checkBox.getId();
            if (! Filter.getInstance().getProtocols().contains(id)) {
                Filter.getInstance().getProtocols().add(id);
                LOGGER.debug(Filter.getInstance());
            } else {
                Filter.getInstance().getProtocols().remove(id);
                LOGGER.debug(Filter.getInstance());
            }
        };
        this.setOnMousePressed(protocolFilter);
    }
}
