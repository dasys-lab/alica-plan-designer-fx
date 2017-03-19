package de.uni_kassel.vs.cn.planDesigner.ui.menu;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.*;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.EntryPointImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.StateImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.SynchronisationImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.TransitionImpl;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.TaskRepositoryTab;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

/**
 * Created by marci on 17.03.17.
 */
public class EditMenu extends Menu {

    private CommandStack commandStack;

    public EditMenu(CommandStack commandStack, EditorTabPane editorTabPane) {
        super(I18NRepo.getString("label.menu.edit"));
        this.commandStack = commandStack;
        MenuItem deleteElementItem = new MenuItem(I18NRepo.getString("label.menu.edit.delete"));
        MenuItem undoItem = new MenuItem(I18NRepo.getString("label.menu.edit.undo"));
        undoItem.setDisable(false);
        commandStack.addObserver((a,b) -> {
            if (((CommandStack)a).getUndoStack().isEmpty()) {
                undoItem.setDisable(true);
            } else {
                undoItem.setDisable(false);
            }
        });
        undoItem.setOnAction(event -> {
            commandStack.undo();
            Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
            if (selectedItem instanceof PlanTab) {
                ((PlanTab)selectedItem).getPlanEditorPane().setupPlanVisualisation();
                ((PlanTab)selectedItem).getConditionHBox().setupConditionVisualisation();
            } else if (selectedItem instanceof TaskRepositoryTab) {
                ((TaskRepositoryTab)selectedItem).createContentView();
            }
        });
        MenuItem redoItem = new MenuItem(I18NRepo.getString("label.menu.edit.redo"));
        commandStack.addObserver((a,b) -> {
            if (((CommandStack)a).getRedoStack().isEmpty()) {
                redoItem.setDisable(true);
            } else {
                redoItem.setDisable(false);
            }
        });
        redoItem.setOnAction(event -> {
            commandStack.redo();
            ((PlanTab)editorTabPane.getSelectionModel().getSelectedItem()).getPlanEditorPane().setupPlanVisualisation();
        });
        redoItem.setDisable(false);
        deleteElementItem.setOnAction(event -> {
            Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            if (selectedItem instanceof PlanTab) {
                PlanTab planTab = (PlanTab) selectedItem;
                PlanElement selectedPlanElement = planTab.getSelectedPlanElement().getValue().getKey();

                if(selectedPlanElement != null) {
                    if(selectedPlanElement instanceof StateImpl) {
                        commandStack.storeAndExecute(new DeleteStateInPlan((State) selectedPlanElement,
                                planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
                    } else if (selectedPlanElement instanceof TransitionImpl) {
                        commandStack.storeAndExecute(new DeleteTransitionInPlan((Transition) selectedPlanElement,
                                planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
                    } else if (selectedPlanElement instanceof EntryPointImpl) {
                        commandStack.storeAndExecute(new DeleteEntryPointInPlan((EntryPoint) selectedPlanElement,
                                planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
                    } else if (selectedPlanElement instanceof AbstractPlan && planTab.getSelectedPlanElement().getValue().getValue() != null) {
                        State state = (State) planTab.getSelectedPlanElement().getValue().getValue().getContainedElement();
                        commandStack.storeAndExecute(new DeleteAbstractPlansFromState((AbstractPlan) selectedPlanElement, state));
                    } else if(selectedPlanElement instanceof SynchronisationImpl) {
                        commandStack.storeAndExecute(new DeleteSynchronisationFromPlan((Synchronisation) selectedPlanElement,
                                planTab.getPlanEditorPane().getPlanModelVisualisationObject()));
                    } else if (selectedPlanElement instanceof Condition) {
                        Condition condition = (Condition) planTab.getSelectedPlanElement().getValue().getKey();
                        commandStack.storeAndExecute(new DeleteConditionFromAbstractPlan(planTab.getPlanEditorPane().getPlanModelVisualisationObject().getPlan(), condition));
                    }
                    planTab.getPlanEditorPane().setupPlanVisualisation();
                    planTab.getConditionHBox().setupConditionVisualisation();
                    //selectedPlanElement
                    //

                    //commandStack.storeAndExecute();
                }
            } else if (selectedItem instanceof TaskRepositoryTab) {
                TaskRepositoryTab taskRepositoryTab = (TaskRepositoryTab) selectedItem;
                if (taskRepositoryTab.getSelectedPlanElement() != null) {
                    commandStack.storeAndExecute(new DeleteTaskFromRepository(taskRepositoryTab.getEditable(), (Task) taskRepositoryTab.getSelectedPlanElement().getValue().getKey()));
                    taskRepositoryTab.createContentView();
                }
            }
        });
        deleteElementItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        getItems().addAll(undoItem, redoItem, deleteElementItem);
        undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    }
}
