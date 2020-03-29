package com.bednarskar.proxycorn.controllers;

import com.bednarskar.proxycorn.ProxyCorn;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import com.bednarskar.proxycorn.pluginresolver.PluginState;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.Window;
import lombok.Getter;
import lombok.Setter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Map;

@Getter
@Setter
public final class LoadPluginWindowController {
    final static Logger LOGGER = Logger.getLogger(LoadPluginWindowController.class);

    @FXML
    private VBox LoadPluginWindow;

    @FXML
    private GridPane loadPluginAnchor;

    @FXML
    private ImageView tux;

    @FXML
    private ScrollPane scrollPaneLoadPlugins;

    @FXML
    private Label label;
    @FXML
    public void initialize() {
       reloadPlugins();
     }

     @FXML
    public void reloadPlugins() {
            try {
            Map<String, Enum<PluginState>> allPlugins = PluginResolver.getInstance().getPluginStates();
            int[] row = {0};
            allPlugins.forEach((name,state) -> {
                String imagePath;
                if (state == PluginState.OFF){
                    imagePath = "/icons/off.png";
                } else {
                    imagePath = "/icons/on.png";
                }
                ImageView imageView =new ImageView(imagePath);
                imageView.setId(name);
                imageView.setSmooth(true);
                imageView.setFitWidth(50);
                imageView.setFitHeight(45);
                imageView.setOnMouseClicked(event -> {
                    ImageView imageView1 = ( ImageView ) event.getSource();
                    if (imageView1.getImage().getUrl().contains("/icons/off.png")) {
                        imageView1.setImage(new Image("/icons/on.png"));
                        imageView1.setSmooth(true);
                        PluginResolver.getInstance().setPluginState(PluginState.ON, imageView1.getId());
                    } else {
                        imageView1.setImage(new Image("/icons/off.png"));
                        imageView1.setSmooth(true);
                        PluginResolver.getInstance().setPluginState(PluginState.OFF, imageView1.getId());
                    }
                });

                Label label = new Label(name);
                label.setId(name);
                label.setLayoutX(80);
                label.setLabelFor(imageView);
                loadPluginAnchor.setHgap(25);
                loadPluginAnchor.add(imageView, 1, row[0]);
                loadPluginAnchor.add(label, 2, row[0]);
                row[0] = row[0] + 1;
            });
            scrollPaneLoadPlugins.setContent(loadPluginAnchor);
            LoadPluginWindow.getChildren().set(0, scrollPaneLoadPlugins);
        } catch (Exception e) {
            LOGGER.error("Error occured during loading plugins. ", e);
        }
    }

}
