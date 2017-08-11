package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;
import org.apache.commons.beanutils.BeanUtils;
import sun.applet.Main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 26.02.17.
 */
class PropertyTextField<T extends PlanElement> extends TextField {

    private CommandStack commandStack;

    public PropertyTextField(T object, String propertyName, CommandStack commandStack) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.commandStack = commandStack;
        setText(BeanUtils.getProperty(object, propertyName));
        textProperty().addListener((observable, oldValue, newValue) -> {
                    getCommandStack().storeAndExecute(
                            new ChangeAttributeValue<>(object, propertyName, newValue, object));
                if(MainController.getInstance()
                        .getEditorTabPane().getSelectionModel().getSelectedItem() instanceof PlanTab) {
                    ((PlanTab)MainController.getInstance()
                            .getEditorTabPane().getSelectionModel().getSelectedItem()).setupPlanVisualisation();
                }
                if (object instanceof AbstractPlan) {
                    try {
                        EMFModelUtils.saveAlicaFile(object);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

        });
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
