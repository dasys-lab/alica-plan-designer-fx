package de.unikassel.vs.alica.planDesigner.view.editor.tab.behaviourTab;

import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.ConditionsTitledPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.IEditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.VariablesTab;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.VariableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
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

    public BehaviourTab(ViewModelElement viewModelElement,IGuiModificationHandler guiModificationHandler) {
        super(viewModelElement, guiModificationHandler);
        setText(I18NRepo.getInstance().getString("label.caption.behaviour") + ": " + viewModelElement.getName());
        i18NRepo = I18NRepo.getInstance();
        this.behaviourViewModel = (BehaviourViewModel) viewModelElement;

        // Properties
        propertiesTable = new PropertiesTable();
        propertiesTable.setEditable(true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.frequency"), "frequency", new IntegerStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.deferring"), "deferring", new LongStringConverter(), true);
        propertiesTable.addItem(this.behaviourViewModel);

        // Variables
        variablesTab = new VariablesTab(viewModelElement);
        for (VariableViewModel variableViewModel : this.behaviourViewModel.getVariables()) {
            variablesTab.addItem(variableViewModel);
        }
        TabPane variablesTabPane = new TabPane();
        variablesTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);
        variablesTabPane.getTabs().addAll(variablesTab);

        contentList = new VBox();
        contentList.getChildren().addAll(propertiesTable, variablesTabPane);

        // Behaviours Conditions
        if (this.behaviourViewModel.getPreCondition() != null) {
            ConditionsTitledPane preConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.preCondtions"), this.behaviourViewModel.getPreCondition());
            contentList.getChildren().addAll(preConditionTabPane);
        }
        if (this.behaviourViewModel.getRuntimeCondition() != null) {
            ConditionsTitledPane runtimeConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.runtimeCondtions"), this.behaviourViewModel.getRuntimeCondition());
            contentList.getChildren().addAll(runtimeConditionTabPane);
        }
        if (this.behaviourViewModel.getPostCondition() != null) {
            ConditionsTitledPane postConditionTabPane = new ConditionsTitledPane(i18NRepo.getString("label.caption.postCondtions"), this.behaviourViewModel.getPostCondition());
            contentList.getChildren().addAll(postConditionTabPane);
        }

        setContent(contentList);

        this.behaviourViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue, "name", String.class.getSimpleName());
        });
        this.behaviourViewModel.commentProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue, "comment", String.class.getSimpleName());
        });
        this.behaviourViewModel.frequencyProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireModificationEvent(newValue.toString(), "frequency", Integer.class.getSimpleName());
        });
        this.behaviourViewModel.deferringProperty().addListener((observable, oldValue, newValue) -> {
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
        guiChangeAttributeEvent.setElementId(behaviourViewModel.getId());
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
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.BEHAVIOUR, viewModelElement.getName());
            event.setElementId(viewModelElement.getId());
            guiModificationHandler.handle(event);
        }
    }

    public void updateText(String newName) {
        this.setText(i18NRepo.getString("label.caption.behaviour") + ": " + newName);
        setDirty(true);
    }
}
