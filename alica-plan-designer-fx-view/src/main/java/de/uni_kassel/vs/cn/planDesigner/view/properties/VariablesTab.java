package de.uni_kassel.vs.cn.planDesigner.view.properties;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.*;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractEditorTab;
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

public class VariablesTab extends AbstractPropertyTab {

    private TableView<Variable> textFieldTableView;


    public VariablesTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        super(activeEditorTab, commandStack);
        setText(i18NRepo.getInstance().getString("label.variables"));
    }

    @Override
    protected void addListenersForActiveTab(AbstractEditorTab<PlanElement> activeEditorTab) {
        activeEditorTab.getSelectedPlanElements().addListener((observable, oldValue, newValue) -> {
            // TODO check fo single
            selectedPlanElement = newValue.get(0).getKey();
            if (selectedPlanElement instanceof Plan && selectedPlanElement == activeEditorTab.getEditable()) {
                this.setDisable(false);
                textFieldTableView.setItems(FXCollections.observableArrayList(((Plan) selectedPlanElement).getVars()));
                createTabContent();
            } else if (selectedPlanElement instanceof Behaviour) {
                this.setDisable(false);
                textFieldTableView.setItems(FXCollections.observableArrayList(((Behaviour) selectedPlanElement).getVars()));
                createTabContent();
            } else if(selectedPlanElement instanceof Condition) {
                // TODO make this work! Conditions have no own variables. They hold references to that of plans or behaviours
                this.setDisable(true);
                /*this.setDisable(false);
                textFieldTableView.setItems(FXCollections.observableArrayList(((Condition) selectedPlanElements).getVars()));
                createTabContent();*/
            } else {
                this.setDisable(true);
                getContent().setDisable(true);
            }
        });
    }

    @Override
    protected void createTabContent() {
        textFieldTableView = new TableView<>();
        if (selectedPlanElement instanceof Plan) {
            textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
        } else if (selectedPlanElement instanceof Behaviour) {
            textFieldTableView.setItems(FXCollections.observableArrayList(((Behaviour)selectedPlanElement).getVars()));
        } else if(selectedPlanElement instanceof Condition) {
            textFieldTableView.setItems(FXCollections.observableArrayList(((Condition)selectedPlanElement).getVars()));
        }

        TableColumn<Variable, TextField> nameColumn = new TableColumn<>(i18NRepo.getString("label.column.name"));
        nameColumn.setCellValueFactory(new CellColumnCreatorCallback("name"));
        textFieldTableView.getColumns().add(nameColumn);
        TableColumn<Variable, TextField> typeColumn = new TableColumn<>(i18NRepo.getString("label.column.type"));
        typeColumn.setCellValueFactory(new CellColumnCreatorCallback("type"));
        textFieldTableView.getColumns().add(typeColumn);
        TableColumn<Variable, TextField> commentColumn = new TableColumn<>(i18NRepo.getString("label.column.comment"));
        commentColumn.setCellValueFactory(new CellColumnCreatorCallback("comment"));
        textFieldTableView.getColumns().add(commentColumn);

        VBox vBox = new VBox();
        HBox hBox = new HBox();
        Button addButton = new Button(i18NRepo.getString("action.list.add"));
        addButton.setOnAction(e -> {
            if (selectedPlanElement instanceof AbstractPlan) {
                commandStack.storeAndExecute(new AddVariableToAbstractPlan(selectedPlanElement));
                if (selectedPlanElement instanceof Plan) {
                    textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
                } else if (selectedPlanElement instanceof Behaviour) {
                    textFieldTableView.setItems(FXCollections.observableArrayList(((Behaviour)selectedPlanElement).getVars()));
                }
            } else if (selectedPlanElement instanceof Condition) {
                commandStack.storeAndExecute(new AddVariableToCondition((Condition) selectedPlanElement,
                        (Plan) activeEditorTab.getEditable()));
                textFieldTableView.setItems(FXCollections.observableArrayList(((Condition)selectedPlanElement).getVars()));
            }

        });

        Button deleteButton = new Button(i18NRepo.getString("action.list.remove"));
        deleteButton.setOnAction(e -> {
            Variable selectedItem = textFieldTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                if (selectedPlanElement instanceof Plan) {
                    commandStack.storeAndExecute(new DeleteVariableFromAbstractPlan(selectedItem, selectedPlanElement));
                    textFieldTableView.setItems(FXCollections.observableArrayList(((Plan)selectedPlanElement).getVars()));
                }

                if (selectedPlanElement instanceof Behaviour) {
                    commandStack.storeAndExecute(new DeleteVariableFromAbstractPlan(selectedItem, selectedPlanElement));
                    textFieldTableView.setItems(FXCollections.observableArrayList(((Behaviour)selectedPlanElement).getVars()));
                }

                if (selectedPlanElement instanceof Condition) {
                    commandStack.storeAndExecute(new DeleteVariableFromCondition(selectedItem,
                            (Condition) selectedPlanElement, (Plan) activeEditorTab.getEditable()));
                    textFieldTableView.setItems(FXCollections.observableArrayList(((Condition)selectedPlanElement).getVars()));
                }
            }

        });
        hBox.getChildren().addAll(addButton, deleteButton);
        vBox.getChildren().addAll(hBox, textFieldTableView);
        setContent(vBox);

    }

    private class CellColumnCreatorCallback implements Callback<TableColumn.CellDataFeatures<Variable, TextField>, ObservableValue<TextField>> {

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
                        PropertyTextField<Variable> variablePropertyTextField = new PropertyTextField<>(param.getValue(), propertyName, commandStack);
                        variablePropertyTextField.focusedProperty().addListener((observable, oldValue, newValue) -> {
                            if(newValue) {
                                param.getTableView().getSelectionModel().select(param.getValue());
                            }
                        });
                        return variablePropertyTextField;
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            };
        }
    }
}
