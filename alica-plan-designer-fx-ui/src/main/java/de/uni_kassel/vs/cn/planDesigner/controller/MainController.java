package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteAbstractPlansFromState;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteEntryPointInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteStateInPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteTransitionInPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.EntryPointImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.StateImpl;
import de.uni_kassel.vs.cn.planDesigner.alica.impl.TransitionImpl;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.PLDFileTreeView;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.properties.PropertyTabPane;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryTabPane;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Created by marci on 16.10.16.
 */
public class MainController implements Initializable {

    private static MainController MAIN_CONTROLLER;

    @FXML
    private PLDFileTreeView fileTreeView;

    @FXML
    private PropertyTabPane propertyAndStatusTabPane;

    @FXML
    RepositoryTabPane repositoryTabPane;

    @FXML
    private EditorTabPane editorTabPane;

    @FXML
    private MenuBar menuBar;

    private CommandStack commandStack = new CommandStack();

    public MainController() {
        super();
        MAIN_CONTROLLER = this;
    }

    public static MainController getInstance() {
        return MAIN_CONTROLLER;
    }

    public void initialize(URL location, ResourceBundle resources) {
        fileTreeView.setController(this);
        editorTabPane.setCommandStack(commandStack);
        editorTabPane.getTabs().clear();
        repositoryTabPane.init();
        menuBar.getMenus().addAll(createMenus());
        propertyAndStatusTabPane.init(editorTabPane);
    }

    /**
     *
     * @return
     */
    private List<Menu> createMenus() {
        List<Menu> menus = new ArrayList<>();
        Menu fileMenu = new Menu(I18NRepo.getInstance().getString("label.menu.file"));
        fileMenu.getItems().add(new MenuItem(I18NRepo.getInstance().getString("label.menu.file.newPlan")));
        menus.add(fileMenu);
        Menu editMenu = new Menu(I18NRepo.getInstance().getString("label.menu.edit"));
        MenuItem deleteElementItem = new MenuItem(I18NRepo.getInstance().getString("label.menu.edit.delete"));
        deleteElementItem.setOnAction(event -> {
            PlanTab planTab = (PlanTab) editorTabPane.getSelectionModel().getSelectedItem();
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
                } else if (planTab.getSelectedPlanElement().getValue().getValue() != null) {
                    State state = (State) planTab.getSelectedPlanElement().getValue().getValue().getContainedElement();
                    commandStack.storeAndExecute(new DeleteAbstractPlansFromState((AbstractPlan) selectedPlanElement, state));
                }
                planTab.getPlanEditorPane().setupPlanVisualisation();
                    //selectedPlanElement
                    //

                //commandStack.storeAndExecute();
            }
        });
        deleteElementItem.setAccelerator(new KeyCodeCombination(KeyCode.DELETE));
        editMenu.getItems().add(deleteElementItem);
        menus.add(editMenu);
        return menus;
    }

    /**
     * delegate to {@link EditorTabPane#openTab(java.nio.file.Path)}
     *
     * @param toOpen
     */
    public void openFile(File toOpen) {
        try {
            editorTabPane.openTab(toOpen.toPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }

    public EditorTabPane getEditorTabPane() {
        return editorTabPane;
    }
}
