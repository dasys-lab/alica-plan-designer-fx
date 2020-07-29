package de.unikassel.vs.alica.codegen.plugin;

import de.unikassel.vs.alica.codegen.Codegenerator;
import de.unikassel.vs.alica.codegen.GeneratedSourcesManager;
import javafx.scene.Parent;

import java.io.IOException;

/**
 * This interface defines the basic functionality that a plugin has to implement.
 */
public interface IPlugin {
    /**
     * Returns the plugin view that will be embedded into the properties view of the newCondition
     * @return the
     */
    Parent getPluginUI() throws IOException;

    /**
     * The name has to be a non-null string value. It is assumed plugin names are unique.
     * @return the name of the plugin
     */
    String getName();

    Codegenerator getCodeGenerator();

    GeneratedSourcesManager getGeneratedSourcesManager();
}
