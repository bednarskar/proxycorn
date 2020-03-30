package com.bednarskar.proxycorn.events;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.api.model.Filter;
import com.bednarskar.proxycorn.api.model.ProxyInstanceBasicInfo;
import com.bednarskar.proxycorn.pluginresolver.PluginResolver;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
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
        Date date = new Date();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save file");
        File file = File.createTempFile(String.valueOf(date.getTime()), "tmp");
        FileWriter fw = new FileWriter(file, false);
        BufferedWriter bw = new BufferedWriter(fw);
        for (ProxyInstanceBasicInfo proxyInstanceBasicInfo : preparedProxies) {
            String protocol = proxyInstanceBasicInfo.getProtocol().name();
            if (protocol.equals("https")) {
                bw.write(proxyInstanceBasicInfo.getProtocol().name() + " " + proxyInstanceBasicInfo.getIp() + " " + proxyInstanceBasicInfo.getPort());
                bw.newLine();
            }
        }
        bw.close();
        fw.close();
        File dest = fileChooser.showSaveDialog(new Stage());
        fileChooser.setInitialFileName("proxies_" + date.getTime());
        if (dest != null) {
            LOGGER.info("Saving proxies file to: " + dest.getAbsolutePath());
            Files.copy(file.toPath(), dest.toPath());
        }
        LOGGER.info("File saved. Deleting temp file.");
        file.delete();
    }
}
