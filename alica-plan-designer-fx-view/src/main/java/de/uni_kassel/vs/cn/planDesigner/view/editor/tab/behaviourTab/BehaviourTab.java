package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.QuantifierTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.KeyValuePropertiesTab;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

public class BehaviourTab extends Tab implements IEditorTab {

    KeyValuePropertiesTab keyValuePropertiesTab;
    VariablesTab variablesTab;
    VBox contentList;
    BehaviourViewModel behaviourViewModel;

    public BehaviourTab(BehaviourViewModel behaviourViewModel) {
        super(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + behaviourViewModel.getName());
        this.behaviourViewModel = behaviourViewModel;

        keyValuePropertiesTab = new KeyValuePropertiesTab();
        variablesTab = new VariablesTab();

        TabPane tabPane = new TabPane();
        tabPane.getTabs().addAll(keyValuePropertiesTab, variablesTab);
        contentList = new VBox();
        contentList.getChildren().addAll(tabPane);
        setContent(contentList);

        // fill variables tab
        for (VariableViewModel variableViewModel : behaviourViewModel.getVariables()) {
            variablesTab.addItem(variableViewModel);
        }

        // fill key value tab
        keyValuePropertiesTab.addKeyValueProperty("Name", behaviourViewModel.getName(), true);
        keyValuePropertiesTab.addKeyValueProperty("ID", String.valueOf(behaviourViewModel.getId()), false);
        keyValuePropertiesTab.addKeyValueProperty("Comment", String.valueOf(behaviourViewModel.getComment()), true);
        keyValuePropertiesTab.addKeyValueProperty("Relative Directory", String.valueOf(behaviourViewModel.getRelativeDirectory()), false);
        keyValuePropertiesTab.addKeyValueProperty("Frequency", String.valueOf(behaviourViewModel.getFrequency()), true);
        keyValuePropertiesTab.addKeyValueProperty("Delay", String.valueOf(behaviourViewModel.getDelay()), true);
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
