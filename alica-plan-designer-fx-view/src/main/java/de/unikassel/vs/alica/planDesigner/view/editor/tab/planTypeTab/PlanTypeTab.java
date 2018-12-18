package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTypeTab;

import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.AnnotatedPlanView;
import de.unikassel.vs.alica.planDesigner.view.model.PlanTypeViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryHBox;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.util.Comparator;

public class PlanTypeTab extends AbstractPlanTab {

    private Button removePlanButton;
    private Button addPlanButton;
    private Button removeAllPlansButton;
    private Button saveButton;

    private TableView<AnnotatedPlanView> planTypeTableView;
    private ListView<RepositoryHBox> planListView;

    private Comparator<RepositoryHBox> repositoryHBoxComparator;
    private Comparator<ViewModelElement> viewModelElementComparator;

    public PlanTypeTab(ViewModelElement planType, IGuiModificationHandler guiModificationHandler) {
        super(planType, guiModificationHandler);
        setText(i18NRepo.getString("label.caption.plantype") + ": " + planType.getName());

        initGui();
    }

    private void initGui() {
        // instantiate gui objects
        this.repositoryHBoxComparator = Comparator.comparing(planRepositoryHBox -> !planRepositoryHBox.getViewModelType().equals(Types.MASTERPLAN));
        this.repositoryHBoxComparator = repositoryHBoxComparator.thenComparing(planRepositoryHBox -> planRepositoryHBox.getViewModelName());
        this.viewModelElementComparator = Comparator.comparing(annotatedPlan -> !annotatedPlan.getType().equals(Types.MASTERPLAN));
        this.viewModelElementComparator = viewModelElementComparator.thenComparing(annotatedPlan -> annotatedPlan.getName());

        removePlanButton = new Button(i18NRepo.getString("label.plantype.removePlan"));
        removeAllPlansButton = new Button(i18NRepo.getString("label.plantype.removeAllPlans"));
        addPlanButton = new Button(i18NRepo.getString("label.plantype.addPlan"));
        saveButton = new Button(i18NRepo.getString("action.save"));
        this.planTypeTableView = new TableView<>();
        this.planListView = new ListView<>();

        // initialize gui objects
        initButtons();
        initPlansInPlanTypeTable();
        initAllPlansListView();

        VBox buttonsVBox = new VBox(addPlanButton, removePlanButton, removeAllPlansButton, saveButton);
        HBox tablesAndButtonsHBox = new HBox(planListView, buttonsVBox, planTypeTableView);
        globalVBox.getChildren().add(0,tablesAndButtonsHBox);
    }

    protected void initButtons() {
        // init button text

        // init button callbacks
        saveButton.setOnAction(e -> {
            if (!isDirty()) {
                return;
            }
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, viewModelElement.getName());
            event.setElementId(viewModelElement.getId());
            guiModificationHandler.handle(event);
        });

        addPlanButton.setOnAction(e -> {
            RepositoryHBox selectedItem = planListView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            fireModificationEvent(GuiEventType.ADD_ELEMENT, selectedItem.getViewModelName(), ((AnnotatedPlanView) selectedItem.getViewModelElement()).getPlanId());
            setDirty(true);
        });

        removePlanButton.setOnAction(e -> {
            ViewModelElement selectedItem = planTypeTableView.getSelectionModel().getSelectedItem();
            if (selectedItem == null) {
                return;
            }
            fireModificationEvent(GuiEventType.REMOVE_ELEMENT, selectedItem.getName(), ((AnnotatedPlanView) selectedItem).getPlanId());
            setDirty(true);
        });

        removeAllPlansButton.setOnAction(e -> {
            fireModificationEvent(GuiEventType.REMOVE_ALL_ELEMENTS, viewModelElement.getName(), viewModelElement.getId());
            setDirty(true);
        });
    }

    private void initPlansInPlanTypeTable() {
        PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) viewModelElement;
        planTypeTableView.setItems(planTypeViewModel.getPlansInPlanType());
        planTypeTableView.getItems().sort(viewModelElementComparator);
        planTypeViewModel.getPlansInPlanType().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {
                    for (ViewModelElement element : c.getAddedSubList()) {
                        planTypeViewModel.getAllPlans().remove(element);
                        planTypeTableView.getItems().add((AnnotatedPlanView) element);
                    }
                } else if (c.wasRemoved()) {
                    for (ViewModelElement element : c.getRemoved()) {
                        planTypeViewModel.getAllPlans().add(element);
                        for (AnnotatedPlanView view : planTypeTableView.getItems()) {
                            if (view.getId() == element.getId()) {
                                planTypeTableView.getItems().remove(view);
                                break;
                            }
                        }
                    }
                }
                planTypeTableView.refresh();
            }
        });

        planTypeTableView.getColumns().add(createActiveColumn());
        planTypeTableView.getColumns().add(createNameColumn());
        planTypeTableView.setRowFactory(tv -> {
            TableRow<AnnotatedPlanView> annotatedPlanTableRow = new TableRow<>();
            annotatedPlanTableRow.setOnMouseClicked(e -> {
                AnnotatedPlanView item = annotatedPlanTableRow.getItem();
                if (e.getClickCount() == 2 && item != null) {
                    item.setActivated(!item.isActivated());
                    planTypeTableView.refresh();
                    setDirty(true);
                    GuiChangeAttributeEvent event = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.ANNOTATEDPLAN, item.getName());
                    event.setParentId(viewModelElement.getId());
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

    private void initAllPlansListView() {
        PlanTypeViewModel planTypeViewModel = (PlanTypeViewModel) viewModelElement;
        for (ViewModelElement plan : planTypeViewModel.getAllPlans()) {
            if (planTypeViewModel.containsPlan(plan.getId())) {
                continue;
            }
            RepositoryHBox planRepositoryHBox = new RepositoryHBox(plan, guiModificationHandler);
            planRepositoryHBox.setOnMouseClicked(null);
            planListView.getItems().add(planRepositoryHBox);
            planListView.getItems().sort(repositoryHBoxComparator);
        }
        planTypeViewModel.getAllPlans().addListener(new ListChangeListener<ViewModelElement>() {
            @Override
            public void onChanged(Change<? extends ViewModelElement> c) {
                c.next();
                if (c.wasAdded()) {
                    for (ViewModelElement element : c.getAddedSubList()) {
                        if (planTypeViewModel.containsPlan(element.getId())) {
                            continue;
                        }

                        RepositoryHBox planRepositoryHBox = new RepositoryHBox(element, guiModificationHandler);
                        planRepositoryHBox.setOnMouseClicked(null);
                        Platform.runLater(() -> {
                            planListView.getItems().add(planRepositoryHBox);
                            planListView.getItems().sort(repositoryHBoxComparator);
                        });
                    }
                } else if (c.wasRemoved()) {
                    for (ViewModelElement element : c.getRemoved()) {
                        for (RepositoryHBox plan : planListView.getItems()) {
                            if (plan.getViewModelId() == element.getId()) {
                                Platform.runLater(() -> {
                                    planListView.getItems().remove(plan);
                                });
                                break;
                            }
                        }
                    }
                }
            }
        });
    }

    private TableColumn<AnnotatedPlanView, String> createNameColumn() {
        TableColumn<AnnotatedPlanView, String> planNameColumn = new TableColumn<>(i18NRepo.getString("label.column.planName"));
        planNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        planNameColumn.setCellFactory(new Callback<TableColumn<AnnotatedPlanView, String>, TableCell<AnnotatedPlanView, String>>() {
            @Override
            public TableCell<AnnotatedPlanView, String> call(TableColumn<AnnotatedPlanView, String> param) {
                TableCell<AnnotatedPlanView, String> planNameTableCell = new TableCell<AnnotatedPlanView, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            setText(item);
                            System.out.println("PTWC:" + item);
                        }
                    }
                };
                return planNameTableCell;
            }
        });
        return planNameColumn;
    }

    private TableColumn<AnnotatedPlanView, Boolean> createActiveColumn() {
        TableColumn<AnnotatedPlanView, Boolean> activeColumn = new TableColumn<>(i18NRepo.getString("label.column.active"));
        activeColumn.setResizable(false);
        activeColumn.setCellValueFactory(new PropertyValueFactory<>(i18NRepo.getString("label.column.activated")));
        activeColumn.setCellFactory(new Callback<TableColumn<AnnotatedPlanView, Boolean>, TableCell<AnnotatedPlanView, Boolean>>() {
            @Override
            public TableCell<AnnotatedPlanView, Boolean> call(TableColumn<AnnotatedPlanView, Boolean> param) {
                TableCell<AnnotatedPlanView, Boolean> annotatedPlanBooleanTableCell = new TableCell<AnnotatedPlanView, Boolean>() {
                    @Override
                    protected void updateItem(Boolean item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty == false) {
                            if (Boolean.TRUE.equals(item)) {
                                setGraphic(new ImageView(new AlicaIcon(Types.SUCCESSSTATE, AlicaIcon.Size.SMALL)));
                            } else {
                                setGraphic(new ImageView(new AlicaIcon(Types.FAILURESTATE, AlicaIcon.Size.SMALL)));
                            }
                            setText("");
                        }
                    }
                };
                annotatedPlanBooleanTableCell.setStyle("-fx-alignment: CENTER;");
                return annotatedPlanBooleanTableCell;
            }
        });
        return activeColumn;
    }

    /**
     * Called for changing comment of plantype and stuff...
     * @param newValue
     * @param attribute
     */
    private void fireGuiChangeAttributeEvent(String newValue, String attribute) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.PLANTYPE, viewModelElement.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(String.class.getSimpleName());
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setElementId(viewModelElement.getId());
        guiModificationHandler.handle(guiChangeAttributeEvent);
    }

    /**
     * Called for adding, removing plans from plantype...
     * @param type
     * @param viewModelName
     * @param viewModelId
     */
    private void fireModificationEvent(GuiEventType type, String viewModelName, long viewModelId) {
        GuiModificationEvent event = new GuiModificationEvent(type, Types.ANNOTATEDPLAN, viewModelName);
        event.setParentId(viewModelElement.getId());
        event.setElementId(viewModelId);
        guiModificationHandler.handle(event);
    }

    @Override
    public GuiModificationEvent handleDelete() {
        System.err.println("PlanTypeTab: Not implemented!");
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLANTYPE, viewModelElement.getName());
            event.setElementId(viewModelElement.getId());
            guiModificationHandler.handle(event);
        }
    }

    // TODO
    public void addPlanToAllPlans (PlanViewModel planViewModel) {
        ((PlanTypeViewModel) viewModelElement).addPlanToAllPlans(planViewModel);
    }

    // TODO
    public void addPlanToPlansInPlanType(AnnotatedPlanView annotatedPlan) {
        ((PlanTypeViewModel) viewModelElement).addPlanToPlansInPlanType(annotatedPlan);
    }
    // TODO
    public void removePlanFromPlansInPlanType(long id) {
        ((PlanTypeViewModel) viewModelElement).removePlanFromPlansInPlanType(id);
    }
}
