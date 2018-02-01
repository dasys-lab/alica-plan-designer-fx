package de.uni_kassel.vs.cn.generator.plugin;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import javafx.scene.Parent;

import java.io.File;
import java.util.Map;

/**
 * This interface defines the basic functionality that a plugin has to implement.
 */
public interface IPlugin<T> {

    /**
     *
     * @return the custom {@link IConstraintCodeGenerator}.
     * This is the main functionality of the plugin from the perspective of the {@link de.uni_kassel.vs.cn.generator.Codegenerator}
     * or the {@link de.uni_kassel.vs.cn.generator.IGenerator}
     */
    IConstraintCodeGenerator getConstraintCodeGenerator();

    /**
     * Returns the plugin ui that will be embedded into the properties view of the condition
     * @return
     */
    Parent getPluginUI();

    /**
     * Writes into the attributes of the given condition.
     * Do NOT write data into the hierarchy above the given condition.
     * @param condition
     */
    void writePluginValuesToCondition(Condition condition);

    /**
     * Reads necessary data for the plugin out of the condition.
     * @param condition
     * @return
     */
    T readPluginValuesFromCondition(Condition condition);

    /**
     * The name has to be a non-null string value.
     * It is assumed plugin names are unique.
     * @return the name of the plugin
     */
    String getPluginName();

    /**
     * @return the JAR of the plugin
     */
    File getPluginFile();

    /**
     * This method should make a delegate to the
     * {@link IConstraintCodeGenerator} and make the protected regions known for it.
     * It is mainly a reminder to not forget the implementation
     * @param protectedRegions
     */
    void setProtectedRegions(Map<String, String> protectedRegions);

    void setPluginFile(File pluginFile);
}
