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
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.converter.BooleanStringConverter;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.LongStringConverter;

public class PlanTab extends AbstractPlanTab {

    private PlanEditorGroup planEditorGroup;
    private ConditionHBox conditionHBox;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;
    private PlanViewModel plan;
    private IGuiModificationHandler guiModificationHandler;


    private I18NRepo i18NRepo;
    private PropertiesTable<ViewModelElement> upperPropertiesTable;
    private PropertiesTable<ViewModelElement> lowerPropertiesTable;

    public PlanTab(ViewModelElement viewModelElement, IGuiModificationHandler handler) {
        super(viewModelElement);
        this.guiModificationHandler = handler;
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.plan") + ": " + viewModelElement.getName());
    }

    private void draw() {
        planEditorGroup = new PlanEditorGroup(plan, this);
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorGroup.setManaged(true);

        upperPropertiesTable = new PropertiesTable<>();
        upperPropertiesTable.setEditable(true);
        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        upperPropertiesTable.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        upperPropertiesTable.addItem(plan);
        plan.nameProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireGuiChangeAttributeEvent(newValue, "name", String.class.getSimpleName());
        });
        plan.commentProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireGuiChangeAttributeEvent(newValue, "comment", String.class.getSimpleName());
        });

        lowerPropertiesTable = new PropertiesTable<>();
        lowerPropertiesTable.setEditable(true);
        lowerPropertiesTable.addColumn(i18NRepo.getString("label.column.utilitythreshold"), "utilityThreshold", new DoubleStringConverter(), true);
        lowerPropertiesTable.addColumn(i18NRepo.getString("label.column.masterplan"), "masterPlan", new BooleanStringConverter(), true);
        lowerPropertiesTable.addItem(plan);
        plan.utilityThresholdProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireGuiChangeAttributeEvent(newValue.toString(), "utilityThreshold", Double.class.getSimpleName());
        });
        plan.masterPlanProperty().addListener((observable, oldValue, newValue) -> {
            setDirty(true);
            fireGuiChangeAttributeEvent(newValue.toString(), "masterPlan", Boolean.class.getSimpleName());
        });

        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane(), this);
        HBox hBox = new HBox(scrollPane, editorToolBar);
        conditionHBox = new ConditionHBox(plan, selectedPlanElements);
        VBox vBox = new VBox(upperPropertiesTable, lowerPropertiesTable, conditionHBox, hBox);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);
        VBox.setVgrow(hBox,Priority.ALWAYS);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);

        hBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        setContent(vBox);
    }

//    public PlanEditorGroup getPlanEditorGroup() {
//        return planEditorGroup;
//    }

    private void fireGuiChangeAttributeEvent(String newValue, String attribute, String type) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.PLAN, plan.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(type);
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setElementId(plan.getId());
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
        event.setParentId(plan.getId());
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

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

//    public void setupPlanVisualisation() {
//        planEditorGroup.setupPlanVisualisation();
//    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }

    @Override
    public void save() {
        if (isDirty()) {
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLAN, presentedViewModelElement.getName());
            event.setElementId(presentedViewModelElement.getId());
            guiModificationHandler.handle(event);
            setDirty(false);
        }
    }

//    public PmlUiExtensionMap getPmlUiExtensionMap() {
//        return pmlUiExtensionMap;
//    }
//
//    public void setPmlUiExtensionMap(PmlUiExtensionMap pmlUiExtensionMap) {
//        this.pmlUiExtensionMap = pmlUiExtensionMap;
//        planModelVisualisationObject = new PlanModelVisualisationObject(getEditable(), pmlUiExtensionMap);
//    }

    protected void initSelectedPlanElement(ViewModelElement viewModelElement) {
        super.initSelectedPlanElements(viewModelElement);
        contentProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                newValue.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
//                    if (event.getTarget() == planContent) {
//                        List<Pair<PlanElement, AbstractPlanElementContainer>> plan = new ArrayList<>();
//                        plan.add(new Pair<>(getEditable(), null));
//                        getSelectedPlanElements().set(plan);
//                    }
                });
            }
        });
    }

    public void updateText(String newName) {
        this.setText(i18NRepo.getString("label.caption.plan") + ": " + newName);
        setDirty(true);
    }

    public void setPlanViewModel(PlanViewModel plan) {
        this.plan = plan;
        setPresentedViewModelElement(this.plan);
        draw();
//        String absolutePath = planPathPair.getValue().toFile().toString();
//        String uiExtensionMapPath = absolutePath.substring(0, absolutePath.lastIndexOf(".")) + ".pmlex";
//        URI relativeURI = EMFModelUtils.createRelativeURI(new File(uiExtensionMapPath));
//        setPmlUiExtensionMap((PmlUiExtensionMap) AlicaResourceSet.getInstance().getResource(relativeURI, false).getContents().get(0));
    }

    public PlanViewModel getPlan(){
        return plan;
    }
    public StackPane getPlanContent() {
        return planContent;
    }
    public PlanEditorGroup getPlanEditorGroup() {
        return planEditorGroup;
    }

}
