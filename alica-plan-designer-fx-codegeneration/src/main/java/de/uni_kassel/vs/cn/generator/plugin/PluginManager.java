package de.uni_kassel.vs.cn.generator.plugin;

import de.uni_kassel.vs.cn.generator.configuration.Configuration;
import de.uni_kassel.vs.cn.generator.configuration.ConfigurationManager;
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
import java.nio.file.Path;
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
            synchronized (PluginManager.class) {
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

        updateAvailablePlugins("");

        if (availablePlugins.size() == 1) {
            setDefaultPlugin(availablePlugins.get(0));
        }
    }

    /**
     * Updates the list of available plugins
     */
    public void updateAvailablePlugins(String pluginsFolder) {
        if (pluginsFolder == null || !pluginsFolder.isEmpty())
        {
            return;
        }

        //HACK This is some nasty code to loadFromDisk the plugins
        try {
            Files.list(Paths.get(pluginsFolder))
                    .map(Path::toFile)
                    .filter(e -> !e.isDirectory() && e.getName().endsWith(".jar"))
                    .forEach(f -> {
                        // Source https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
                        JarFile jarFile;
                        try {
                            jarFile = new JarFile(f);
                        } catch (IOException e) {
                            LOG.error("Couldn't loadFromDisk jar file", e);
                            throw new RuntimeException(e);
                        }
                        Enumeration<JarEntry> e = jarFile.entries();

                        while (e.hasMoreElements()) {
                            JarEntry je = e.nextElement();
                            if (je.isDirectory() || !je.getName().endsWith(".class")) {
                                continue;
                            }
                            String className = je.getName().substring(0, je.getName().length() - String.valueOf(".class").length());
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
            setDefaultPlugin(null);
        }
    }

    /**
     * Searches through the {@link PluginManager#availablePlugins}.
     *
     * @param name of the wanted plugin
     * @return plugin with matching name otherwise null
     */
    public IPlugin getPlugin(String name) {
        for (IPlugin plugin : availablePlugins) {
            if (plugin.getName().equals(name)) {
                return plugin;
            }
        }

        return null;
    }

    public ObservableList<String> getAvailablePluginNames() {
        ObservableList<String> pluginNamesList = FXCollections.observableArrayList();
        for (IPlugin plugin : availablePlugins) {
            pluginNamesList.add(plugin.getName());
        }
        return pluginNamesList;
    }

    /**
     * Sets the active Plugin.
     *
     * @param activePlugin the plugin that should be active now
     */
    private void setDefaultPlugin(IPlugin<?> activePlugin) {
        this.activePlugin = activePlugin;
    }

    /**
     * Getter for the active plugin.
     *
     * @return The currently activated plugin
     */
    public IPlugin<?> getDefaultPlugin() {
        return activePlugin;
    }
}
