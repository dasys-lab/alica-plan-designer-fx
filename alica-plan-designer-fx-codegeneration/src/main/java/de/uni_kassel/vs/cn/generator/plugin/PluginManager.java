package de.uni_kassel.vs.cn.generator.plugin;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.alica.configuration.ConfigurationManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * The {@link PluginManager} holds a list of available Plugins and sets the active plugin for the current session.
 */
public class PluginManager {

    // SINGLETON
    private static volatile PluginManager instance;

    public static PluginManager getInstance() {
        if (instance == null) {
            synchronized (ConfigurationManager.class) {
                if (instance == null) {
                    instance = new PluginManager();
                }
            }
        }

        return instance;
    }

    private static Logger LOG = LogManager.getLogger(PluginManager.class);
    private List<IPlugin<?>> availablePlugins;
    private IPlugin<?> activePlugin;

    /**
     * The PluginManager initializes its plugin list at construction.
     * The first available plugin is set as active.
     * The plugin directory is not monitored for changes. That means there is no hot plug functionality here.
     */
    private PluginManager() {
        availablePlugins = new ArrayList<>();
        updateAvailablePlugins();

        if (availablePlugins.size() == 1) {
            setActivePlugin(availablePlugins.get(0));
        }

    }

    /**
     * Searches through the {@link PluginManager#availablePlugins}.
     *
     * @param name
     * @return plugin with matching name otherwise null
     */
    public IPlugin getPluginByName(String name) {
        for (IPlugin plugin : availablePlugins) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }

        return null;
    }

    /**
     * Sets the active Plugin.
     *
     * @param activePlugin
     */
    public void setActivePlugin(IPlugin<?> activePlugin) {
        this.activePlugin = activePlugin;
    }

    public List<IPlugin<?>> getAvailablePlugins() {
        return availablePlugins;
    }

    public ObservableList<String> getAvailablePluginNames() {
        ObservableList<String> pluginNamesList = FXCollections.observableArrayList();
        for (IPlugin plugin : availablePlugins) {
            pluginNamesList.add(plugin.getName());
        }
        return pluginNamesList;
    }

    /**
     * Updates the list of available plugins
     */
    public void updateAvailablePlugins() {
        Configuration conf = ConfigurationManager.getInstance().getActiveConfiguration();
        if (conf == null) {
            setActivePlugin(null);
            return;
        }

        String pluginsPath = conf.getPluginsPath();
        if (pluginsPath == null || pluginsPath.isEmpty() || !Files.exists(Paths.get(pluginsPath))) {
            LOG.info("No Plugin Path configured, or Plugin Path does not exist");
            return;
        }

        //HACK This is some nasty code to loadFromDisk the plugins
        try {
            Files.list(Paths.get(pluginsPath))
                .map(e -> e.toFile())
                .filter(e -> e.isDirectory() == false && e.getName().endsWith(".jar"))
                .forEach(f -> {
                    // Source https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
                    JarFile jarFile = null;
                    try {
                        jarFile = new JarFile(f);
                    } catch (IOException ex) {
                        LOG.error("Couldn't loadFromDisk jar file", ex);
                        throw new RuntimeException(ex);
                    }
                    Enumeration<JarEntry> e = jarFile.entries();

                    while (e.hasMoreElements()) {
                        JarEntry je = e.nextElement();
                        if (je.isDirectory() || !je.getName().endsWith(".class")) {
                            continue;
                        }
                        // -6 because of .class
                        String className = je.getName().substring(0, je.getName().length() - 6);
                        className = className.replace(File.separatorChar, '.');
                        try {
                            URLClassLoader classLoader = (URLClassLoader) ClassLoader.getSystemClassLoader();
                            Method method = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
                            method.setAccessible(true);
                            method.invoke(classLoader, f.toURI().toURL());
                            Class c = classLoader.loadClass(className);
                            Object o = c.newInstance();
                            if (o instanceof IPlugin) {
                                ((IPlugin) o).setPluginFile(f);
                                availablePlugins.add((IPlugin<?>) o);
                            }
                        } catch (ClassNotFoundException | InstantiationException ignored) {
                        } catch (IllegalAccessException | NoSuchMethodException | MalformedURLException | InvocationTargetException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
        } catch (IOException e) {
            LOG.error("Couldn't initialize PluginManager", e);
            throw new RuntimeException(e);
        }

        if (!availablePlugins.contains(activePlugin)) {
            setActivePlugin(null);
        }
    }

    /**
     * Getter for the active plugin.
     *
     * @return The currently activated plugin
     */
    public IPlugin<?> getActivePlugin() {
        return activePlugin;
    }
}
