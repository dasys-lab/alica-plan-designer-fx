package de.unikassel.vs.alica.planDesigner.view.properties;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.ParametrisationViewModel;
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

public class ParamTable extends TableView<ParametrisationViewModel> {

    private ParametrisationTab controller;

    public ParamTable() {
        I18NRepo i18NRepo = I18NRepo.getInstance();


        TableColumn<ParametrisationViewModel, String>  actionCol = new TableColumn<>();
        actionCol.setCellValueFactory(new PropertyValueFactory<>("DUMMY"));

        Callback<TableColumn<ParametrisationViewModel, String>, TableCell<ParametrisationViewModel, String>> cellFactory = new Callback<TableColumn<ParametrisationViewModel, String>, TableCell<ParametrisationViewModel, String>>() {

            @Override
            public TableCell<ParametrisationViewModel, String> call(final TableColumn<ParametrisationViewModel, String> param) {
                return new TableCell<ParametrisationViewModel, String>() {
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

        TableColumn<ParametrisationViewModel, String> varColumn = new TableColumn<>();
        varColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrisationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrisationViewModel, String> cellData) {
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

        TableColumn<ParametrisationViewModel, String> subPlanColumn = new TableColumn<>();
        subPlanColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrisationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrisationViewModel, String> cellData) {
                return new SimpleStringProperty(cellData.getValue().getSubPlan().getName());
            }
        });

        TableColumn<ParametrisationViewModel, String> subVarColumn = new TableColumn<>();
        subVarColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<ParametrisationViewModel, String>, ObservableValue<String>>() {
            @Override
            public ObservableValue<String> call(TableColumn.CellDataFeatures<ParametrisationViewModel, String> cellData) {
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

    public void setController(ParametrisationTab controller) {
        this.controller = controller;
    }
}
