package de.uni_kassel.vs.cn.defaultPlugin;

import de.uni_kassel.vs.cn.generator.IConstraintCodeGenerator;
import de.uni_kassel.vs.cn.generator.plugin.IPlugin;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Created by marci on 19.05.17.
 */
public class DefaultPlugin implements IPlugin<Void> {

    private File pluginJar;
    private DefaultConstraintCodeGenerator defaultConstraintCodeGenerator;

    public DefaultPlugin() {
        defaultConstraintCodeGenerator = new DefaultConstraintCodeGenerator();
    }

    @Override
    public IConstraintCodeGenerator getConstraintCodeGenerator() {
        return defaultConstraintCodeGenerator;
    }

    @Override
    public Parent getPluginUI() {
        FXMLLoader fxmlLoader = new FXMLLoader(this.getClass().getClassLoader().getResource("ui.fxml"));
        try {
            return fxmlLoader.load();
        } catch (IOException e) {
            // TODO add error dialog, don't rethrow
            throw new RuntimeException(e);
        }
    }

    @Override
    public void writePluginValuesToCondition(Condition condition) {
        // default doesn't need any implementation here
    }

    @Override
    public Void readPluginValuesFromCondition(Condition condition) {
        // default doesn't need any implementation here
        return null;
    }

    @Override
    public String getPluginName() {
        return "DefaultPlugin";
    }

    @Override
    public void setPluginFile(File pluginJar) {
        this.pluginJar = pluginJar;
    }

    @Override
    public File getPluginFile() {
        return pluginJar;
    }

    @Override
    public void setProtectedRegions(Map<String, String> protectedRegions) {
        defaultConstraintCodeGenerator.setProtectedRegions(protectedRegions);
    }
}
