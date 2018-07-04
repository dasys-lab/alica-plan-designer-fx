package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryHBox;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Pair;

import java.net.URL;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class PlanTypeWindowController implements Initializable {

    private ViewModelElement planType;

    private Comparator<Pair<ViewModelElement, Path>> pairComparator;

    private Comparator<RepositoryHBox> repositoryHBoxComparator;

    private Comparator<ViewModelElement> annotatedPlanComparator;

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


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        initComparators();
        initUIText();
        initPlanListView();
        initTableView();
        initButtons();
    }

    public void refresh() {
        planTypeTableView.refresh();
    }

    private void initPlanListView() {
        //TODO get list from modelmanager
//        List<RepositoryHBox> allPlans = RepositoryViewModel
//                .getInstance()
//                .getPlans()
//                .stream()
//                .sorted(pairComparator)
//                .map(e -> {
//                    RepositoryHBox planRepositoryHBox = new RepositoryHBox(e.getKey(), e.getValue());
//                    planRepositoryHBox.setOnMouseClicked(null);
//                    return planRepositoryHBox;
//                })
//                .collect(Collectors.toList());
//        planListView.setItems(FXCollections.observableArrayList(allPlans));
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
//                planTypeTableView.getItems().add(command.getCopyForRemoval());
                planTypeTableView.getItems().sort(annotatedPlanComparator);
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

    private void initTableView() {
        planTypeTableView.getItems().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {
                    List<? extends ViewModelElement> addedSubList = c.getAddedSubList();
                    //TODO get items from event
//                    addedSubList
//                            .forEach(e -> {
//                                List<RepositoryHBox> toRemove = planListView
//                                        .getItems()
//                                        .stream()
//                                        .filter(f -> f.getObject().equals(e.getPlan()))
//                                        .collect(Collectors.toList());
//                                toRemove.forEach(rH -> planListView.getItems().remove(rH));
//                            });
                } else if (c.wasRemoved()) {
                    List<? extends ViewModelElement> removedSubList = c.getRemoved();
                    //TODO get items from event
//                    removedSubList
//                            .forEach(e -> {
//                                Pair<TreeViewModelElement, Path> planPathPair = RepositoryViewModel
//                                        .getInstance()
//                                        .getPlans()
//                                        .stream()
//                                        .filter(f -> f.getKey().equals(e.getPlan()))
//                                        .findFirst().get();
//                                RepositoryHBox e1 = new RepositoryHBox(planPathPair.getKey(), planPathPair.getValue());
//                                e1.setOnMouseClicked(null);
//                                planListView.getItems().add(e1);
//                            });
                }
            }
        });

        TableColumn<ViewModelElement, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setResizable(false);
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("activated"));
        activeColumn.setCellFactory(new Callback<TableColumn<ViewModelElement, Boolean>, TableCell<ViewModelElement, Boolean>>() {
            @Override
            public TableCell<ViewModelElement, Boolean> call(TableColumn<ViewModelElement, Boolean> param) {
                TableCell<ViewModelElement, Boolean> annotatedPlanBooleanTableCell = new TableCell<ViewModelElement, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            if (Boolean.TRUE.equals(item)) {
                                setGraphic(new ImageView(new AlicaIcon("SuccessState")));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon("FailureState")));
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
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>("plan"));
        planNameColumn.setCellFactory(new Callback<TableColumn<ViewModelElement, ViewModelElement>, TableCell<ViewModelElement, ViewModelElement>>() {
            @Override
            public TableCell<ViewModelElement, ViewModelElement> call(TableColumn<ViewModelElement, ViewModelElement> param) {
                TableCell<ViewModelElement, ViewModelElement> planNameTableCell = new TableCell<ViewModelElement, ViewModelElement>() {
                    @Override
                    protected void updateItem(ViewModelElement item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            if (item.getType().equals(Types.MASTERPLAN)) {
                                setGraphic(new ImageView(new AlicaIcon("masterplan")));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon("plan")));
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


    private void initComparators() {
        pairComparator = Comparator.comparing(planPathPair -> !planPathPair.getKey().getType().equals(Types.MASTERPLAN));
        pairComparator = pairComparator.thenComparing(planPathPair -> planPathPair.getKey().getName());
        repositoryHBoxComparator = Comparator.comparing(planRepositoryHBox -> !planRepositoryHBox.getViewModelType().equals(Types.MASTERPLAN));
        repositoryHBoxComparator = repositoryHBoxComparator.thenComparing(planRepositoryHBox -> planRepositoryHBox.getViewModelName());
        annotatedPlanComparator = Comparator.comparing(annotatedPlan -> !annotatedPlan.getType().equals(Types.MASTERPLAN));
        annotatedPlanComparator = annotatedPlanComparator.thenComparing(annotatedPlan -> annotatedPlan.getName());
    }

    public void setPlanType(ViewModelElement planType) {
        if (planType == null) {
            throw new RuntimeException("Empty PlanType BULLSHIT!");
        }
        this.planType = planType;
        //TODO get event here
//        planTypeTableView.getItems().addAll(planType.getPlans());
    }

    public void setPlanTypeTab(PlanTypeTab planTypeTab) {this.planTypeTab = planTypeTab; }
}
