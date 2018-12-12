package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiChangeAttributeEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiChangePositionEvent;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.DraggableEditorElement;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.ConditionHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlanTab extends AbstractPlanTab {

    private PlanEditorGroup planEditorGroup;
    private ConditionHBox conditionHBox;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    private I18NRepo i18NRepo;
    private PropertiesConditionsVariablesPane propertiesConditionsVariablesPane;
//    private PropertiesTable<ViewModelElement> upperPropertiesTable;
//    private PropertiesTable<ViewModelElement> lowerPropertiesTable;

    public PlanTab(ViewModelElement viewModelElement, IGuiModificationHandler handler) {
        super(viewModelElement, handler);
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.plan") + ": " + viewModelElement.getName());
        draw();
    }

    private void draw() {
        PlanViewModel planViewModel = (PlanViewModel) viewModelElement;
        planEditorGroup = new PlanEditorGroup(planViewModel, this);
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorGroup.setManaged(true);

//        upperPropertiesTable = new PropertiesTable<>();
//        upperPropertiesTable.setEditable(true);
//        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
//        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
//        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
//        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
//        upperPropertiesTable.addItem(planViewModel);
//        planViewModel.nameProperty().addListener((observable, oldValue, newValue) -> {
//            setDirty(true);
//            fireGuiChangeAttributeEvent(newValue, "name", String.class.getSimpleName());
//        });
//        planViewModel.commentProperty().addListener((observable, oldValue, newValue) -> {
//            setDirty(true);
//            fireGuiChangeAttributeEvent(newValue, "comment", String.class.getSimpleName());
//        });
//
//        lowerPropertiesTable = new PropertiesTable<>();
//        lowerPropertiesTable.setEditable(true);
//        lowerPropertiesTable.addColumn(i18NRepo.getString("label.column.utilitythreshold"), "utilityThreshold", new DoubleStringConverter(), true);
//        lowerPropertiesTable.addColumn(i18NRepo.getString("label.column.masterplan"), "masterPlan", new BooleanStringConverter(), true);
//        lowerPropertiesTable.addItem(planViewModel);
//        planViewModel.utilityThresholdProperty().addListener((observable, oldValue, newValue) -> {
//            setDirty(true);
//            fireGuiChangeAttributeEvent(newValue.toString(), "utilityThreshold", Double.class.getSimpleName());
//        });
//        planViewModel.masterPlanProperty().addListener((observable, oldValue, newValue) -> {
//            setDirty(true);
//            fireGuiChangeAttributeEvent(newValue.toString(), "masterPlan", Boolean.class.getSimpleName());
//        });

        propertiesConditionsVariablesPane = new PropertiesConditionsVariablesPane();
        propertiesConditionsVariablesPane.setText(planViewModel.getName());

        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);

        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane(), this);

        HBox scrollPlaneAndToolBarHBox = new HBox(scrollPane, editorToolBar);
        scrollPlaneAndToolBarHBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(scrollPlaneAndToolBarHBox,Priority.ALWAYS);

//        conditionHBox = new ConditionHBox(planViewModel, selectedPlanElements);

//        VBox globalVBox = new VBox(upperPropertiesTable, lowerPropertiesTable, conditionHBox, scrollPlaneAndToolBarHBox);
        VBox globalVBox = new VBox(scrollPlaneAndToolBarHBox, propertiesConditionsVariablesPane);
        setContent(globalVBox);
    }

    private void fireGuiChangeAttributeEvent(String newValue, String attribute, String type) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.PLAN, viewModelElement.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(type);
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setElementId(viewModelElement.getId());
        guiModificationHandler.handle(guiChangeAttributeEvent);
    }

    /**
     * Fire an event, notifying the {@link IGuiModificationHandler}, that an object has changed its position.
     *
     * @param planElementContainer  the container of the object, that changed its position
     * @param type  the type of object, that changed its position
     * @param newX  the new x-coordinate
     * @param newY  the new y-coordinate
     */
    public void fireChangePositionEvent(DraggableEditorElement planElementContainer, String type, double newX, double newY) {
        GuiChangePositionEvent event = new GuiChangePositionEvent(GuiEventType.CHANGE_ELEMENT, type, planElementContainer.getModelElement().getName());
        event.setElementId(planElementContainer.getModelElement().getId());
        event.setParentId(viewModelElement.getId());
        event.setNewX((int) newX);
        event.setNewY((int) newY);

        guiModificationHandler.handleGuiChangePositionEvent(event);
    }

    public ConditionHBox getConditionHBox() {
        return conditionHBox;
    }

    public EditorToolBar getEditorToolBar() {
        return editorToolBar;
    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLAN, viewModelElement.getName());
            event.setElementId(viewModelElement.getId());
            guiModificationHandler.handle(event);
            setDirty(false);
        }
    }

    protected void initSelectedPlanElement(ViewModelElement viewModelElement) {
        super.initSelectedPlanElements(viewModelElement);
        contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//                    if (event.getTarget() == planContent) {
//                        List<Pair<PlanElement, AbstractPlanElementContainer>> planViewModel = new ArrayList<>();
//                        planViewModel.add(new Pair<>(getEditable(), null));
//                        getSelectedPlanElements().set(planViewModel);
//                    }
                });
            }
        });
    }

    public StackPane getPlanContent() {
        return planContent;
    }
    public PlanEditorGroup getPlanEditorGroup() {
        return planEditorGroup;
    }

}
