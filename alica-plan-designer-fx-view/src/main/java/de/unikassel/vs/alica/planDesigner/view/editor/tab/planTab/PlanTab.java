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
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.model.PlanViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.SerializableViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class PlanTab extends AbstractPlanTab {

    private PlanEditorGroup planEditorGroup;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(SerializableViewModel serializableViewModel, IGuiModificationHandler handler) {
        super(serializableViewModel, handler);
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.plan") + ": " + serializableViewModel.getName());

        if (serializableViewModel instanceof  PlanViewModel) {
            PlanViewModel planViewModel = (PlanViewModel) serializableViewModel;
            planViewModel.masterPlanProperty().addListener((observable, oldValue, newValue) -> {
                System.out.println("PlanTab: MasterPlan is " + newValue);
            });
        }
        draw();
    }

    private void draw() {
        PlanViewModel planViewModel = (PlanViewModel) serializableViewModel;
        planEditorGroup = new PlanEditorGroup(planViewModel, this);
        planContent = new StackPane(planEditorGroup);
        planContent.setPadding(new Insets(50, 50, 50, 50));
        planContent.setManaged(true);

        planEditorGroup.setManaged(true);

        scrollPane = new ScrollPane(planContent);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        HBox.setHgrow(scrollPane, Priority.ALWAYS);
        VBox.setVgrow(scrollPane,Priority.ALWAYS);

        editorToolBar = new EditorToolBar(MainWindowController.getInstance().getEditorTabPane(), this);

        HBox scrollPlaneAndToolBarHBox = new HBox(scrollPane, editorToolBar);
        scrollPlaneAndToolBarHBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(scrollPlaneAndToolBarHBox,Priority.ALWAYS);

        splitPane.getItems().add(0, scrollPlaneAndToolBarHBox);

    }

    private void fireGuiChangeAttributeEvent(String newValue, String attribute, String type) {
        GuiChangeAttributeEvent guiChangeAttributeEvent = new GuiChangeAttributeEvent(GuiEventType.CHANGE_ELEMENT, Types.PLAN, serializableViewModel.getName());
        guiChangeAttributeEvent.setNewValue(newValue);
        guiChangeAttributeEvent.setAttributeType(type);
        guiChangeAttributeEvent.setAttributeName(attribute);
        guiChangeAttributeEvent.setElementId(serializableViewModel.getId());
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
        event.setParentId(serializableViewModel.getId());
        event.setNewX((int) newX);
        event.setNewY((int) newY);

        guiModificationHandler.handleGuiChangePositionEvent(event);
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
            GuiModificationEvent event = new GuiModificationEvent(GuiEventType.SAVE_ELEMENT, Types.PLAN, serializableViewModel.getName());
            event.setElementId(serializableViewModel.getId());
            guiModificationHandler.handle(event);
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
