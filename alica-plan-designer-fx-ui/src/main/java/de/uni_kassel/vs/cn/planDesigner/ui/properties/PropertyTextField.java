package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.common.FileWrapper;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.controller.ErrorWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import javafx.scene.control.TextField;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 26.02.17.
 */
class PropertyTextField<T extends PlanElement> extends TextField {

    private CommandStack commandStack;

    String oldValue = "";

    String newValue = "";

    public PropertyTextField(T object, String propertyName, CommandStack commandStack) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        this.commandStack = commandStack;
        setText(BeanUtils.getProperty(object, propertyName));
        focusedProperty().addListener((observable, oldVal, newVal) -> {
            if (newVal) {
                oldValue = this.getText();
            } else {
                setText(oldValue);
                newValue = "";
            }
        });

        setOnAction(event -> {
            if (!newValue.isEmpty()) {
                if(propertyName.equals("name"))
                {
                    if (hasSameName(object)) {
                        ErrorWindowController.createErrorWindow(I18NRepo.getInstance().getString("label.error.rename"), null);
                    } else {
                        getCommandStack().storeAndExecute(
                                new ChangeAttributeValue<>(object, propertyName, newValue, object));
                    }
                } else {
                    getCommandStack().storeAndExecute(
                        new ChangeAttributeValue<>(object, propertyName, newValue, object));
                }
            }
            newValue = "";
            event.consume();
        });

       textProperty().addListener((observable, oldVal, newVal) -> {
           newValue = newVal;
           if(MainController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem() instanceof PlanTab) {
                ((PlanTab)MainController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem()).setupPlanVisualisation();
           }
        });
    }

    private boolean hasSameName(T object) {
        boolean hasSameName = false;
        RepoViewBackend repoViewBackend = RepoViewBackend.getInstance();
        if (object instanceof Plan) {
            hasSameName = repoViewBackend.getPlans()
                    .stream()
                    .anyMatch(planPathPair -> planPathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof Behaviour) {
            hasSameName = repoViewBackend.getBehaviours()
                    .stream()
                    .anyMatch(behaviourPathPair -> behaviourPathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof PlanType) {
            hasSameName = repoViewBackend.getPlanTypes()
                    .stream()
                    .anyMatch(planTypePathPair -> planTypePathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof Task) {
            hasSameName = repoViewBackend.getTasks().getKey()
                    .stream()
                    .anyMatch(task -> task.getName().equals(newValue));
        }
        return hasSameName;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
