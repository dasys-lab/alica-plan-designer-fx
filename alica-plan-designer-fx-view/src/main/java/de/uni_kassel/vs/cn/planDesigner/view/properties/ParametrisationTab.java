package de.uni_kassel.vs.cn.planDesigner.view.properties;

import de.uni_kassel.vs.cn.planDesigner.alica.Parametrisation;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Variable;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractEditorTab;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ObservableValueBase;
import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class ParametrisationTab extends AbstractPropertyTab {
    private static final Logger LOG = LogManager.getLogger(ParametrisationTab.class);
    private TableView<Parametrisation> parametrisationTableView;
    private State currentState;

    /**
     *
     * @param activeEditorTab
     * @param commandStack
     */
    public ParametrisationTab(AbstractEditorTab<PlanElement> activeEditorTab, CommandStack commandStack) {
        super(activeEditorTab, commandStack);
        setText(i18NRepo.getString("label.parametrisation"));
    }

    /**
     *
     */
    @Override
    protected void createTabContent() {
        parametrisationTableView = new TableView<>();
        if (getSelectedEditorTabPlanElement() instanceof State) {
            currentState = (State) getSelectedEditorTabPlanElement();
            if (currentState.getParametrisation().size() == 0) {
                currentState.getPlans()
                        .forEach(abstractPlan -> AlicaModelUtils.addParametrisations(abstractPlan, currentState));
            }
            this.setDisable(false);
        } else {
            currentState = null;
            this.setDisable(true);
        }

        TableColumn<Parametrisation, Label> subPlanColumn = new TableColumn<>(i18NRepo.getString("label.column.subplan"));
        subPlanColumn.setCellValueFactory(new ParametrisationTab.CellColumnLabelCreatorCallback("subplan.name"));
        parametrisationTableView.getColumns().add(subPlanColumn);
        TableColumn<Parametrisation, Label> subVariableColumn = new TableColumn<>(i18NRepo.getString("label.column.subVariable"));
        subVariableColumn.setCellValueFactory(new ParametrisationTab.CellColumnLabelCreatorCallback("subvar.name"));
        parametrisationTableView.getColumns().add(subVariableColumn);
        TableColumn<Parametrisation, ComboBox<Variable>> varColumn = new TableColumn<>(i18NRepo.getString("label.column.subVariableValue"));
        varColumn.setCellValueFactory(new ParametrisationTab.CellColumnCreatorCallback());
        parametrisationTableView.getColumns().add(varColumn);
        if (currentState != null) {
            parametrisationTableView.setItems(FXCollections.observableArrayList(currentState.getParametrisation()));
        }
        setContent(parametrisationTableView);
    }

    private class CellColumnCreatorCallback implements Callback<TableColumn.CellDataFeatures<Parametrisation, ComboBox<Variable>>, ObservableValue<ComboBox<Variable>>> {

        @Override
        public ObservableValue<ComboBox<Variable>> call(TableColumn.CellDataFeatures<Parametrisation, ComboBox<Variable>> param) {
            return new ObservableValueBase<ComboBox<Variable>>() {
                @Override
                public ComboBox<Variable> getValue() {
                    List<Variable> vars = new ArrayList<>();
                    vars.addAll(currentState.getInPlan().getVars());
                    vars.add(null);
                    ComboBox<Variable> comboBox = new ComboBox<>();
                    comboBox.getSelectionModel().select(param.getValue().getVar());
                    comboBox.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Variable>() {
                        @Override
                        public void changed(ObservableValue<? extends Variable> observable, Variable oldValue, Variable newValue) {
                            commandStack.storeAndExecute(
                                    new ChangeAttributeValue<>(param.getValue(), "var", newValue, currentState.getInPlan()));
                            //param.getValue().setVar(newValue);
                            //selectedElementContainerProperty().getValue().getValue().setupContainer();
                        }
                    });
                    comboBox.setItems(FXCollections.observableArrayList(vars));
                    comboBox.setButtonCell(new ListCell<Variable>() {
                        @Override
                        protected void updateItem(Variable item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                setText(item.getName());
                            } else {
                                setText(i18NRepo.getString("label.combobox.param.empty"));
                            }
                        }
                    });
                    comboBox.setCellFactory(new Callback<ListView<Variable>, ListCell<Variable>>() {
                        @Override
                        public ListCell<Variable> call(ListView<Variable> param) {
                            return new ListCell<Variable>() {
                                @Override
                                protected void updateItem(Variable item, boolean empty) {
                                    super.updateItem(item, empty);
                                    if (item != null) {
                                        setText(item.getName());
                                    } else {
                                        setText(i18NRepo.getString("label.combobox.param.empty"));
                                    }
                                }
                            };
                        }
                    });
                    return comboBox;
                }
            };
        }
    }

    private class CellColumnLabelCreatorCallback implements Callback<TableColumn.CellDataFeatures<Parametrisation, Label>, ObservableValue<Label>> {

        private final String propertyName;

        public CellColumnLabelCreatorCallback(String propertyName) {
            this.propertyName = propertyName;
        }

        @Override
        public ObservableValue<Label> call(TableColumn.CellDataFeatures<Parametrisation, Label> param) {
            return new ObservableValueBase<Label>() {
                @Override
                public Label getValue() {
                    try {
                        return new PropertyLabel<>(param.getValue(), propertyName);
                    } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
                        LOG.error("An error occured while creating a label for parametrisation", e);
                        throw new RuntimeException(e);
                    }
                }
            };
        }
    }
}
