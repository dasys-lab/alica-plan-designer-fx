package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryHBox;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PlanTypeWindowController implements Initializable {

    private ViewModelElement planType;

    private Comparator<RepositoryHBox> repositoryHBoxComparator;

    private Comparator<ViewModelElement> viewModelElementComparator;

    private ObservableList<ViewModelElement> allPlans;

    @FXML
    private PlanTypeTab planTypeTab;

    @FXML
    private Button removePlanButton;

    @FXML
    private Button addPlanButton;

    @FXML
    private Button removeAllPlansButton;

    @FXML
    private TableView<ViewModelElement> planTypeTableView;

    @FXML
    private ListView<RepositoryHBox> planListView;

    @FXML
    private Button saveButton;

    private I18NRepo i18NRepo;

    private IGuiModificationHandler guiModificationHandler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        initComparators();
        initUIText();
        initButtons();
    }

    public void refresh() {
        planTypeTableView.refresh();
    }

    private void initButtons() {
        saveButton.setOnAction(e -> {
                planTypeTab.setText(planTypeTab.getText().replace("*",""));
                //TODO
//                EMFModelUtils.saveAlicaFile(planType);
//                commandStack.setSavedForAbstractPlan(planType);
        });

        addPlanButton.setOnAction(e -> {
            RepositoryHBox selectedItem = planListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                //TODO fire event here
//                TreeViewModelElement command = new AddPlanToPlanType(selectedItem.getObject(), planType);
//                commandStack.storeAndExecute(command);
                ViewModelElement element = selectedItem.getViewModelElement();
                planTypeTableView.getItems().add(element);
                planTypeTableView.getItems().sort(viewModelElementComparator);
            }
        });

        removePlanButton.setOnAction(e -> {
            ViewModelElement selectedItem = planTypeTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                //TODO fire event here
//                commandStack.storeAndExecute(new RemovePlanFromPlanType(selectedItem, planType));
                planTypeTableView.getItems().remove(selectedItem);
                planTypeTableView.refresh();
                planListView.getItems().sort(repositoryHBoxComparator);
            }
        });

        removeAllPlansButton.setOnAction(e -> {
            //TODO fire event here
//            commandStack.storeAndExecute(new RemoveAllPlansFromPlanType(planType));
            planTypeTableView.getItems().clear();
            planTypeTableView.refresh();
            planListView.getItems().sort(repositoryHBoxComparator);
        });
    }

    private void initUIText() {
        removePlanButton.setText(i18NRepo.getString("label.plantype.removePlan"));
        removeAllPlansButton.setText(i18NRepo.getString("label.plantype.removeAllPlans"));
        addPlanButton.setText(i18NRepo.getString("label.plantype.addPlan"));
        saveButton.setText(i18NRepo.getString("action.save"));
    }

    public void initTableView(ObservableList<ViewModelElement> plansInPlanType) {
        planTypeTableView.getItems().addAll(plansInPlanType);
        planTypeTableView.getItems().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {
                    List<? extends ViewModelElement> addedSubList = c.getAddedSubList();

                    for(ViewModelElement element : addedSubList) {
                        planListView.getItems().remove(element);
                    }

                } else if (c.wasRemoved()) {
                    List<? extends ViewModelElement> removedSubList = c.getRemoved();
                    for (ViewModelElement element : removedSubList) {
                        RepositoryHBox e1 = new RepositoryHBox(element, guiModificationHandler);
                        e1.setOnMouseClicked(null);
                        planListView.getItems().add(e1);
                    }
                }
            }
        });

        TableColumn<ViewModelElement, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setResizable(false);
        activeColumn.setCellValueFactory(new PropertyValueFactory<>(i18NRepo.getString("label.column.activated")));
        activeColumn.setCellFactory(new Callback<TableColumn<ViewModelElement, Boolean>, TableCell<ViewModelElement, Boolean>>() {
            @Override
            public TableCell<ViewModelElement, Boolean> call(TableColumn<ViewModelElement, Boolean> param) {
                TableCell<ViewModelElement, Boolean> annotatedPlanBooleanTableCell = new TableCell<ViewModelElement, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            if (Boolean.TRUE.equals(item)) {
                                setGraphic(new ImageView(new AlicaIcon(Types.SUCCESSSTATE)));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon(Types.FAILURESTATE)));
                            }
                            setText("");
                        }
                    }
                };
                annotatedPlanBooleanTableCell.setStyle("-fx-alignment: CENTER;");
                return annotatedPlanBooleanTableCell;
            }
        });

        TableColumn<ViewModelElement, ViewModelElement> planNameColumn = new TableColumn<>(i18NRepo.getString("label.column.planName"));
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>(Types.PLAN));
        planNameColumn.setCellFactory(new Callback<TableColumn<ViewModelElement, ViewModelElement>, TableCell<ViewModelElement, ViewModelElement>>() {
            @Override
            public TableCell<ViewModelElement, ViewModelElement> call(TableColumn<ViewModelElement, ViewModelElement> param) {
                TableCell<ViewModelElement, ViewModelElement> planNameTableCell = new TableCell<ViewModelElement, ViewModelElement>() {
                    @Override
                    protected void updateItem(ViewModelElement item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false ) {
                            if (item.getType().equals(Types.MASTERPLAN)) {
                                setGraphic(new ImageView(new AlicaIcon(Types.MASTERPLAN)));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon(Types.PLAN)));
                            }
                            setText(item.getName());
                        }
                    }
                };
                return planNameTableCell;
            }
        });

        planTypeTableView.getColumns().add(activeColumn);
        planTypeTableView.getColumns().add(planNameColumn);
        planTypeTableView.setRowFactory(tv -> {
            TableRow<ViewModelElement> annotatedPlanTableRow = new TableRow<>();
            annotatedPlanTableRow.setOnMouseClicked(e -> {
                if(e.getClickCount() == 2 && annotatedPlanTableRow.getItem() != null) {
                    //TODO fire event
//                    commandStack.storeAndExecute(new ChangeAttributeValue<>(annotatedPlanTableRow.getItem(),
//                            "activated", !annotatedPlanTableRow.getItem().isActivated(), planType));
                    planTypeTableView.refresh();
                }
            });
            return annotatedPlanTableRow;
        });
    }

    public void initPlanListView(ObservableList<ViewModelElement> allPlans) {
        this.allPlans = allPlans;
        for(ViewModelElement plan : this.allPlans) {
            RepositoryHBox planRepositoryHBox = new RepositoryHBox(plan, guiModificationHandler);
            planRepositoryHBox.setOnMouseClicked(null);
            planListView.getItems().add(planRepositoryHBox);
        }
    }


    private void initComparators() {
        repositoryHBoxComparator = Comparator.comparing(planRepositoryHBox -> !planRepositoryHBox.getViewModelType().equals(Types.MASTERPLAN));
        repositoryHBoxComparator = repositoryHBoxComparator.thenComparing(planRepositoryHBox -> planRepositoryHBox.getViewModelName());
        viewModelElementComparator = Comparator.comparing(annotatedPlan -> !annotatedPlan.getType().equals(Types.MASTERPLAN));
        viewModelElementComparator = viewModelElementComparator.thenComparing(annotatedPlan -> annotatedPlan.getName());
    }

    public void setPlanType(ViewModelElement planType) {
        if (planType == null) {
            throw new RuntimeException("PlanTypeWindowController: Empty PlanType was set!");
        }
        this.planType = planType;
    }

    public void setPlanTypeTab(PlanTypeTab planTypeTab) {this.planTypeTab = planTypeTab; }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }
}
