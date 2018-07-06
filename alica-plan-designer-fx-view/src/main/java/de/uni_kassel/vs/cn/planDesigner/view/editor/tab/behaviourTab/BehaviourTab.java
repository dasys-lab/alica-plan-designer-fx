package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class BehaviourTab extends Tab implements IEditorTab {

    BehaviourPropertiesTab behaviourPropertiesTab;
    VariablesTab variablesTab;
    VBox contentList;
    BehaviourViewModel behaviourViewModel;
    I18NRepo i18NRepo;

    public BehaviourTab(BehaviourViewModel behaviourViewModel) {
        super(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + behaviourViewModel.getName());
        i18NRepo = I18NRepo.getInstance();
        this.behaviourViewModel = behaviourViewModel;

        // fill properties
        behaviourPropertiesTab = new BehaviourPropertiesTab();
        behaviourPropertiesTab.addItem(behaviourViewModel);

        // fill variables tab
        variablesTab = new VariablesTab();
        for (VariableViewModel variableViewModel : behaviourViewModel.getVariables()) {
            variablesTab.addItem(variableViewModel);
        }

        // Add Properties and Variables into TabPane
        TabPane tabPane = new TabPane();
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        tabPane.getTabs().addAll(behaviourPropertiesTab, variablesTab);

        // Behaviours Conditions
        BehaviourConditionTitledPane preConditionTabPane = new BehaviourConditionTitledPane(behaviourViewModel.getPreCondition(), i18NRepo.getString("label.caption.preCondtion"));
        BehaviourConditionTitledPane runtimeConditionTabPane = new BehaviourConditionTitledPane(behaviourViewModel.getRuntimeCondition(), i18NRepo.getString("label.caption.runtimeCondtion"));
        BehaviourConditionTitledPane postConditionTabPane = new BehaviourConditionTitledPane(behaviourViewModel.getPostCondition(), i18NRepo.getString("label.caption.postCondtion"));

        contentList = new VBox();
        contentList.getChildren().addAll(tabPane, preConditionTabPane, runtimeConditionTabPane, postConditionTabPane);
        setContent(contentList);
    }

    @Override
    public boolean representsViewModelElement(ViewModelElement element) {
        if (this.behaviourViewModel.equals(element) || this.behaviourViewModel.getId() == element.getParentId()) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ViewModelElement getViewModelElement() {
        return behaviourViewModel;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete is a no-op for behaviour tabs!");
        return null;
    }

    @Override
    public void save() {
        System.err.println("BehaviourTab: Save not implemented, yet!");
    }
}
