package de.uni_kassel.vs.cn.planDesigner.ui.properties;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddVariableToAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.delete.DeleteVariableFromAbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.AbstractEditorTab;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.lang.reflect.InvocationTargetException;

/**
 * Created by marci on 24.02.17.
 */
public class VariablesTab extends AbstractPropertyTab {

    private TableView<Variable> textFieldTableView;

    public VariablesTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        super(activeEditorTab, commandStack);
        setText(I18NRepo.getString("label.variables"));

    }

    @Override
    protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
        activeEditorTab.getSelectedPlanElement().addListener((observable, oldValue, newValue) -> {
            selectedPlanElement = newValue.getKey();
            if (selectedPlanElement instanceof Plan) {
                this.setDisable(false);
                textFieldTableView.setItems(FXCollections.observableArrayList(((Plan) selectedPlanElement).getVars()));
                createTabContent();
            } else {
                this.setDisable(true);
            }
        });
    }

    @Override
    protected void createTabContent() {
        textFieldTableView = new TableView<>();
        textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
        TableColumn<Variable, TextField> nameColumn = new TableColumn<>(I18NRepo.getString("label.column.name"));
        nameColumn.setCellValueFactory(new CellColumnCreatorCallback("name"));
        textFieldTableView.getColumns().add(nameColumn);
        TableColumn<Variable, TextField> typeColumn = new TableColumn<>(I18NRepo.getString("label.column.type"));
        typeColumn.setCellValueFactory(new CellColumnCreatorCallback("type"));
        textFieldTableView.getColumns().add(typeColumn);
        TableColumn<Variable, TextField> commentColumn = new TableColumn<>(I18NRepo.getString("label.column.comment"));
        commentColumn.setCellValueFactory(new CellColumnCreatorCallback("comment"));
        textFieldTableView.getColumns().add(commentColumn);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Button addButton = new Button(I18NRepo.getString("action.list.add"));
        addButton.setOnAction(e -> {
            commandStack.storeAndExecute(new AddVariableToAbstractPlan((AbstractPlan) selectedPlanElement));
            textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
        });
        Button deleteButton = new Button(I18NRepo.getString("action.list.remove"));
        deleteButton.setOnAction(e -> {
            Variable selectedItem = textFieldTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                commandStack.storeAndExecute(new DeleteVariableFromAbstractPlan(selectedItem, (AbstractPlan) selectedPlanElement));
                textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
            }
        });
        hBox.getChildren().addAll(addButton, deleteButton);
        vBox.getChildren().addAll(hBox, textFieldTableView);
        setContent(vBox);

    }

    private static class CellColumnCreatorCallback implements Callback<TableColumn.CellDataFeatures<Variable, TextField>, ObservableValue<TextField>> {

        private final String propertyName;

        public CellColumnCreatorCallback(String propertyName) {
            this.propertyName = propertyName;
        }

        @Override
        public ObservableValue<TextField> call(TableColumn.CellDataFeatures<Variable, TextField> param) {
            return new ObservableValueBase<TextField>() {
                @Override
                public TextField getValue() {
                    try {
                        return new PropertyTextField<>(param.getValue(), propertyName);
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }
    }
}
