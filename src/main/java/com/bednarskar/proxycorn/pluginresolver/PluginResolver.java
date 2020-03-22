package com.bednarskar.proxycorn.pluginresolver;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.stream.Stream;


public class PluginResolver {
    final static Logger LOGGER = Logger.getLogger(PluginResolver.class);
    private static Map<String, ProxyCornPlugin> proxyCornPluginMap;
    private static PluginResolver pluginResolver;

    private PluginResolver () {

    }

    public static PluginResolver getInstance () {
        if (pluginResolver == null) {
            pluginResolver = new PluginResolver();
        }
        return pluginResolver;
    }

    public Map<String, ProxyCornPlugin> getPlugins () throws Exception {
        if (proxyCornPluginMap == null) {
            loadPlugins();
        }
        return proxyCornPluginMap;
    }

    private void loadPlugins () throws Exception {
        proxyCornPluginMap = new HashMap<>();
        List<Path> pathList = getPluginsPaths();

        for (Path path : pathList) {
            File pluginJarFile = path.toFile();
            ClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{pluginJarFile.toURL()});
            JarFile jar = new JarFile(pluginJarFile);

            Attributes jarAttributes = jar.getManifest().getMainAttributes();
            Map<String, String> pluginAttributes = new HashMap<>();
            jarAttributes.forEach((key, value) -> pluginAttributes.put(key.toString(), value.toString()));
            if (validate(pluginAttributes)) {
                LOGGER.info(pluginAttributes);
                ProxyCornPlugin plugin = (ProxyCornPlugin) urlClassLoader.loadClass(pluginAttributes.get(DynamicStyles.PLUGIN_PATH_ATTRIBUTE)).getDeclaredConstructor().newInstance();
                proxyCornPluginMap.put(pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE), plugin);
//                Set<ProxyInstanceBasicInfo> proxyInstanceBasicInfoSet = plugin.getProxyInstanceBasicInfo(Filter.getInstance());
//                proxyInstanceBasicInfoSet.forEach(proxy -> LOGGER.info(proxy.getIp() + " " + proxy.getPort() + " " + proxy.getProtocol().name()));
            }
        }
    }

    private List<Path> getPluginsPaths () {
        List<Path> pathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(DynamicStyles.PLUGINS_PATH))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> !p.getFileName().endsWith(DynamicStyles.JAR))
                    .forEach(pathList::add);

        } catch (IOException e) {
            LOGGER.error("Cannot load plugins. Check if plugins directory exists.", e);
        }
        return pathList;
    }

    private boolean validate (Map<String, String> pluginAttributes) {
        return pluginAttributes.containsKey(DynamicStyles.PLUGIN_PATH_ATTRIBUTE) && pluginAttributes.containsKey(DynamicStyles.PLUGIN_NAME_ATTRIBUTE);
    }
}