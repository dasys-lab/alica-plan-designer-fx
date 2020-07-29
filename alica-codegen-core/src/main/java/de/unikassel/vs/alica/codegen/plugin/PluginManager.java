package de.unikassel.vs.alica.codegen.plugin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

/**
 * The {@link PluginManager} holds a list of available Plugins and sets the active plugin for the current session.
 */
public class PluginManager {
    private static volatile PluginManager instance;

    public static PluginManager getInstance() {
        if (instance == null) {
            synchronized (PluginManager.class) {
                if (instance == null) {
                    instance = new PluginManager();
                }
            }
        }
        return instance;
    }

    private static Logger LOG = LogManager.getLogger(PluginManager.class);
    private final Map<String, IPlugin> availablePlugins;
    private IPlugin defaultPlugin;

    /**
     * The PluginManager initializes its plugin list at construction.
     * The first available plugin is set as active.
     * The plugin directory is not monitored for changes. That means there is no hot plug functionality here.
     */
    private PluginManager() {
        availablePlugins = new HashMap<>();
    }

    /**
     * Updates the list of available plugins. Must be
     * called from outside at least once for making the PluginManager work.
     */
    public void updateAvailablePlugins(String pluginsFolder) {
        availablePlugins.clear();
        if (pluginsFolder == null || pluginsFolder.isEmpty()) {
            System.out.println("PluginManager: Setting empty plugin folder ignored.");
            return;
        }

        File folder = new File(pluginsFolder);
        List<File> files = collectJarsRecursively(folder);
        for (File file: files) {
            try {
                loadPlugin(file);
                LOG.debug("Loaded plugin: " + file.toString());
            } catch (Exception e) {
                LOG.debug("Skipped plugin: " + file.toString());
            }
        }

        if (!availablePlugins.containsValue(defaultPlugin)) {
            setDefaultPlugin(null);
        }
    }

    public void loadPlugin(File file) throws Exception {
        JarFile jarFile = new JarFile(file);
        Manifest manifest = jarFile.getManifest();
        Attributes attrib = manifest.getMainAttributes();
        String main = attrib.getValue(Attributes.Name.MAIN_CLASS);

        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{file.toURI().toURL()});
        Class<?> mainClass = urlClassLoader.loadClass(main);
        IPlugin plugin = (IPlugin) mainClass.getDeclaredConstructor().newInstance();
        availablePlugins.put(file.getName(), plugin);
    }

    private List<File> collectJarsRecursively(File folder) {
        List<File> jars = new ArrayList<>();

        String[] list = folder.list();
        if (list == null) {
            return jars;
        }

        for (String fileName: list) {
            File currentFile = Paths.get(folder.getAbsolutePath(),fileName).toFile();
            if (currentFile.isDirectory()) {
                jars.addAll(collectJarsRecursively(currentFile));
            } else if (currentFile.isFile() && fileName.endsWith(".jar")) {
                jars.add(currentFile);
            }
        }
        return jars;
    }

    public ObservableList<String> getAvailablePluginNames() {
        ObservableList<String> pluginNamesList = FXCollections.observableArrayList();
        for (IPlugin plugin: availablePlugins.values()) {
            pluginNamesList.add(plugin.getName());
        }
        return pluginNamesList;
    }

    /**
     * Searches through the {@link PluginManager#availablePlugins}.
     *
     * @param name of the wanted plugin
     * @return plugin with matching name otherwise null
     */
    public IPlugin getPlugin(String name) {
        for (IPlugin plugin: availablePlugins.values()) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }
        return null;
    }

    /**
     * Sets the defaultplugin.
     *
     * @param defaultPluginName the plugin that should be active now
     */
    public void setDefaultPlugin(String defaultPluginName) {
        for (IPlugin plugin: availablePlugins.values()) {
            if (plugin.getName().equals(defaultPluginName)) {
                this.defaultPlugin = plugin;
                return;
            }
        }
    }

    /**
     * Getter for the default plugin.
     *
     * @return The default plugin
     */
    public IPlugin getDefaultPlugin() {
        return defaultPlugin;
    }
}
