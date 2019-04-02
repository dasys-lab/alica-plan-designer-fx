package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.VariableBindingViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

public class VariableBindingTable extends TableView<VariableBindingViewModel> {

    private VariableBindingTab controller;

    public VariableBindingTable() {
        I18NRepo i18NRepo = I18NRepo.getInstance();


        TableColumn<VariableBindingViewModel, String>  actionCol = new TableColumn<>();
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<VariableBindingViewModel, String>, TableCell<VariableBindingViewModel, String>> cellFactory = new Callback<TableColumn<VariableBindingViewModel, String>, TableCell<VariableBindingViewModel, String>>() {

            @Override
            public TableCell<VariableBindingViewModel, String> call(final TableColumn<VariableBindingViewModel, String> param) {
                return new TableCell<VariableBindingViewModel, String>() {
                    final Button button = new Button();

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        button.setGraphic(new ImageView(new AlicaIcon(AlicaIcon.REMOVE, AlicaIcon.Size.SMALL)));
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            button.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                    controller.deleteParametrisation(getTableView().getItems().get(getIndex()).getId());

                                }
                            });
                            setGraphic(button);
                            setText(null);
                            setAlignment(Pos.CENTER);
                        }
                    }
                };
            }
        };

        actionCol.setCellFactory(cellFactory);

        TableColumn<VariableBindingViewModel, String> varColumn = new TableColumn<>();
        varColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<VariableBindingViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<VariableBindingViewModel, String> cellData) {
                String name = null;
                switch (controller.getElement().getType()) {
                    case Types.PLANTYPE:
                        name = controller.getComplexPlanVariableName(cellData.getValue().getVariable());
                        break;
                    case Types.STATE:
                        name = cellData.getValue().getVariable().getName();
                        break;
                    default:
                        name = "Error";
                }
                return new SimpleStringProperty(name);
            }
        });

        TableColumn<VariableBindingViewModel, String> subPlanColumn = new TableColumn<>();
        subPlanColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<VariableBindingViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<VariableBindingViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getSubPlan().getName());
            }
        });

        TableColumn<VariableBindingViewModel, String> subVarColumn = new TableColumn<>();
        subVarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<VariableBindingViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<VariableBindingViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getSubVariable().getName());
            }
        });

        varColumn.setText(i18NRepo.getString("label.caption.variables"));
        subVarColumn.setText(i18NRepo.getString("label.column.subVariable"));
        subPlanColumn.setText(i18NRepo.getString("label.column.subplan"));

        this.getColumns().add(0, actionCol);
        this.getColumns().add(1, varColumn);
        this.getColumns().add(2, subPlanColumn);
        this.getColumns().add(3, subVarColumn);
    }

    public void setController(VariableBindingTab controller) {
        this.controller = controller;
    }
}
