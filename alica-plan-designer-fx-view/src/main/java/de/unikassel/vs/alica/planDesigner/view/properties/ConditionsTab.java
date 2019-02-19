package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IPluginEventHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ConditionViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.beans.property.ObjectProperty;
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

        TitledPane section0 = new TitledPane();
        section0.setContent(properties);
        section0.setText(I18NRepo.getInstance().getString("label.caption.properties"));

        TitledPane section1 = new TitledPane();
        section1.setContent(pluginUI);
        section1.setText(I18NRepo.getInstance().getString("label.caption.pluginui"));
        section1.setExpanded(false);

        VBox vBox = new VBox(section0, section1);
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
        Predicate<PropertyDescriptor> relevantProperties
                = desc -> Arrays.asList("id", "name", "comment", "enabled", "conditionString").contains(desc.getName());

        // Set the current value
        this.condition = property.get();

        this.properties.getItems().clear();
        if(condition != null) {
            this.properties.getItems().addAll(BeanPropertyUtils.getProperties(this.condition, relevantProperties));
            setPluginSelection(condition.getPluginName());
        }else {
            setPluginSelection(NONE);
        }

        // Update for new values
        property.addListener((observable, oldValue, newValue) -> {

            this.condition = newValue;

            this.properties.getItems().clear();
            if(condition != null) {
                this.properties.getItems().addAll(BeanPropertyUtils.getProperties(this.condition, relevantProperties));
                setPluginSelection(condition.getPluginName());
            }else {
                setPluginSelection(NONE);
            }
        });
    }

    private void setPluginSelection(String pluginName){
        EventHandler<ActionEvent> handler = this.pluginSelection.getOnAction();
        this.pluginSelection.setOnAction(null);
        this.pluginSelection.getSelectionModel().select(pluginName);
        this.hidableView.setVisible(pluginName != null && !pluginName.equals(NONE));
        this.pluginSelection.setOnAction(handler);
    }
}
