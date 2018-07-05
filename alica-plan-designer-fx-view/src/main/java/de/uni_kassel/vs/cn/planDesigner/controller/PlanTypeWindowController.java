package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanTypeViewModelElement;
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
    private TableView<PlanTypeViewModelElement> planTypeTableView;

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
            if (!planTypeTab.getText().contains("*")) {
                return;
            }
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, planType.getName());
            event.setElementId(planType.getId());
            guiModificationHandler.handle(event);
            planTypeTab.setText(planTypeTab.getText().replace("*", ""));
            planTypeTab.setDirty(false);

        });

        addPlanButton.setOnAction(e -> {
            RepositoryHBox selectedItem = planListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            fireModificationEvent(GuiEventType.ADD_ELEMENT, selectedItem.getViewModelName(), selectedItem.getViewModelId());
            planTypeTableView.getItems().add(new PlanTypeViewModelElement(selectedItem.getViewModelElement(), true));
            planTypeTableView.getItems().sort(viewModelElementComparator);
            setDirty();
        });

        removePlanButton.setOnAction(e -> {
            ViewModelElement selectedItem = planTypeTableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            fireModificationEvent(GuiEventType.REMOVE_ELEMENT, selectedItem.getName(), selectedItem.getId());
            planTypeTableView.getItems().remove(selectedItem);
            planTypeTableView.refresh();
            planListView.getItems().sort(repositoryHBoxComparator);
            setDirty();
        });

        removeAllPlansButton.setOnAction(e -> {
            for(PlanTypeViewModelElement element : planTypeTableView.getItems()) {
                fireModificationEvent(GuiEventType.REMOVE_ELEMENT, element.getName(), element.getId());
            }
            planTypeTableView.getItems().clear();
            planTypeTableView.refresh();
            planListView.getItems().sort(repositoryHBoxComparator);
            setDirty();
        });
    }

    private void fireModificationEvent(GuiEventType type, String viewModelName, long viewModelId) {
        GuiModificationEvent event = new GuiModificationEvent(type, Types.PLAN, viewModelName);
        event.setParentId(planType.getId());
        event.setElementId(viewModelId);
        event.setElementId(planType.getId());
        guiModificationHandler.handle(event);
    }

    private void setDirty() {
        if (!planTypeTab.getText().contains("*")) {
            planTypeTab.setText(planTypeTab.getText() + "*");
        }
        planTypeTab.setDirty(true);
    }

    private void initUIText() {
        removePlanButton.setText(i18NRepo.getString("label.plantype.removePlan"));
        removeAllPlansButton.setText(i18NRepo.getString("label.plantype.removeAllPlans"));
        addPlanButton.setText(i18NRepo.getString("label.plantype.addPlan"));
        saveButton.setText(i18NRepo.getString("action.save"));
    }

    public void initTableView(ObservableList<PlanTypeViewModelElement> plansInPlanType) {
        planTypeTableView.getItems().addAll(plansInPlanType);
        planTypeTableView.getItems().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {

                    for (ViewModelElement element : c.getAddedSubList()) {
                        for (RepositoryHBox repositoryHBox : planListView.getItems()) {
                            if (repositoryHBox.getViewModelId() == element.getId()) {
                                planListView.getItems().remove(repositoryHBox);
                                break;
                            }
                        }
                    }

                } else if (c.wasRemoved()) {
                    for (ViewModelElement element : c.getRemoved()) {
                        RepositoryHBox e1 = new RepositoryHBox(element, guiModificationHandler);
                        e1.setOnMouseClicked(null);
                        planListView.getItems().add(e1);
                    }
                }
            }
        });

        TableColumn<PlanTypeViewModelElement, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setResizable(false);
        activeColumn.setCellValueFactory(new PropertyValueFactory<>(i18NRepo.getString("label.column.activated")));
        activeColumn.setCellFactory(new Callback<TableColumn<PlanTypeViewModelElement, Boolean>, TableCell<PlanTypeViewModelElement, Boolean>>() {
            @Override
            public TableCell<PlanTypeViewModelElement, Boolean> call(TableColumn<PlanTypeViewModelElement, Boolean> param) {
                TableCell<PlanTypeViewModelElement, Boolean> annotatedPlanBooleanTableCell = new TableCell<PlanTypeViewModelElement, Boolean>() {
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

        TableColumn<PlanTypeViewModelElement, String> planNameColumn = new TableColumn<>(i18NRepo.getString("label.column.planName"));
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        planNameColumn.setCellFactory(new Callback<TableColumn<PlanTypeViewModelElement, String>, TableCell<PlanTypeViewModelElement, String>>() {
            @Override
            public TableCell<PlanTypeViewModelElement, String> call(TableColumn<PlanTypeViewModelElement, String> param) {
                TableCell<PlanTypeViewModelElement, String> planNameTableCell = new TableCell<PlanTypeViewModelElement, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            setText(item);
                        }
                    }
                };
                return planNameTableCell;
            }
        });

        planTypeTableView.getColumns().add(activeColumn);
        planTypeTableView.getColumns().add(planNameColumn);
        planTypeTableView.setRowFactory(tv -> {
            TableRow<PlanTypeViewModelElement> annotatedPlanTableRow = new TableRow<>();
            annotatedPlanTableRow.setOnMouseClicked(e -> {
                if (e.getClickCount() == 2 && annotatedPlanTableRow.getItem() != null) {
                    annotatedPlanTableRow.getItem().setActivated(!annotatedPlanTableRow.getItem().isActivated());
                    planTypeTableView.refresh();
                }
            });
            return annotatedPlanTableRow;
        });
    }

    public void initPlanListView(ObservableList<ViewModelElement> allPlans) {
        this.allPlans = allPlans;
        for (ViewModelElement plan : this.allPlans) {
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

    public void setPlanTypeTab(PlanTypeTab planTypeTab) {
        this.planTypeTab = planTypeTab;
    }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }
}
