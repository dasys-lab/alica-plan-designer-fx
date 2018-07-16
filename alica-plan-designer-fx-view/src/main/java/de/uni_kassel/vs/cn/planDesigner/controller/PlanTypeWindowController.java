package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.events.GuiChangeAttributeEvent;
import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTypeTab.PlanTypeTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanTypeViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.PlanViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryHBox;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;

public class PlanTypeWindowController implements Initializable {

    private ViewModelElement planType;

    private Comparator<RepositoryHBox> repositoryHBoxComparator;

    private Comparator<ViewModelElement> viewModelElementComparator;

    private PlanTypeViewModel planTypeViewModel;

    private I18NRepo i18NRepo;

    private IGuiModificationHandler guiModificationHandler;

    @FXML
    private PlanTypeTab planTypeTab;

    @FXML
    private Button removePlanButton;

    @FXML
    private Button addPlanButton;

    @FXML
    private Button removeAllPlansButton;

    @FXML
    private TableView<PlanViewModelElement> planTypeTableView;

    @FXML
    private ListView<RepositoryHBox> planListView;

    @FXML
    private Button saveButton;

    @FXML
    private PropertiesTable propertiesTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        i18NRepo = I18NRepo.getInstance();
        initComparators();
        initUIText();
        initButtons();
    }

    private void initButtons() {
        saveButton.setOnAction(e -> {
            if (!planTypeTab.isDirty()) {
                return;
            }
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, planType.getName());
            event.setElementId(planType.getId());
            guiModificationHandler.handle(event);
        });

        addPlanButton.setOnAction(e -> {
            RepositoryHBox selectedItem = planListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            fireModificationEvent(GuiEventType.ADD_ELEMENT, selectedItem.getViewModelName(), selectedItem.getViewModelId());
            planTypeTableView.getItems().add(new PlanViewModelElement(selectedItem.getViewModelElement(), true));
            planTypeTableView.getItems().sort(viewModelElementComparator);
            planTypeTab.setDirty(true);
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
            planTypeTab.setDirty(true);
        });

        removeAllPlansButton.setOnAction(e -> {
            if (planTypeViewModel.getPlansInPlanType().size() == 0) {
                return;
            }
            fireModificationEvent(GuiEventType.REMOVE_ALL_ELEMENTS, planType.getName(), planType.getId());
            planTypeTableView.getItems().clear();
            planTypeTableView.refresh();
            planListView.getItems().sort(repositoryHBoxComparator);
            planTypeTab.setDirty(true);
        });
    }

    public void init(PlanTypeViewModel planTypeViewModel) {
        this.planTypeViewModel = planTypeViewModel;
        initTableView();
        initPlanListView();
        propertiesTable.setEditable(true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        propertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        propertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        propertiesTable.addItem(planTypeViewModel);
        planTypeViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
            this.planTypeTab.setDirty(true);
            fireGuiChangeAttributeEvent(newValue, "name");
        });
        planTypeViewModel.commentProperty().addListener((observable, oldValue, newValue) -> {
            this.planTypeTab.setDirty(true);
            fireGuiChangeAttributeEvent(newValue, "comment");
        });
    }

    private void fireGuiChangeAttributeEvent(String newValue, String attribute) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.PLANTYPE, planType.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(String.class.getSimpleName());
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setParentId(planType.getId());
        guiModificationHandler.handle(guiChangeAttributeEvent);
    }

    private void fireModificationEvent(GuiEventType type, String viewModelName, long viewModelId) {
        GuiModificationEvent event = new GuiModificationEvent(type, Types.PLAN, viewModelName);
        event.setParentId(planType.getId());
        event.setElementId(viewModelId);
        guiModificationHandler.handle(event);
    }

    private void initUIText() {
        removePlanButton.setText(i18NRepo.getString("label.plantype.removePlan"));
        removeAllPlansButton.setText(i18NRepo.getString("label.plantype.removeAllPlans"));
        addPlanButton.setText(i18NRepo.getString("label.plantype.addPlan"));
        saveButton.setText(i18NRepo.getString("action.save"));
    }

    private void initTableView() {
        planTypeTableView.getItems().addAll(planTypeViewModel.getPlansInPlanType());
        planTypeTableView.getItems().sort(viewModelElementComparator);
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

        TableColumn<PlanViewModelElement, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setResizable(false);
        activeColumn.setCellValueFactory(new PropertyValueFactory<>(i18NRepo.getString("label.column.activated")));
        activeColumn.setCellFactory(new Callback<TableColumn<PlanViewModelElement, Boolean>, TableCell<PlanViewModelElement, Boolean>>() {
            @Override
            public TableCell<PlanViewModelElement, Boolean> call(TableColumn<PlanViewModelElement, Boolean> param) {
                TableCell<PlanViewModelElement, Boolean> annotatedPlanBooleanTableCell = new TableCell<PlanViewModelElement, Boolean>() {
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

        TableColumn<PlanViewModelElement, String> planNameColumn = new TableColumn<>(i18NRepo.getString("label.column.planName"));
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        planNameColumn.setCellFactory(new Callback<TableColumn<PlanViewModelElement, String>, TableCell<PlanViewModelElement, String>>() {
            @Override
            public TableCell<PlanViewModelElement, String> call(TableColumn<PlanViewModelElement, String> param) {
                TableCell<PlanViewModelElement, String> planNameTableCell = new TableCell<PlanViewModelElement, String>() {
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
            TableRow<PlanViewModelElement> annotatedPlanTableRow = new TableRow<>();
            annotatedPlanTableRow.setOnMouseClicked(e -> {
                PlanViewModelElement item = annotatedPlanTableRow.getItem();
                if (e.getClickCount() == 2 && item != null) {
                    item.setActivated(!item.isActivated());
                    planTypeTableView.refresh();
                    planTypeTab.setDirty(true);
                    GuiChangeAttributeEvent event = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.ANNOTATEDPLAN, item.getName());
                    event.setParentId(planType.getId());
                    event.setElementId(item.getId());
                    event.setAttributeName("activated");
                    event.setAttributeType(Boolean.class.getSimpleName());
                    event.setNewValue(String.valueOf(item.isActivated()));
                    guiModificationHandler.handle(event);
                }
            });
            return annotatedPlanTableRow;
        });
    }

    private void initPlanListView() {
        planTypeViewModel.getAllPlans().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {
                    for (ViewModelElement element : c.getAddedSubList()) {
                        if (!isAlreadyInPlanType(element)) {
                            RepositoryHBox planRepositoryHBox = new RepositoryHBox(element, guiModificationHandler);
                            planRepositoryHBox.setOnMouseClicked(null);
                            Platform.runLater(() -> {
                                planListView.getItems().add(planRepositoryHBox);
                                planListView.getItems().sort(repositoryHBoxComparator);
                            });
                        }
                    }

                } else if (c.wasRemoved()) {
                    for (ViewModelElement element : c.getAddedSubList()) {
                        for (RepositoryHBox plan : planListView.getItems()) {
                            if (plan.getViewModelId() == element.getId()) {
                                Platform.runLater(() -> {
                                    planListView.getItems().remove(plan);
                                });
                                break;
                            }
                        }
                        Platform.runLater(() -> {
                            planListView.getItems().sort(repositoryHBoxComparator);
                        });
                    }
                }
            }
        });
        for (ViewModelElement plan : planTypeViewModel.getAllPlans()) {
            if (isAlreadyInPlanType(plan)) {
                continue;
            }
            RepositoryHBox planRepositoryHBox = new RepositoryHBox(plan, guiModificationHandler);
            planRepositoryHBox.setOnMouseClicked(null);
            planListView.getItems().add(planRepositoryHBox);
            planListView.getItems().sort(repositoryHBoxComparator);
        }
    }

    private boolean isAlreadyInPlanType(ViewModelElement plan) {
        for (PlanViewModelElement planViewModelElement : planTypeViewModel.getPlansInPlanType()) {
            if (plan.getId() == planViewModelElement.getId()) {
                return true;
            }
        }
        return false;
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

    public PlanTypeViewModel getPlanTypeViewModel() {
        return planTypeViewModel;
    }
}
