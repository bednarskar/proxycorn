package com.bednarskar.proxycorn.events;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.api.model.Filter;
import com.bednarskar.proxycorn.api.model.ProxyInstanceBasicInfo;
import com.bednarskar.proxycorn.menu.configurator.ConfigureMenu;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GetProxies {
    final static Logger LOGGER = Logger.getLogger(GetProxies.class);

    public void getProxiesEvent() throws Exception {
        Map<String, ProxyCornPlugin> availablePlugins = PluginResolver.getInstance().getPlugins();
        Set<ProxyInstanceBasicInfo> preparedProxies = new HashSet<>();
        availablePlugins.forEach((k,v) ->{
            LOGGER.info("Getting proxies from.......... " + k);
            try {
                Set<ProxyInstanceBasicInfo> proxiesFromPlugin = v.getProxyInstanceBasicInfo(Filter.getInstance());
                preparedProxies.addAll(proxiesFromPlugin);
                LOGGER.info("Number of proxies from " + k + ": " + proxiesFromPlugin.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File file = File.createTempFile("maa", "tmp");
        File dest = fileChooser.showSaveDialog(new Stage());
        Date date = new Date();
        fileChooser.setInitialFileName("proxies_"+date.getTime());
        if (dest != null) {
            Files.copy(file.toPath(), dest.toPath());
        }
    }
}
