package com.bednarskar.proxycorn.pluginresolver;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
    private static Map<String, Enum<PluginState>> pluginStates;

    private PluginResolver () {
    }

    public static PluginResolver getInstance () {
        if (pluginResolver == null) {
            pluginResolver = new PluginResolver();
            pluginStates = new HashMap<>();
        }
        return pluginResolver;
    }

    public Map<String, ProxyCornPlugin> getPlugins() throws Exception {
        if (proxyCornPluginMap == null) {
            loadPlugins();
        }
        // return only "ON" plugins
        // 1.remove off plugins
        proxyCornPluginMap.entrySet().removeIf(entry -> pluginStates.containsKey(entry.getKey()) && pluginStates.get(entry.getKey()).name().equals(PluginState.OFF.name()));
        //2. load already turned on plugins
        pluginStates.forEach((name, state) -> {
            if (state.name().equals(PluginState.ON.name()) && ! proxyCornPluginMap.containsKey(name)) {
                try {
                    loadSinglePlugin(name);
                } catch (Exception e) {
                   LOGGER.error("Error during loading plugin "+ name, e);
                }
            }
        });
        return proxyCornPluginMap;
    }

    private void loadSinglePlugin(String name) throws Exception {
        List<Path> pathList = getPluginsPaths();

        for (Path path : pathList) {
            File pluginJarFile = path.toFile();
            JarFile jar = new JarFile(pluginJarFile);
            Attributes jarAttributes = jar.getManifest().getMainAttributes();
            Map<String, String> pluginAttributes = new HashMap<>();
            jarAttributes.forEach((key, value) -> pluginAttributes.put(key.toString(), value.toString()));
            if (validate(pluginAttributes) && pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE).equalsIgnoreCase(name)) {
                LOGGER.info("Loading additional plugin: " +pluginAttributes);
                ClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{pluginJarFile.toURL()});
                ProxyCornPlugin plugin = (ProxyCornPlugin) urlClassLoader.loadClass(pluginAttributes.get(DynamicStyles.PLUGIN_PATH_ATTRIBUTE)).getDeclaredConstructor().newInstance();
                proxyCornPluginMap.put(pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE), plugin);
                // set plugin on by default
                setPluginState(PluginState.ON, pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE));
//                Set<ProxyInstanceBasicInfo> proxyInstanceBasicInfoSet = plugin.getProxyInstanceBasicInfo(Filter.getInstance());
//                proxyInstanceBasicInfoSet.forEach(proxy -> LOGGER.info(proxy.getIp() + " " + proxy.getPort() + " " + proxy.getProtocol().name()));
            }


        }
    }

    private void loadPlugins() throws Exception {
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
                // set plugin off by default
                setPluginState(PluginState.OFF, pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE));
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

    public void setPluginState(Enum<PluginState> pluginState, String pluginName) {
        pluginStates.put(pluginName, pluginState);
    }

    public Map<String, Enum<PluginState>> getPluginStates() {
        return pluginStates;
    }
}