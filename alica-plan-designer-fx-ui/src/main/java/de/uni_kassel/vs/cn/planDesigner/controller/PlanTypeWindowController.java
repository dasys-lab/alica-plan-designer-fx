package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.command.add.AddPlanToPlanType;
import de.uni_kassel.vs.cn.planDesigner.command.change.ChangeAttributeValue;
import de.uni_kassel.vs.cn.planDesigner.command.delete.RemoveAllPlansFromPlanType;
import de.uni_kassel.vs.cn.planDesigner.command.delete.RemovePlanFromPlanType;
import de.uni_kassel.vs.cn.planDesigner.alica.*;
import de.uni_kassel.vs.cn.planDesigner.alica.util.RepoViewBackend;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.common.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.ui.repo.RepositoryHBox;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * Created by marci on 17.03.17.
 */
public class PlanTypeWindowController implements Initializable {

    private PlanType planType;

    private CommandStack commandStack;

    private Comparator<Pair<Plan, Path>> pairComparator;

    private Comparator<RepositoryHBox<Plan>> repositoryHBoxComparator;

    @FXML
    private PlanTypeTab planTypeTab;

    @FXML
    private Button removePlanButton;

    @FXML
    private Button addPlanButton;

    @FXML
    private Button removeAllPlansButton;

    @FXML
    private TableView<AnnotatedPlan> plantypeTableView;

    @FXML
    private ListView<RepositoryHBox<Plan>> planListView;

    @FXML
    private Button saveButton;

    private I18NRepo i18NRepo;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        pairComparator = Comparator.comparing(planPathPair -> !planPathPair.getKey().isMasterPlan());
        pairComparator = pairComparator.thenComparing(planPathPair -> planPathPair.getKey().getName());
        repositoryHBoxComparator = Comparator.comparing(planRepositoryHBox -> !planRepositoryHBox.getObject().isMasterPlan());
        repositoryHBoxComparator = repositoryHBoxComparator.thenComparing(planRepositoryHBox -> planRepositoryHBox.getObject().getName());
        initUIText();
        initPlanListView();
        initTableView();
        initButtons();
    }

    public void refresh() {
        plantypeTableView.refresh();
    }

    private void initPlanListView() {
        List<RepositoryHBox<Plan>> allPlans = RepoViewBackend
                .getInstance()
                .getPlans()
                .stream()
                .sorted(pairComparator)
                .map(e -> {
                    RepositoryHBox<Plan> planRepositoryHBox = new RepositoryHBox<>(e.getKey(), e.getValue());
                    planRepositoryHBox.setOnMouseClicked(null);
                    return planRepositoryHBox;
                })
                .collect(Collectors.toList());
        planListView.setItems(FXCollections.observableArrayList(allPlans));
    }

    private void initButtons() {
        saveButton.setOnAction(e -> {
            try {
                planTypeTab.setText(planTypeTab.getText().replace("*",""));
                EMFModelUtils.saveAlicaFile(planType);
                commandStack.setSavedForAbstractPlan(planType);
            } catch (IOException e1) {
                throw new RuntimeException(e1);
            }
        });

        addPlanButton.setOnAction(e -> {
            RepositoryHBox<Plan> selectedItem = planListView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                AddPlanToPlanType command = new AddPlanToPlanType(selectedItem.getObject(), planType);
                commandStack.storeAndExecute(command);
                plantypeTableView.getItems().add(command.getCopyForRemoval());

            }
        });

        removePlanButton.setOnAction(e -> {
            AnnotatedPlan selectedItem = plantypeTableView.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                commandStack.storeAndExecute(new RemovePlanFromPlanType(selectedItem, planType));
                plantypeTableView.getItems().remove(selectedItem);
                plantypeTableView.refresh();
                planListView.getItems().sort(repositoryHBoxComparator);
            }
        });

        removeAllPlansButton.setOnAction(e -> {
            commandStack.storeAndExecute(new RemoveAllPlansFromPlanType(planType));
            plantypeTableView.getItems().clear();
            plantypeTableView.refresh();
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
        plantypeTableView.getItems().addListener(new ListChangeListener<AnnotatedPlan>() {
            @Override
            public void onChanged(Change<? extends AnnotatedPlan> c) {
                c.next();
                if (c.wasAdded()) {
                    List<? extends AnnotatedPlan> addedSubList = c.getAddedSubList();
                    addedSubList
                            .forEach(e -> {
                                List<RepositoryHBox<Plan>> toRemove = planListView
                                        .getItems()
                                        .stream()
                                        .filter(f -> f.getObject().equals(e.getPlan()))
                                        .collect(Collectors.toList());
                                toRemove.forEach(rH -> planListView.getItems().remove(rH));
                            });
                } else if (c.wasRemoved()) {
                    List<? extends AnnotatedPlan> removedSubList = c.getRemoved();
                    removedSubList
                            .forEach(e -> {
                                Pair<Plan, Path> planPathPair = RepoViewBackend
                                        .getInstance()
                                        .getPlans()
                                        .stream()
                                        .filter(f -> f.getKey().equals(e.getPlan()))
                                        .findFirst().get();
                                RepositoryHBox<Plan> e1 = new RepositoryHBox<>(planPathPair.getKey(), planPathPair.getValue());
                                e1.setOnMouseClicked(null);
                                planListView.getItems().add(e1);
                            });
                }
            }
        });

        TableColumn<AnnotatedPlan, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setCellValueFactory(new PropertyValueFactory<>("activated"));
        activeColumn.setCellFactory(new Callback<TableColumn<AnnotatedPlan, Boolean>, TableCell<AnnotatedPlan, Boolean>>() {
            @Override
            public TableCell<AnnotatedPlan, Boolean> call(TableColumn<AnnotatedPlan, Boolean> param) {
                TableCell<AnnotatedPlan, Boolean> annotatedPlanBooleanTableCell = new TableCell<AnnotatedPlan, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            if (Boolean.TRUE.equals(item)) {
                                setGraphic(new ImageView(new AlicaIcon(SuccessState.class.getSimpleName())));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon(FailureState.class.getSimpleName())));
                            }
                            setText("");
                        }
                    }
                };
                return annotatedPlanBooleanTableCell;
            }
        });

        TableColumn<AnnotatedPlan, Plan> planNameColumn = new TableColumn<>();
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>("plan"));
        planNameColumn.setCellFactory(new Callback<TableColumn<AnnotatedPlan, Plan>, TableCell<AnnotatedPlan, Plan>>() {
            @Override
            public TableCell<AnnotatedPlan, Plan> call(TableColumn<AnnotatedPlan, Plan> param) {
                TableCell<AnnotatedPlan, Plan> planNameTableCell = new TableCell<AnnotatedPlan, Plan>() {
                    @Override
                    protected void updateItem(Plan item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            setText(item.getName());
                        }
                    }
                };
                return planNameTableCell;
            }
        });

        plantypeTableView.getColumns().add(activeColumn);
        plantypeTableView.getColumns().add(planNameColumn);
        plantypeTableView.setRowFactory(tv -> {
            TableRow<AnnotatedPlan> annotatedPlanTableRow = new TableRow<>();
            annotatedPlanTableRow.setOnMouseClicked(e -> {
                commandStack.storeAndExecute(new ChangeAttributeValue<>(annotatedPlanTableRow.getItem(),
                        "activated", !annotatedPlanTableRow.getItem().isActivated(), planType));
                plantypeTableView.refresh();
            });
            return annotatedPlanTableRow;
        });
    }


    public void setPlanType(PlanType planType) {
        if (planType == null) {
            throw new RuntimeException("Empty PlanType BULLSHIT!");
        }
        this.planType = planType;
        plantypeTableView.getItems().addAll(planType.getPlans());
    }

    public void setCommandStack(CommandStack commandStack) {
        this.commandStack = commandStack;
    }

    public void setPlanTypeTab(PlanTypeTab planTypeTab) {this.planTypeTab = planTypeTab; }
}
