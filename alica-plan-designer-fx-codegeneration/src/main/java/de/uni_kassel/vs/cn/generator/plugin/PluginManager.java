package de.uni_kassel.vs.cn.generator.plugin;

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
    private IPlugin<?> defaultPlugin;

    /**
     * The PluginManager initializes its plugin list at construction.
     * The first available plugin is set as active.
     * The plugin directory is not monitored for changes. That means there is no hot plug functionality here.
     */
    private PluginManager() {
        availablePlugins = new ArrayList<>();
    }

    /**
     * Updates the list of available plugins. Must be
     * called from outside at least once for making the PluginManager work.
     */
    public void updateAvailablePlugins(String pluginsFolder) {
        if (pluginsFolder == null || pluginsFolder.isEmpty()) {
            System.out.println("PluginManager: Setting empty plugin folder ignored.");
            return;
        }


        File folder = new File(pluginsFolder);
        List<File> jars = collectJarsRecursively(folder);

        for (File currentFile : jars) {
            System.out.println("PluginManager: " + currentFile.getName());

            // Source https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
            Enumeration<JarEntry> e = null;
            try {
                e = new JarFile(currentFile).entries();
            } catch (IOException e1) {
                e1.printStackTrace();
            }

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
                    System.out.println(currentFile.toString());
                    method.invoke(classLoader, currentFile.toURI().toURL());
                    Class c = classLoader.loadClass(className);
                    Object o = c.newInstance();
                    if (o instanceof IPlugin) {
                        ((IPlugin) o).setPluginFile(currentFile);
                        availablePlugins.add((IPlugin<?>) o);
                    }
                } catch (ClassNotFoundException | InstantiationException ignored) {
                } catch (IllegalAccessException | NoSuchMethodException | MalformedURLException | InvocationTargetException e1) {
                    e1.printStackTrace();
                }

            }
        }

        if (!availablePlugins.contains(defaultPlugin)) {
            setDefaultPlugin(null);
        }
    }

    private List<File> collectJarsRecursively(File folder) {
        List<File> jars = new ArrayList<>();

        for (String fileName : folder.list()) {
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
        for (IPlugin plugin : availablePlugins) {
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
        for (IPlugin plugin : availablePlugins) {
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
        for (IPlugin<?> plugin : availablePlugins) {
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
    public IPlugin<?> getDefaultPlugin() {
        return defaultPlugin;
    }
}
