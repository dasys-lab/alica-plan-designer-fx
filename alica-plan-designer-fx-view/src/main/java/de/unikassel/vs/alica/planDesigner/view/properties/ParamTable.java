package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.view.model.ParametrizationViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class ParamTable extends TableView<ParametrizationViewModel> {

    public ParamTable() {
        TableColumn<ParametrizationViewModel, String> varColumn = new TableColumn<>();
        varColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrizationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrizationViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getVariable().getName());
            }
        });

        TableColumn<ParametrizationViewModel, String> subPlanColumn = new TableColumn<>();
        subPlanColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrizationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrizationViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getSubPlan().getName());
            }
        });

        TableColumn<ParametrizationViewModel, String> subVarColumn = new TableColumn<>();
        subVarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrizationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrizationViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getSubVariable().getName());
            }
        });

        this.getColumns().add(0, varColumn);
        this.getColumns().add(1, subPlanColumn);
        this.getColumns().add(2, subVarColumn);
    }

}
