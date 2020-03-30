package com.bednarskar.proxycorn.events;

import com.bednarskar.proxycorn.api.model.Filter;
import com.bednarskar.proxycorn.controllers.PortLabelsController;
import com.bednarskar.proxycorn.models.PortNumberField;
import com.bednarskar.proxycorn.utils.ProjectConstants;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

import java.util.List;

public class RemoveAddPort {
    final static Logger LOGGER = Logger.getLogger(RemoveAddPort.class);
    static int[] i = {0};


    public void removePortEvent (PortNumberField portNumbers, GridPane labelsForPort, ScrollPane choosenPorts) {
        if (! portNumbers.getStyle().contains(ProjectConstants.RED_TEXT)) {
            String port = portNumbers.getText();
            if (Filter.getInstance().getPortNumbers().contains(port)) {
                Filter.getInstance().getPortNumbers().removeIf(el -> el.equals(port));
                List<String> portNumbers2 = Filter.getInstance().getPortNumbers();
                // generate labels again:
                labelsForPort = new GridPane();
//                int[] i = {0};
                for (String portInFilter : portNumbers2) {
                    Label portLabel = new Label();
                    portLabel.setText(portInFilter);
                    portLabel.setId("port" + portInFilter);
                    labelsForPort.add(portLabel, 0, i[0]);
                    i[0] = i[0] + 1;
                }
            } else {
                portNumbers.clear();
                return;
            }
            portNumbers.clear();
            choosenPorts.setContent(labelsForPort);
            PortLabelsController portLabelsController = new PortLabelsController();
            portLabelsController.setChoosenPorts(choosenPorts);
            LOGGER.debug(Filter.getInstance());
        }
    }

    public void addPortEvent (PortNumberField portNumbers, ScrollPane choosenPorts) {
        if (! portNumbers.getStyle().contains(ProjectConstants.RED_TEXT) && !portNumbers.getText().equals("")) {
            String port = portNumbers.getText();
            Label portLabel = new Label();
            portLabel.setText(port);
            portLabel.setId("port" + port);
            GridPane labelsForPort = (GridPane) choosenPorts.getContent();

            if (! Filter.getInstance().getPortNumbers().contains(port)) {
                if (labelsForPort == null) labelsForPort = new GridPane();
                labelsForPort.add(new Label(port), 0, i[0]);
                Filter.getInstance().addPort(port);
                i[0] = i[0] + 1;
            }
            portNumbers.clear();
            LOGGER.debug(Filter.getInstance());
            choosenPorts.setContent(labelsForPort);
        }
    };

    public void preparePortLabelFromLoadedFilter(GridPane labelsForPort, ScrollPane choosenPorts) {
        List<String> ports = Filter.getInstance().getPortNumbers();
        labelsForPort = (GridPane) choosenPorts.getContent();
        labelsForPort.getChildren().clear();
        int[] no = {0};
        i[0] = 0;
        GridPane finalLabelsForPort = labelsForPort;
        ports.forEach(p -> {
            finalLabelsForPort.add(new Label(p), 0, no[0]);
            i[0] = i[0] + 1;
            no[0] = no[0] +1;
        });
        choosenPorts.setContent(finalLabelsForPort);

    }
}
