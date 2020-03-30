package com.bednarskar.proxycorn.models;

import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.event.EventHandler;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

/**
 * Model class PortNumberField for autovalidating fields with port ranges.
 * Port ranges in format 0-8080
 * In case invalid range or port it is setting css style with red text.
 */
public class PortNumberField extends TextField {

    private final String PORT_RANGE_REGEX = "(?<A>(([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])))" +
            "((?<B>(\\-)(([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5]))))?";
    private final String PORT_RANGE_PARTIAL_REGEX = "(?<A>(([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])))" +
            "(?<B>(\\-))";
    private final String SPLITTER = "\\-";
    private final String ZERO = "0";

    public PortNumberField() {
        EventHandler<KeyEvent> textEvent = TE -> {
            PortNumberField field = (PortNumberField) TE.getSource();
            TE.consume();
            String text = field.getText();
            if (! text.matches(PORT_RANGE_REGEX)) {
                if (! text.matches(PORT_RANGE_PARTIAL_REGEX)) {
                    this.deletePreviousChar();
                } else {
                    field.setStyle(ProjectConstants.RED_TEXT);
                }
            } else {
                String[] portRange = text.split(SPLITTER);
                if (portRange.length == 2) {
                    if (Integer.valueOf(portRange[1]) < Integer.valueOf(portRange[0])) {
                        field.setStyle(ProjectConstants.RED_TEXT);
                    } else {
                        field.setStyle(ProjectConstants.NORMAL_TEXT);
                    }
                } else if (portRange.length ==1 && portRange[0].length()>1 && portRange[0].startsWith(ZERO)){
                    field.setStyle(ProjectConstants.RED_TEXT);
                } else {
                    field.setStyle(ProjectConstants.NORMAL_TEXT);
                }
            }

        };
        this.setOnKeyTyped(textEvent);
    }


}
