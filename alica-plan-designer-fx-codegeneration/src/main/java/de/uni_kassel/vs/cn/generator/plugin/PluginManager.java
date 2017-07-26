package de.uni_kassel.vs.cn.generator.plugin;

import de.uni_kassel.vs.cn.planDesigner.alica.configuration.WorkspaceManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
 * Created by marci on 19.05.17.
 */
public class PluginManager {
    private static Logger LOG = LogManager.getLogger(PluginManager.class);

    private static PluginManager pluginManager;

    private List<IPlugin<?>> availablePlugins;

    private IPlugin<?> activePlugin;

    public static PluginManager getInstance() {

        if (pluginManager == null) {
            pluginManager = new PluginManager();
        }

        return pluginManager;
    }

    private PluginManager() {
        availablePlugins = new ArrayList<>();
        try {
            Files.list(Paths.get(new WorkspaceManager().getActiveWorkspace().getConfiguration().getPluginPath()))
                    .map(e -> e.toFile())
                    .filter(e -> e.isDirectory() == false && e.getName().endsWith(".jar"))
                    .forEach(f -> {
                        // Source https://stackoverflow.com/questions/11016092/how-to-load-classes-at-runtime-from-a-folder-or-jar
                        JarFile jarFile = null;
                        try {
                            jarFile = new JarFile(f);
                        } catch (IOException ex) {
                            LOG.error("Couldn't load jar file", ex);
                            throw new RuntimeException(ex);
                        }
                        Enumeration<JarEntry> e = jarFile.entries();

                        while (e.hasMoreElements()) {
                            JarEntry je = e.nextElement();
                            if(je.isDirectory() || !je.getName().endsWith(".class")){
                                continue;
                            }
                            // -6 because of .class
                            String className = je.getName().substring(0,je.getName().length()-6);
                            className = className.replace('/', '.');
                            try {
                                URLClassLoader classLoader = (URLClassLoader)ClassLoader.getSystemClassLoader();
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

        if (availablePlugins.size() == 1) {
            setActivePlugin(availablePlugins.get(0));
        }

    }

    /**
     * Searches through the {@link PluginManager#availablePlugins}.
     * @param name
     * @return plugin with matching name otherwise null
     */
    public IPlugin getPluginByName(String name) {
        for (IPlugin plugin : availablePlugins) {
            if (plugin.getPluginName().equals(name)) {
                return plugin;
            }
        }

        return null;
    }

    /**
     *
     * @param activePlugin
     */
    public void setActivePlugin(IPlugin<?> activePlugin) {
        this.activePlugin = activePlugin;
    }

    public IPlugin<?> getActivePlugin() {
        return activePlugin;
    }
}
