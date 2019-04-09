package de.unikassel.vs.alica.planDesigner.view.properties.conditions;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IPluginEventHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.properties.variables.VariablesTable;
import javafx.beans.InvalidationListener;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Callback;
import javafx.util.StringConverter;
import javafx.util.converter.DefaultStringConverter;
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

    private ComboBox<String> pluginSelection;
    private PropertySheet properties;
    private VariablesTable<VariableViewModel> variables;
    private VariablesTable<QuantifierViewModel> quantifiers;
    private Pane pluginUI;

    private ViewModelElement parentElement;
    private AbstractPlanViewModel variablesHoldingParent;
    private ConditionViewModel condition;

    private ScrollPane hideableView;

    private final ListChangeListener<VariableViewModel> allVariablesListener;
    private final ListChangeListener<QuantifierViewModel> quantifierListener;

    public ConditionsTab(String title, String type) {
        super(title);
        this.type = type;
        pluginHandler = MainWindowController.getInstance().getConfigWindowController().getPluginEventHandler();

        allVariablesListener = c -> {
            while(c.next()) {
                for (VariableViewModel rem : c.getRemoved()) {
                    variables.removeItem(rem);
                }
                for (VariableViewModel add : c.getAddedSubList()) {
                    variables.addItem(add);
                }
            }
        };

        quantifierListener = c -> {
            while (c.next()){
                for (QuantifierViewModel rem : c.getRemoved()){
                    quantifiers.removeItem(rem);
                }
                for (QuantifierViewModel add : c.getAddedSubList()){
                    quantifiers.addItem(add);
                }
            }
        };
    }

    private void createGui() {
        this.setContent(null);
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
        properties.setSearchBoxVisible(false);
        String propertiesTitle = I18NRepo.getInstance().getString("label.caption.properties");
        TitledPane propertySection = new TitledPane(propertiesTitle, properties);

        String pluginTitle = I18NRepo.getInstance().getString("label.caption.pluginui");
        TitledPane pluginSection = new TitledPane(pluginTitle, pluginUI);
        pluginSection.setExpanded(false);

        String variablesTitle = I18NRepo.getInstance().getString("label.caption.variables");
        variables = createVariableTable();
        TitledPane variablesSection = new TitledPane(variablesTitle, variables);
        variablesSection.setExpanded(false);

        String quantifiersTitle = I18NRepo.getInstance().getString("label.caption.quantifiers");
        quantifiers = createQuantifierTable();
        TitledPane quantifierSection = new TitledPane(quantifiersTitle, quantifiers);
        quantifierSection.setExpanded(false);

        VBox vBox = new VBox(propertySection, variablesSection, quantifierSection, pluginSection);
        this.hideableView = new ScrollPane(vBox);
        hideableView.setFitToWidth(true);

        TitledPane mainPane = new TitledPane();
        mainPane.setText(I18NRepo.getInstance().getString("label.caption.selectedplugin"));
        mainPane.setContent(new VBox(selectionBox, hideableView));
        mainPane.setCollapsible(false);
        this.setContent(mainPane);
    }

    public void setViewModelElement(ViewModelElement viewModelElement){

        if(variablesHoldingParent != null) {
            variablesHoldingParent.getVariables().removeListener(allVariablesListener);
        }

        this.parentElement = viewModelElement;
        ObjectProperty<ConditionViewModel> conditionProperty;

        switch(this.type){
            case Types.PRECONDITION:
                switch (parentElement.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        PlanViewModel plan = (PlanViewModel) parentElement;
                        this.variablesHoldingParent = plan;
                        conditionProperty = plan.preConditionProperty();
                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        this.variablesHoldingParent = behaviour;
                        conditionProperty = behaviour.preConditionProperty();
                        break;

                    case Types.TRANSITION:
                        TransitionViewModel transition = (TransitionViewModel) parentElement;
                        this.variablesHoldingParent = (AbstractPlanViewModel) MainWindowController.getInstance()
                                .getGuiModificationHandler().getViewModelElement(transition.getParentId());
                        conditionProperty = transition.preConditionProperty();
                        break;
                    default:
                        condition = null;
                        this.parentElement = null;
                        this.variablesHoldingParent = null;
                        conditionProperty = null;
                }
                break;

            case Types.RUNTIMECONDITION:
                switch (parentElement.getType()){
                    case Types.PLAN:
                    case Types.MASTERPLAN:
                        PlanViewModel plan = (PlanViewModel) parentElement;
                        this.variablesHoldingParent = plan;
                        conditionProperty = plan.runtimeConditionProperty();
                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        this.variablesHoldingParent = behaviour;
                        conditionProperty = behaviour.runtimeConditionProperty();
                        break;
                    default:
                        condition = null;
                        this.parentElement = null;
                        this.variablesHoldingParent = null;
                        conditionProperty = null;
                }
                break;

            case Types.POSTCONDITION:
                switch (parentElement.getType()){

                    case Types.SUCCESSSTATE:
                    case Types.FAILURESTATE:
                        StateViewModel state = (StateViewModel) viewModelElement;
                        this.variablesHoldingParent = (AbstractPlanViewModel) MainWindowController.getInstance()
                                .getGuiModificationHandler().getViewModelElement(state.getParentId());
                        conditionProperty = state.postConditionProperty();
                        break;
                    case Types.BEHAVIOUR:
                        BehaviourViewModel behaviour = (BehaviourViewModel) parentElement;
                        this.variablesHoldingParent = behaviour;
                        conditionProperty = behaviour.posConditionProperty();
                        break;
                    default:
                        condition = null;
                        this.parentElement = null;
                        this.variablesHoldingParent = null;
                        conditionProperty = null;
                }
                break;

            default:
                condition = null;
                this.parentElement = null;
                this.variablesHoldingParent = null;
                conditionProperty = null;
        }


        createGui();
        if(conditionProperty != null) {
            setConditionAndListener(conditionProperty);
        }

        // Setup gui and listeners
        if(condition == null){
            pluginSelection.getSelectionModel().select(NONE);
        }else {
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


        if(condition != null) {
            condition.getQuantifiers().removeListener(quantifierListener);
        }


        Predicate<PropertyDescriptor> relevantProperties
                = desc -> Arrays.asList("id", "name", "comment", "enabled", "conditionString").contains(desc.getName());
        this.condition = condition;

        this.properties.getItems().clear();
        this.variables.clear();
        this.quantifiers.clear();
        if(condition != null) {
            this.properties.getItems().addAll(BeanPropertyUtils.getProperties(this.condition, relevantProperties));
            for(VariableViewModel variable : variablesHoldingParent.getVariables()){
                variables.addItem(variable);
            }
            variablesHoldingParent.getVariables().addListener(allVariablesListener);
            for(QuantifierViewModel quantifier : condition.getQuantifiers()){
                quantifiers.addItem(quantifier);
            }
            condition.getQuantifiers().addListener(quantifierListener);
            setPluginSelection(condition.getPluginName());
        }else {
            setPluginSelection(NONE);
        }
    }

    private void setPluginSelection(String pluginName){
        EventHandler<ActionEvent> handler = this.pluginSelection.getOnAction();
        this.pluginSelection.setOnAction(null);
        this.pluginSelection.getSelectionModel().select(pluginName);
        this.hideableView.setVisible(pluginName != null && !pluginName.equals(NONE));
        this.pluginSelection.setOnAction(handler);
    }

    private VariablesTable<VariableViewModel> createVariableTable(){
        VariablesTable<VariableViewModel> variablesTable = new VariablesTable<VariableViewModel>() {
            @Override
            protected void onAddElement() {
                VariableViewModel sel = variables.getSelectedItem();
                if(sel != null && !condition.getVariables().contains(sel)){
                    GuiModificationEvent event = new GuiModificationEvent(GuiEventType.ADD_ELEMENT, Types.VARIABLE, sel.getName());
                    event.setElementId(sel.getId());
                    event.setParentId(condition.getId());
                    MainWindowController.getInstance().getGuiModificationHandler().handle(event);
                }
            }

            @Override
            protected void onRemoveElement() {
                VariableViewModel sel = variables.getSelectedItem();
                if(sel != null && condition.getVariables().contains(sel)){
                    GuiModificationEvent event = new GuiModificationEvent(GuiEventType.REMOVE_ELEMENT, Types.VARIABLE, sel.getName());
                    event.setElementId(sel.getId());
                    event.setParentId(condition.getId());
                    MainWindowController.getInstance().getGuiModificationHandler().handle(event);
                }
            }
        };

        variablesTable.table.setRowFactory(param -> new TableRow<VariableViewModel>(){
            @Override
            public void updateItem(VariableViewModel item, boolean empty){
                if(condition != null) {
                    if (condition.getVariables().contains(item)) {
                        setStyle("-fx-font-weight: bold;");
                    } else {
                        setStyle("");
                    }
                    condition.getVariables().addListener((InvalidationListener) observable -> {
                        if (condition.getVariables().contains(item)) {
                            setStyle("-fx-font-weight: bold;");
                        } else {
                            setStyle("");
                        }
                    });
                }
            }
        });

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
                QuantifierViewModel selected = quantifiers.getSelectedItem();
                if(selected == null){
                    return;
                }

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.QUANTIFIER, selected.getName());
                event.setParentId(condition.getId());
                event.setElementId(selected.getId());
                MainWindowController.getInstance().getGuiModificationHandler().handle(event);
            }
        };

        I18NRepo i18NRepo = I18NRepo.getInstance();

        // Inserting a special column into the table, that holds a ComboBox instead of a TextField
        String type = i18NRepo.getString("label.column.elementType");
        TableColumn<QuantifierViewModel, String> typeColumn = new TableColumn<>(type);
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("quantifierType"));
        Callback<TableColumn<QuantifierViewModel, String>, TableCell<QuantifierViewModel, String>> typeCellFactory
                = ComboBoxTableCell.forTableColumn(new DefaultStringConverter()
                , FXCollections.observableArrayList(QuantifierViewModel.QUANTIFIER_TYPES));
        typeColumn.setCellFactory(col -> {
            TableCell<QuantifierViewModel, String> cell = typeCellFactory.call(col);
            cell.setEditable(true);
            return cell;
        });
        quantifiersTable.table.getColumns().add(typeColumn);

        // Inserting a special column into the table, that holds a ComboBox instead of a TextField
        String scope = i18NRepo.getString("label.column.scope");
        TableColumn<QuantifierViewModel, Long> scopeColumn = new TableColumn<>(scope);
        scopeColumn.setCellValueFactory(new PropertyValueFactory<>("scope"));
        Callback<TableColumn<QuantifierViewModel, Long>, TableCell<QuantifierViewModel, Long>> scopeCellFactory
                = ComboBoxTableCell.forTableColumn(
                // Custom StringConverter that allows to show the elements name in addition to its id
                new StringConverter<Long>() {
                    private IGuiModificationHandler handler = MainWindowController.getInstance().getGuiModificationHandler();
                    @Override
                    public String toString(Long l) {
                        if(l == 0) {
                            return "";
                        }
                        ViewModelElement e = handler.getViewModelElement(l);
                        return e.getName() + " (id: " + e.getId() + ")";
                    }

                    @Override
                    public Long fromString(String s) {
                        if(s.isEmpty()) {
                            return 0L;
                        }
                        int strtIdx = s.lastIndexOf(' ') + 1;
                        int endIdx  = s.lastIndexOf(')');
                        return Long.parseLong(s.substring(strtIdx, endIdx));
                    }
                }
                , this.getPossibleScopesObservableList());
        scopeColumn.setCellFactory(col -> {
            TableCell<QuantifierViewModel, Long> cell = scopeCellFactory.call(col);
            cell.setEditable(true);
            return cell;
        });
        quantifiersTable.table.getColumns().add(scopeColumn);



        quantifiersTable.addColumn(i18NRepo.getString("label.column.sorts"), "sorts"
                , new DefaultStringConverter(), true);
        quantifiersTable.addColumn(i18NRepo.getString("label.column.comment"), "comment"
                , new DefaultStringConverter(), true);

        return quantifiersTable;
    }

    private ObservableList<Long> getPossibleScopesObservableList() {
        ObservableList<Long> possibleScopes = FXCollections.observableArrayList();
        if(parentElement == null || variablesHoldingParent == null) {
            return possibleScopes;
        }

        switch (parentElement.getType()) {
            case Types.BEHAVIOUR:
                possibleScopes.add(parentElement.getId());
                break;
            case Types.PLAN:
            case Types.MASTERPLAN:
            case Types.SUCCESSSTATE:
            case Types.FAILURESTATE:
            case Types.TRANSITION:
                PlanViewModel plan = (PlanViewModel) variablesHoldingParent;
                possibleScopes.add(plan.getId());

                // Adding the plans current States and Tasks to the possibleScopes
                for(StateViewModel state : plan.getStates()) {
                    possibleScopes.add(state.getId());
                }
                for(EntryPointViewModel entryPoint : plan.getEntryPoints()) {
                    if(!possibleScopes.contains(entryPoint.getId())) {
                        possibleScopes.add(entryPoint.getId());
                    }
                }

                // Adding Listeners to keep the possibleScopes updated
                plan.getStates().addListener((ListChangeListener<? super StateViewModel>) c -> {
                    while(c.next()) {
                        for(StateViewModel state : c.getAddedSubList()) {
                            possibleScopes.add(state.getId());
                        }
                        for(StateViewModel state : c.getRemoved()) {
                            possibleScopes.remove(state.getId());
                        }
                    }
                });
                plan.getEntryPoints().addListener((ListChangeListener<? super EntryPointViewModel>) c -> {
                    while(c.next()) {
                        for(EntryPointViewModel entryPoint : c.getAddedSubList()) {
                            if(!possibleScopes.contains(entryPoint.getId())) {
                                possibleScopes.add(entryPoint.getId());
                            }
                        }
                        for(EntryPointViewModel entryPoint : c.getRemoved()) {
                            possibleScopes.remove(entryPoint.getId());
                        }
                    }
                });
        }

        return possibleScopes;
    }
}
