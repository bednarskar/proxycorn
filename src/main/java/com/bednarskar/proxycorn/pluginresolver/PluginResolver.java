package com.bednarskar.proxycorn.pluginresolver;

import com.bednarskar.proxycorn.api.ProxyCornPlugin;
import com.bednarskar.proxycorn.utils.DynamicStyles;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
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
            loadPluginProperties();
            loadPlugins();
        }
        // return only "ON" plugins
        // 1.remove off plugins
        proxyCornPluginMap.entrySet().removeIf(entry -> pluginStates.containsKey(entry.getKey()) && pluginStates.get(entry.getKey()).name().equals(PluginState.OFF.name()));
        //2. load already turned on plugins
        pluginStates.forEach((name, state) -> {
            if (state.name().equals(PluginState.ON.name()) && ! proxyCornPluginMap.containsKey(name)) {
                try {
                    LOGGER.debug("Loading plugin " + name);
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
                LOGGER.debug("Loaded plugin "+ pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE));
//                Set<ProxyInstanceBasicInfo> proxyInstanceBasicInfoSet = plugin.getProxyInstanceBasicInfo(Filter.getInstance());
//                proxyInstanceBasicInfoSet.forEach(proxy -> LOGGER.info(proxy.getIp() + " " + proxy.getPort() + " " + proxy.getProtocol().name()));
            }


        }
    }

    public void reloadPlugins() throws Exception {
        loadPlugins();
    }
    private void loadPlugins() throws Exception {
        LOGGER.debug("Loading plugins...");
        proxyCornPluginMap = new HashMap<>();
        List<Path> pathList = getPluginsPaths();
        if (!pathList.isEmpty()) {
            for(Path path : pathList) {
                File pluginJarFile = path.toFile();
                LOGGER.info("Loading plugin from path: " + path);
                ClassLoader urlClassLoader = URLClassLoader.newInstance(new URL[]{pluginJarFile.toURL()});
                JarFile jar = new JarFile(pluginJarFile);

                Attributes jarAttributes = jar.getManifest().getMainAttributes();
                Map<String, String> pluginAttributes = new HashMap<>();
                jarAttributes.forEach((key, value) -> pluginAttributes.put(key.toString(), value.toString()));
                if(validate(pluginAttributes)) {
                    LOGGER.info(pluginAttributes);
                    ProxyCornPlugin plugin = ( ProxyCornPlugin ) urlClassLoader.loadClass(pluginAttributes.get(DynamicStyles.PLUGIN_PATH_ATTRIBUTE)).getDeclaredConstructor().newInstance();
                    LOGGER.info("Loaded plugin class: " + plugin.getName());
                    String pluginName = pluginAttributes.get(DynamicStyles.PLUGIN_NAME_ATTRIBUTE);
                    proxyCornPluginMap.put(pluginName, plugin);
                    if (pluginStates.containsKey(pluginName)) {
                        setPluginState(pluginStates.get(pluginName), pluginName);
                    } else {
                        // set plugin off by default
                        setPluginState(PluginState.OFF, pluginName);
                    }
//                Set<ProxyInstanceBasicInfo> proxyInstanceBasicInfoSet = plugin.getProxyInstanceBasicInfo(Filter.getInstance());
//                proxyInstanceBasicInfoSet.forEach(proxy -> LOGGER.info(proxy.getIp() + " " + proxy.getPort() + " " + proxy.getProtocol().name()));
                }
            }
        }
    }

    private List<Path> getPluginsPaths () {
        List<Path> pathList = new ArrayList<>();
        try (Stream<Path> paths = Files.walk(Paths.get(DynamicStyles.PLUGINS_PATH))) {
            paths
                    .filter(Files::isRegularFile)
                    .filter(p -> ! FilenameUtils.getExtension(p.toString()).equals(DynamicStyles.JAR))
                    .forEach(pathList::add);

        } catch (IOException e) {
            LOGGER.error("Cannot load plugins. Check if plugins directory exists.", e);
        }
        LOGGER.debug("Number of paths with plugins: " + pathList.size());
        LOGGER.debug("Detected paths: " + pathList);
        return pathList;
    }

    private boolean validate (Map<String, String> pluginAttributes) {
        return pluginAttributes.containsKey(DynamicStyles.PLUGIN_PATH_ATTRIBUTE) && pluginAttributes.containsKey(DynamicStyles.PLUGIN_NAME_ATTRIBUTE);
    }

    public void setPluginState(Enum<PluginState> pluginState, String pluginName) {
        LOGGER.debug("Plugin state set to: " + pluginState.name() + " " + pluginName);
        pluginStates.put(pluginName, pluginState);
        savePluginProperties();
    }

    public Map<String, Enum<PluginState>> getPluginStates() {
        return pluginStates;
    }
    private void savePluginProperties() {
        File f = new File(DynamicStyles.PROPERTIES_FILE_PATH);
        if (!f.exists() || !f.isDirectory()) {
            LOGGER.info("Directory " + DynamicStyles.PROPERTIES_FILE_PATH + " not exists. creating directory. ");
            boolean status = new File(DynamicStyles.PROPERTIES_FILE_PATH).mkdirs();
        }
        LOGGER.info("Plugin state changed - generating plugins.properties file...");
        Properties properties = new Properties();
        try(OutputStream outputStream = new FileOutputStream(DynamicStyles.PROPERTIES_FILE_PATH + "plugins.properties")){
            pluginStates.forEach((k, v) ->{
                properties.setProperty(k, v.name());
            });
            properties.store(outputStream, null);
            LOGGER.info("Plugins.properties file generated. ");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPluginProperties() {
        Properties prop = new Properties();
        try {
            LOGGER.info("Loading saved plugins properties - loading states. ");
            prop.load(new FileInputStream(DynamicStyles.PROPERTIES_FILE_PATH + "plugins.properties"));
            prop.forEach((key, value) -> pluginStates.put(( String ) key, PluginState.valueOf(( String ) value)));
            LOGGER.info("PluginStates map populated: " + pluginStates.toString());
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}