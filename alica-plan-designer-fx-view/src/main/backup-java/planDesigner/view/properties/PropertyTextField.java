package de.uni_kassel.vs.cn.planDesigner.view.properties;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;
import javafx.scene.control.TextField;

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
           if(MainWindowController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem() instanceof PlanTab) {
                ((PlanTab)MainWindowController.getInstance().getEditorTabPane().getSelectionModel().getSelectedItem()).setupPlanVisualisation();
           }
        });
    }

    private boolean hasSameName(T object) {
        boolean hasSameName = false;
        RepositoryViewModel repositoryViewModel = RepositoryViewModel.getInstance();
        if (object instanceof Plan) {
            hasSameName = repositoryViewModel.getPlans()
                    .stream()
                    .anyMatch(planPathPair -> planPathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof Behaviour) {
            hasSameName = repositoryViewModel.getBehaviours()
                    .stream()
                    .anyMatch(behaviourPathPair -> behaviourPathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof PlanType) {
            hasSameName = repositoryViewModel.getPlanTypes()
                    .stream()
                    .anyMatch(planTypePathPair -> planTypePathPair.getKey().getName().equals(newValue));
        }

        else if (object instanceof Task) {
            hasSameName = repositoryViewModel.getTasks().getKey()
                    .stream()
                    .anyMatch(task -> task.getName().equals(newValue));
        }
        return hasSameName;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
