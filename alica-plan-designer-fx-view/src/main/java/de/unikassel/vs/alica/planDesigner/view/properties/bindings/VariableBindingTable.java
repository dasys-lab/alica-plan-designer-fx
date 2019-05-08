package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;

import java.util.ArrayList;

public abstract class VariableBindingTable extends VBox {

    public PropertiesTable<VariableBindingViewModel> table;
    I18NRepo i18NRepo;
    private ComboBox<VariableViewModel> varDropDown;
    private ComboBox<AbstractPlanViewModel> subPlanDropDown;
    private ComboBox<VariableViewModel> subVarDropDown;
    private Button addButton;
    private Button deleteButton;

    public VariableBindingTable() {
        super();
        this.i18NRepo = I18NRepo.getInstance();

        // LABELS, DROPDOWNS, ADD Button
        Label varLabel = new Label(i18NRepo.getString("label.caption.variable") + ":");
        varLabel.setPadding(new Insets(0,0,0,5));
        varDropDown = new ComboBox<VariableViewModel>();
        varDropDown.setConverter(new VariableStringConverter());

        Label subPlanLabel = new Label (i18NRepo.getString("label.column.subplan") + ":");
        subPlanLabel.setPadding(new Insets(0,0,0,15));
        subPlanDropDown = new ComboBox<AbstractPlanViewModel>();
        subPlanDropDown.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            this.fillSubVariableDropDown(newValue);
        });
        subPlanDropDown.setConverter(new HasVariablesViewStringConverter());

        Label subVarLabel = new Label (i18NRepo.getString("label.column.subvariable") + ":");
        subVarLabel.setPadding(new Insets(0,0,0,15));
        subVarDropDown = new ComboBox<VariableViewModel>();
        subVarDropDown.setConverter(new VariableStringConverter());

        addButton = new Button();
        addButton.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.ADD, AlicaIcon.Size.SMALL)));
        addButton.setOnAction(event -> {
            onAddElement();
        });

        deleteButton = new Button();
        deleteButton.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.REMOVE, AlicaIcon.Size.SMALL)));
        deleteButton.setOnAction(event -> {
            onRemoveElement();
        });
        Separator sep = new Separator();
        sep.setOrientation(Orientation.VERTICAL);
        HBox dropDownHBox = new HBox(varLabel, varDropDown, subPlanLabel, subPlanDropDown, subVarLabel, subVarDropDown,sep, addButton, deleteButton);
        dropDownHBox.setAlignment(Pos.CENTER_LEFT);
        dropDownHBox.setSpacing(5);
        dropDownHBox.setPadding(new Insets(3,0,3,0));

        // TABLE
        table = new PropertiesTable<>();
        table.setEditable(false);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.clear();

        this.getChildren().addAll(dropDownHBox, table);
    }

    private void fillSubVariableDropDown(AbstractPlanViewModel hasVariablesView) {
        this.subVarDropDown.getItems().clear();
        if (hasVariablesView != null) {
            this.subVarDropDown.getItems().addAll(hasVariablesView.getVariables());
        }
    }

    public void setVariablesDropDownContent(ObservableList<VariableViewModel> variables) {
        this.varDropDown.getItems().clear();
        if (variables != null) {
            this.varDropDown.getItems().addAll(variables);
        }
    }

    public void setSubPlanDropDownContent(ArrayList<AbstractPlanViewModel> hasVariablesViewArrayList) {
        this.subPlanDropDown.getItems().clear();
        if (hasVariablesViewArrayList != null) {
            this.subPlanDropDown.getItems().addAll(hasVariablesViewArrayList);
        }
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

    public AbstractPlanViewModel getSelectedSubPlan() {
        return subPlanDropDown.getSelectionModel().getSelectedItem();
    }

    protected abstract void onAddElement();

    protected abstract void onRemoveElement();

}
