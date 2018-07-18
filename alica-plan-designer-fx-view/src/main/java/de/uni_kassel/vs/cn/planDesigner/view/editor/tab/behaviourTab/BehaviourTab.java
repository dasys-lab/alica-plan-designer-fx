package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiChangeAttributeEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.ConditionsTitledPane;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.IEditorTab;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.VariablesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

public class BehaviourTab extends AbstractPlanTab implements IEditorTab {

    PropertiesTable<BehaviourViewModel> propertiesTable;
    VariablesTab variablesTab;
    VBox contentList;
    BehaviourViewModel behaviourViewModel;
    I18NRepo i18NRepo;
    IGuiModificationHandler guiModificationHandler;

    public BehaviourTab(BehaviourViewModel behaviourViewModel) {
        super(behaviourViewModel);
        setText(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + behaviourViewModel.getName());
        i18NRepo = I18NRepo.getInstance();
        this.behaviourViewModel = behaviourViewModel;

        // Properties
        propertiesTable = new PropertiesTable();
        propertiesTable.setEditable(true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.frequency"), "frequency", new IntegerStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.deferring"), "deferring", new LongStringConverter(), true);
        propertiesTable.addItem(behaviourViewModel);

        // Variables
        variablesTab = new VariablesTab();
        for (VariableViewModel variableViewModel : behaviourViewModel.getVariables()) {
            variablesTab.addItem(variableViewModel);
        }
        TabPane variablesTabPane = new TabPane();
        variablesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        variablesTabPane.getTabs().addAll(variablesTab);

        // Behaviours Conditions
        ConditionsTitledPane preConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.preCondtions"));
        ConditionsTitledPane runtimeConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.runtimeCondtions"));
        ConditionsTitledPane postConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.postCondtions"));

        contentList = new VBox();
        contentList.getChildren().addAll(propertiesTable, variablesTabPane, preConditionTabPane, runtimeConditionTabPane, postConditionTabPane);
        setContent(contentList);
    }

    public void init(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
        behaviourViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue, "name", String.class.getSimpleName());
        });
        behaviourViewModel.commentProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue, "comment", String.class.getSimpleName());
        });
        behaviourViewModel.frequencyProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue.toString(), "frequency", Integer.class.getSimpleName());
        });
        behaviourViewModel.deferringProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue.toString(), "deferring", Long.class.getSimpleName());
        });
    }

    private void fireModificationEvent(String newValue, String attributeName, String type) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.BEHAVIOUR, behaviourViewModel
                .getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(type);
        guiChangeAttributeEvent.setAttributeName(attributeName);
        guiChangeAttributeEvent.setParentId(behaviourViewModel.getId());
        guiModificationHandler.handle(guiChangeAttributeEvent);
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
    public ViewModelElement getPresentedViewModelElement() {
        return behaviourViewModel;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("BehaviourTab: HandleDelete is a no-op for behaviour tabs!");
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.BEHAVIOUR, presentedViewModelElement.getName());
            event.setElementId(presentedViewModelElement.getId());
            guiModificationHandler.handle(event);
        }
    }

    public void updateText(String newName) {
        this.setText(i18NRepo.getString("label.caption.behaviour") + ": " + newName);
        setDirty(true);
    }
}
