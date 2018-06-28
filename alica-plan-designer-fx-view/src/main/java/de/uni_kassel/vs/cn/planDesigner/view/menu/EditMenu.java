package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.PlanDesignerApplication;
import de.uni_kassel.vs.cn.planDesigner.controller.ConfigurationWindowController;
import de.uni_kassel.vs.cn.planDesigner.controller.MainWindowController;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryTabPane;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.io.IOException;

public class EditMenu extends Menu {

    private MenuItem deleteElementItem;
    private MenuItem undoItem;
    private MenuItem redoItem;
    private final MenuItem configItem;
    private I18NRepo i18NRepo;
    private Stage configStage;

    private ConfigurationWindowController configWindowController;

    public EditMenu(EditorTabPane editorTabPane, ConfigurationWindowController configWindowController) {
        super(I18NRepo.getInstance().getString("label.menu.edit"));
        this.configWindowController = configWindowController;
        i18NRepo = I18NRepo.getInstance();

        deleteElementItem = new MenuItem(i18NRepo.getString("label.menu.edit.delete"));
        deleteElementItem.setDisable(true);
        deleteElementItem.setOnAction(event -> delete(editorTabPane));

        undoItem = new MenuItem(i18NRepo.getString("label.menu.edit.undo"));
        undoItem.setDisable(true);
        undoItem.setOnAction(event -> undo(editorTabPane));

        redoItem = new MenuItem(i18NRepo.getString("label.menu.edit.redo"));
        redoItem.setDisable(true);
        redoItem.setOnAction(event -> redo(editorTabPane));

        configItem = new MenuItem(i18NRepo.getString("label.menu.edit.config"));
        configItem.setOnAction(event -> openConfigMenu());

        getItems().addAll(undoItem, redoItem, deleteElementItem, configItem);
        defineAccelerator();
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack
    public void setUndoDisabled(boolean value) {
        undoItem.setDisable(value);
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack
    public void setRedoItemDisabled(boolean value) {
        redoItem.setDisable(value);
    }

    // TODO: call this methods from the controller - reacting to changes in the commandstack and selected treeviewitem
    public void setDeleteItemDisabled(boolean value) {
        deleteElementItem.setDisable(value);
    }

    private void undo(EditorTabPane editorTabPane) {
//        commandStack.undo();
//        Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
//        if (selectedItem instanceof PlanTab) {
//            ((PlanTab) selectedItem).getPlanEditorGroup().setupPlanVisualisation();
//            ((PlanTab) selectedItem).getConditionHBox().setupConditionVisualisation();
//        } else if (selectedItem instanceof PlanTypeTab) {
//            ((PlanTypeTab) selectedItem).refresh();
//        } else if (selectedItem instanceof TaskRepositoryTab) {
//            ((TaskRepositoryTab) selectedItem).createContentView();
//        }
    }

    private void redo(EditorTabPane editorTabPane) {
//        commandStack.redo();
//        Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
//        if (selectedItem instanceof PlanTab) {
//            ((PlanTab) selectedItem).getPlanEditorGroup().setupPlanVisualisation();
//            ((PlanTab) selectedItem).getConditionHBox().setupConditionVisualisation();
//        } else if (selectedItem instanceof PlanTypeTab) {
//            ((PlanTypeTab) selectedItem).refresh();
//        } else if (selectedItem instanceof TaskRepositoryTab) {
//            ((TaskRepositoryTab) selectedItem).createContentView();
//        }
    }

    private void openConfigMenu() {
        if (configStage == null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("configurationWindow.fxml"));
            fxmlLoader.setController(this.configWindowController);
            Parent window;
            try {
                 window = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            configStage = new Stage();
            configStage.setResizable(false);
            configStage.setTitle(i18NRepo.getString("label.config.title"));
            configStage.setScene(new Scene(window));
            configStage.initOwner(PlanDesignerApplication.getPrimaryStage());
        }
        configStage.show();
        configStage.toFront();
    }

    private void delete(EditorTabPane editorTabPane) {
        // TODO refactor
        MainWindowController mainWindowController = MainWindowController.getInstance();
        RepositoryTabPane repositoryTabPane = mainWindowController.getRepositoryTabPane();

        //
        if (editorTabPane.focusedProperty().get()) {
            Tab selectedItem = editorTabPane.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }

            // TODO: callback on editMenuEventHandler
            // Plan Element in Plan Editor selected
//            if (selectedItem instanceof PlanTab) {
//                PlanTab planTab = (PlanTab) selectedItem;
//                PlanElement selectedPlanElement = planTab.getSelectedPlanElements().get().get(0).getKey();
//
//                if (selectedPlanElement != null) {
//                    deletePlanElement(commandStack, planTab, selectedPlanElement);
//                }
//            }

            // TaskRepository in editorTabPane selected
//            if (selectedItem instanceof TaskRepositoryTab) {
//                TaskRepositoryTab repositoryTab = (TaskRepositoryTab) selectedItem;
//                Task taskToBeDeleted = repositoryTab
//                        .getTaskListView()
//                        .getSelectionModel()
//                        .getSelectedItem();
//                checkTaskUsage(commandStack, repositoryTab.getEditable(), taskToBeDeleted);
//            }
            return;
        }

        // RepositoryView selected
//        boolean isRepoFocused = repositoryTabPane.getTabs().stream()
//                .anyMatch(e -> ((RepositoryTab) e).getRepositoryListView().focusedProperty().get());

//        if (isRepoFocused) {
//            int selectedTabIndex = repositoryTabPane.getSelectionModel().getSelectedIndex();
//            PlanElement selectedPlanElement = ((RepositoryTab) repositoryTabPane
//                    .getSelectionModel()
//                    .getSelectedItem())
//                    .getRepositoryListView()
//                    .getSelectionModel().getSelectedItem().getObject();
//            editorTabPane.getTabs()
//                    .stream()
//                    .filter(e -> ((AbstractEditorTab<PlanElement>) e).getEditable().equals(selectedPlanElement))
//                    .forEach(e -> editorTabPane.getTabs().remove(e));
//            if (selectedPlanElement instanceof AbstractPlan) {
//                checkAbstractPlanUsage(commandStack, (AbstractPlan) selectedPlanElement);
//            } else if (selectedPlanElement instanceof Task) {
//                List<Pair<TaskRepository, Path>> taskRepositories = RepositoryViewModel.getInstance().getTaskRepository();
//                TaskRepository taskRepository = null;
//                for (Pair<TaskRepository, Path> pair : taskRepositories) {
//                    if (pair.getKey().getTasks().contains((Task) selectedPlanElement)) {
//                        taskRepository = pair.getKey();
//                        break;
//                    }
//                }
//                if (taskRepository != null) {
//                    checkTaskUsage(commandStack, taskRepository, (Task) selectedPlanElement);
//                }
//            }
//            repositoryTabPane.init();
//            repositoryTabPane.getSelectionModel().select(selectedTabIndex);
//            return;
//        }

        // FileTreeView selected
        if (mainWindowController.getFileTreeView().focusedProperty().get()) {
            DeleteFileMenuItem deleteFileMenuItem = new DeleteFileMenuItem(mainWindowController.getFileTreeView()
                    .getSelectionModel()
                    .getSelectedItem()
                    .getValue());
            deleteFileMenuItem.deleteFile();
            return;

        }
    }

//    private void checkTaskUsage(TaskRepository taskRepository, Task taskToBeDeleted) {
//        List<AbstractPlan> usages = EMFModelUtils.getUsages(taskToBeDeleted);
//        if (usages.size() > 0) {
//            FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
//            try {
//                Parent infoWindow = fxmlLoader.load();
//                UsagesWindowController controller = fxmlLoader.getController();
//                controller.createReferencesList(usages);
//                Stage stage = new Stage();
//                stage.setTitle(i18NRepo.getString("label.usage.nodelete"));
//                stage.setScene(new Scene(infoWindow));
//                stage.initModality(Modality.WINDOW_MODAL);
//                stage.initOwner(PlanDesigner.getPrimaryStage());
//                stage.showAndWait();
//            } catch (IOException ignored) {
//            }
//        } else {
//            commandStack.storeAndExecute(new DeleteTaskFromRepository(taskRepository, taskToBeDeleted));
//            MainWindowController.getInstance().closePropertyAndStatusTabIfOpen();
//
//        }
//    }

//    private void checkAbstractPlanUsage(AbstractPlan toBeDeleted) {
//        List<AbstractPlan> usages = EMFModelUtils.getUsages(toBeDeleted);
//        if (usages.size() > 0) {
//            FXMLLoader fxmlLoader = new FXMLLoader(ShowUsagesMenuItem.class.getClassLoader().getResource("usagesWindow.fxml"));
//            try {
//                Parent infoWindow = fxmlLoader.load();
//                UsagesWindowController controller = fxmlLoader.getController();
//                controller.createReferencesList(usages);
//                Stage stage = new Stage();
//                stage.setTitle(i18NRepo.getString("label.usage.nodelete"));
//                stage.setScene(new Scene(infoWindow));
//                stage.initModality(Modality.WINDOW_MODAL);
//                stage.initOwner(PlanDesigner.getPrimaryStage());
//                stage.showAndWait();
//            } catch (IOException ignored) {
//            }
//        } else {
//            commandStack.storeAndExecute(new DeleteAbstractPlan(toBeDeleted));
//        }
//    }

//    private void deletePlanElement(PlanTab planTab, PlanElement selectedPlanElement) {
//        if (selectedPlanElement instanceof StateImpl) {
//            commandStack.storeAndExecute(new DeleteStateInPlan((State) selectedPlanElement,
//                    planTab.getPlanEditorGroup().getPlanModelVisualisationObject()));
//        } else if (selectedPlanElement instanceof TransitionImpl) {
//            commandStack.storeAndExecute(new DeleteTransitionInPlan((Transition) selectedPlanElement,
//                    planTab.getPlanEditorGroup().getPlanModelVisualisationObject()));
//        } else if (selectedPlanElement instanceof EntryPointImpl) {
//            commandStack.storeAndExecute(new DeleteEntryPointInPlan((EntryPoint) selectedPlanElement,
//                    planTab.getPlanEditorGroup().getPlanModelVisualisationObject())); // TODO ask single * 3
//        } else if (selectedPlanElement instanceof AbstractPlan && planTab.getSelectedPlanElements().get().get(0).getValue() != null) {
//            State state = (State) planTab.getSelectedPlanElements().getValue().get(0).getValue().getModelElementId();
//            commandStack.storeAndExecute(new DeleteAbstractPlansFromState((AbstractPlan) selectedPlanElement, state));
//        } else if (selectedPlanElement instanceof SynchronisationImpl) {
//            commandStack.storeAndExecute(new DeleteSynchronisationFromPlan((Synchronisation) selectedPlanElement,
//                    planTab.getPlanEditorGroup().getPlanModelVisualisationObject()));
//        } else if (selectedPlanElement instanceof Condition) {
//            Condition condition = (Condition) planTab.getSelectedPlanElements().getValue().get(0).getKey();
//            commandStack.storeAndExecute(new DeleteConditionFromPlan(planTab.getPlanEditorGroup().getPlanModelVisualisationObject().getPlan(), condition));
//        }
//        planTab.getPlanEditorGroup().setupPlanVisualisation();
//        planTab.getConditionHBox().setupConditionVisualisation();
//    }

    private void defineAccelerator() {
        this.deleteElementItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        this.undoItem.setAccelerator(new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN));
        this.redoItem.setAccelerator(new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN));
    }
}
