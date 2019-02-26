package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IPluginEventHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.beans.property.ObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;
import org.controlsfx.control.PropertySheet;
import org.controlsfx.property.BeanPropertyUtils;

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;


public class ConditionsTab extends Tab {

    private static final String NONE = "NONE";

    private final String type;
    private final IPluginEventHandler pluginHandler;

    private final  ComboBox<String> pluginSelection;
    private final PropertySheet properties;
    private final VariablesTable<VariableViewModel> variables;
    private final VariablesTable<QuantifierViewModel> quantifiers;
    private final Pane pluginUI;

    private ViewModelElement parentElement;
    private ConditionViewModel condition;

    private final ScrollPane hidableView;

    public ConditionsTab(String title, String type){
        super(title);
        this.type = type;
        pluginHandler = MainWindowController.getInstance().getConfigWindowController().getPluginEventHandler();

        pluginUI = new Pane();
        pluginSelection = new ComboBox<>();
        List<String> availablePlugins = pluginHandler.getAvailablePlugins();
        pluginSelection.getItems().add(NONE);
        pluginSelection.getItems().addAll(availablePlugins);
        pluginSelection.getItems();
        pluginSelection.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override public ListCell<String> call(ListView<String> param) {
                return new ListCell<String>() {
                    {
                        super.setPrefWidth(100);
                    }
                    @Override public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(item);
                        if(NONE.equals(item)){
                            setFont(Font.font(Font.getDefault().getFamily(), FontPosture.ITALIC, Font.getDefault().getSize()));
                        }
                    }
                };
            }
        });

        Label label = new Label(I18NRepo.getInstance().getString("label.column.pluginName"));
        label.setMinWidth(150);
        HBox selectionBox = new HBox(label, pluginSelection);
        selectionBox.setSpacing(5);
        selectionBox.setPadding(new Insets(5));

        properties = new PropertySheet();
        properties.setModeSwitcherVisible(false);
        String propertiesTitle = I18NRepo.getInstance().getString("label.caption.properties");
        TitledPane propertySection = new TitledPane(propertiesTitle, properties);

        String pluginTitle = I18NRepo.getInstance().getString("label.caption.pluginui");
        TitledPane pluginSection = new TitledPane(pluginTitle, pluginUI);
        pluginSection.setExpanded(false);

        String variablesTitle = I18NRepo.getInstance().getString("label.caption.variables");
        variables = createVariableTable();
        variables.setMinHeight(100);
        TitledPane variablesSection = new TitledPane(variablesTitle, variables);
        variablesSection.setExpanded(false);

        String quantifiersTitle = I18NRepo.getInstance().getString("label.caption.quantifiers");
        quantifiers = createQuantifierTable();
        TitledPane quantifierSection = new TitledPane(quantifiersTitle, quantifiers);
        quantifierSection.setExpanded(false);

        VBox vBox = new VBox(propertySection, pluginSection, variablesSection, quantifierSection);
        this.hidableView = new ScrollPane(vBox);
        hidableView.setFitToWidth(true);

        TitledPane mainPane = new TitledPane();
        mainPane.setText(I18NRepo.getInstance().getString("label.caption.selectedplugin"));
        mainPane.setContent(new VBox(selectionBox, hidableView));
        mainPane.setCollapsible(false);
        this.setContent(mainPane);
    }

    public void setViewModelElement(ViewModelElement viewModelElement){
        this.parentElement = viewModelElement;

        switch(this.type){
            case Types.PRECONDITION:
                switch (parentElement.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        PlanViewModel plan = (PlanViewModel) parentElement;
                        setConditionAndListener(plan.preConditionProperty());
                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        setConditionAndListener(behaviour.preConditionProperty());
                        break;
                    default:
                        condition = null;
                }
                break;

            case Types.RUNTIMECONDITION:
                switch (parentElement.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        PlanViewModel plan = (PlanViewModel) parentElement;
                        setConditionAndListener(plan.runtimeConditionProperty());
                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        setConditionAndListener(behaviour.runtimeConditionProperty());
                        break;
                    default:
                        condition = null;
                }
                break;

            case Types.POSTCONDITION:
                switch (parentElement.getType()){
// TODO: Find a way to get the postconditions of success- and failurestates from the viewmodel
//                    case Types.SUCCESSSTATE:
//                    case Types.FAILURESTATE:
//                        StateViewModel state = (StateViewModel) viewModelElement;
//                        condition = state.???
//                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        setConditionAndListener(behaviour.posConditionProperty());
                        break;
                    default:
                        condition = null;
                }
                break;

            default:
                condition = null;
        }


        // Setup gui and listeners
        if(condition == null){
            pluginSelection.getSelectionModel().select(NONE);
        }else{
            pluginSelection.getSelectionModel().select(condition.getPluginName());
            updateGuiOnChange(condition.getPluginName());
        }


        pluginSelection.setOnAction(event -> {
            String newValue = pluginSelection.getValue();
            updateModelOnChange(newValue);
            updateGuiOnChange(newValue);
        });
    }

    private void updateGuiOnChange(String newPlugin){
        pluginUI.getChildren().clear();
        if(newPlugin != null && !newPlugin.equals(NONE)){
            try {
                pluginUI.getChildren().add(pluginHandler.getPluginUI(newPlugin));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateModelOnChange(String newPlugin){
        IGuiModificationHandler controller = MainWindowController.getInstance().getGuiModificationHandler();


        if(newPlugin != null && !newPlugin.equals(NONE)){

            GuiModificationEvent addNewCondition
                    = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, type, "");
            addNewCondition.setParentId(parentElement.getId());
            addNewCondition.setName(newPlugin);

            controller.handle(addNewCondition);
        }
        else if(condition != null) {

            GuiModificationEvent removeOldCondition
                    = new GuiModificationEvent(GuiEventType.REMOVE_ELEMENT, type, condition.getName());
            removeOldCondition.setParentId(parentElement.getId());
            removeOldCondition.setElementId(condition.getId());

            controller.handle(removeOldCondition);
        }
    }

    private void setConditionAndListener(ObjectProperty<ConditionViewModel> property){

        // Set the current value
        setCondition(property.get());

        // Update for new values
        property.addListener((observable, oldValue, newValue) -> setCondition(newValue));
    }

    private void setCondition(ConditionViewModel condition){
        Predicate<PropertyDescriptor> relevantProperties
                = desc -> Arrays.asList("id", "name", "comment", "enabled", "conditionString").contains(desc.getName());
        this.condition = condition;

        this.properties.getItems().clear();
        this.variables.clear();
        this.quantifiers.clear();
        if(condition != null) {
            this.properties.getItems().addAll(BeanPropertyUtils.getProperties(this.condition, relevantProperties));
            for(VariableViewModel variable : condition.getVariables()){
                variables.addItem(variable);
            }
            condition.getVariables().addListener((ListChangeListener<VariableViewModel>) c -> {
                while(c.next()) {
                    for (VariableViewModel rem : c.getRemoved()) {
                        variables.removeItem(rem);
                    }
                    for (VariableViewModel add : c.getAddedSubList()) {
                        variables.addItem(add);
                    }
                }
            });
            for(QuantifierViewModel quantifier : condition.getQuantifier()){
                quantifiers.addItem(quantifier);
            }
            setPluginSelection(condition.getPluginName());
        }else {
            setPluginSelection(NONE);
        }
    }

    private void setPluginSelection(String pluginName){
        EventHandler<ActionEvent> handler = this.pluginSelection.getOnAction();
        this.pluginSelection.setOnAction(null);
        this.pluginSelection.getSelectionModel().select(pluginName);
        this.hidableView.setVisible(pluginName != null && !pluginName.equals(NONE));
        this.pluginSelection.setOnAction(handler);
    }

    private VariablesTable<VariableViewModel> createVariableTable(){
        VariablesTable<VariableViewModel> variablesTable = new VariablesTable<VariableViewModel>() {
            @Override
            protected void onAddElement() {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.VARIABLE, "NEW_VARIABLE");
                event.setParentId(condition.getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }

            @Override
            protected void onRemoveElement() {
                VariableViewModel selected = variables.getSelectedItem();
                if(selected == null){
                    return;
                }

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.VARIABLE, selected.getName());
                event.setParentId(condition.getId());
                event.setElementId(selected.getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }
        };

        I18NRepo i18NRepo = I18NRepo.getInstance();
        variablesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        variablesTable.addColumn(i18NRepo.getString("label.column.elementType"), "variableType", new DefaultStringConverter(), true);
        variablesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);

        return variablesTable;
    }

    private VariablesTable<QuantifierViewModel> createQuantifierTable(){
        VariablesTable<QuantifierViewModel> quantifiersTable = new VariablesTable<QuantifierViewModel>() {
            @Override
            protected void onAddElement() {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.QUANTIFIER, "NEW_QUANTIFIER");
                event.setParentId(condition.getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }

            @Override
            protected void onRemoveElement() {
                // TODO
            }
        };

        I18NRepo i18NRepo = I18NRepo.getInstance();
        quantifiersTable.addColumn(i18NRepo.getString("label.column.elementType"), "quantifierType", new DefaultStringConverter(), true);
        quantifiersTable.addColumn(i18NRepo.getString("label.column.scope"), "scope", new LongStringConverter(), true);
        quantifiersTable.addColumn(i18NRepo.getString("label.column.sorts"), "sorts", new DefaultStringConverter(), true);
        quantifiersTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);

        return quantifiersTable;
    }
}
