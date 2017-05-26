package de.uni_kassel.vs.cn.generator.plugin;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import javafx.scene.Parent;

import java.io.File;

/**
 * Created by marci on 19.05.17.
 */
public interface IPlugin<T> {
    IConstraintCodeGenerator getConstraintCodeGenerator();

    Parent getPluginUI();

    void writePluginValuesToCondition(Condition condition);

    T readPluginValuesFromCondition(Condition condition);

    String getPluginName();

    File getPluginFile();

    void setPluginFile(File pluginFile);
}
