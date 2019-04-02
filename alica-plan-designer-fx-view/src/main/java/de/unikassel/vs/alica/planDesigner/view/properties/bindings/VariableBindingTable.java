package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.HashMap;

public abstract class VariableBindingTable extends VBox {

    public PropertiesTable<VariableBindingViewModel> table;
    I18NRepo i18NRepo;
    private ComboBox<VariableViewModel> varDropDown;
    private ComboBox<HasVariablesView> subPlanDropDown;
    private ComboBox<VariableViewModel> subVarDropDown;
    private Button addButton;

    public VariableBindingTable() {
        super();
        this.i18NRepo = I18NRepo.getInstance();

        // LABELS, DROPDOWNS, ADD Button
        Label varLabel = new Label(i18NRepo.getString("label.caption.variable") + ":");
        varDropDown = new ComboBox<VariableViewModel>();

        Label subPlanLabel = new Label (i18NRepo.getString("label.column.subplan") + ":");
        subPlanDropDown = new ComboBox<HasVariablesView>();
        subPlanDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.fillSubVariableDropDown(newValue);
        });

        Label subVarLabel = new Label (i18NRepo.getString("label.column.subVariable") + ":");
        subVarDropDown = new ComboBox<VariableViewModel>();

        addButton = new Button();
        addButton.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.ADD, AlicaIcon.Size.SMALL)));
        addButton.setOnAction(event -> {
            onAddElement();
        });
        HBox dropDownHBox = new HBox(varLabel, varDropDown, subPlanLabel, subPlanDropDown, subVarLabel, subVarDropDown, addButton);

        // TABLE
        table = new PropertiesTable<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        this.getChildren().addAll(dropDownHBox, table);
    }

    private void fillSubVariableDropDown(HasVariablesView hasVariablesView) {
        this.subVarDropDown.getItems().clear();
        ObservableList<VariableViewModel> variables = hasVariablesView.getVariables();
        for (VariableViewModel variable : variables) {
            this.subVarDropDown.getItems().add(variable);
        }
    }

    public void fillVariablesDropDown(ObservableList<VariableViewModel> variables) {
        this.varDropDown.getItems().clear();
        for (VariableViewModel variable : variables) {
            this.varDropDown.getItems().add(variable);
        }
    }



    private void createAlert() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Variable Binding!");
        alert.setContentText(i18NRepo.getString("label.error.invalidVariableBinding"));

        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"));

        alert.getButtonTypes().setAll(closeBtn);

        alert.initOwner(PlanDesignerApplication.getPrimaryStage());
        alert.showAndWait();
    }

    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable) {
        table.addColumn(title, propertyName, converter, editable);
    }

    public void addItem(VariableBindingViewModel viewModel) {
        table.addItem(viewModel);
    }

    public void removeItem(VariableBindingViewModel viewModel) {
        table.removeItem(viewModel);
    }

    public void clear() {
        this.table.clear();
        // clear drop downs
        this.varDropDown.getItems().clear();
        this.subVarDropDown.getItems().clear();
        this.subPlanDropDown.getItems().clear();
    }

    public VariableBindingViewModel getSelectedItem() {
        return table.getSelectionModel().getSelectedItem();
    }

    public VariableViewModel getSelectedVariable() {
        return varDropDown.getSelectionModel().getSelectedItem();
    }

    public VariableViewModel getSelectedSubVariable() {
        return subVarDropDown.getSelectionModel().getSelectedItem();
    }

    public HasVariablesView getSelectedSubPlan() {
        return subPlanDropDown.getSelectionModel().getSelectedItem();
    }

    protected abstract void onAddElement();

    protected abstract void onRemoveElement();

}
