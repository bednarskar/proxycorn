package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

@Getter
@Setter
public class LoadPluginWindowController {
    final static Logger LOGGER = Logger.getLogger(LoadPluginWindowController.class);

    @FXML
    private VBox LoadPluginWindow;

    @FXML
    private GridPane loadPluginAnchor;

    @FXML
    void initialize() {
        try {
            Map<String, ProxyCornPlugin> plugins = PluginResolver.getInstance().getPlugins();
            int[] row = {0};
            plugins.forEach((k,v) -> {
                ImageView imageView =new ImageView("/icons/off.png");
                imageView.setId(k);
                imageView.setSmooth(true);
                imageView.setFitWidth(50);
                imageView.setFitHeight(45);
                imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle (MouseEvent event) {
                        ImageView imageView1 = (ImageView) event.getSource();
                        if (imageView1.getImage().getUrl().contains("/icons/off.png")) {
                            imageView1.setImage(new Image("/icons/switch.png"));
                            imageView1.setSmooth(true);
                        } else {
                            imageView1.setImage(new Image("/icons/off.png"));
                            imageView1.setSmooth(true);
                        }
                    }
                });

                Label label = new Label(k);
                label.setId(k);
                label.setLayoutX(80);
                label.setLabelFor(imageView);
                loadPluginAnchor.add(imageView, 0, row[0]);
                loadPluginAnchor.add(label, 1, row[0]);
                row[0] = row[0] + 1;
            });
        } catch (Exception e) {
            LOGGER.error("Error occured during loading plugins. ", e);
        }

    }

}
