package de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab;

import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.container.DraggableEditorElement;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.AbstractPlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.EditorToolBar;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.HashMap;


public class PlanTab extends AbstractPlanTab {

    private PlanEditorGroup planEditorGroup;
    private EditorToolBar editorToolBar;
    private StackPane planContent;
    private ScrollPane scrollPane;

    public PlanTab(SerializableViewModel serializableViewModel, EditorTabPane editorTabPane) {
        super(serializableViewModel, editorTabPane.getGuiModificationHandler());
        draw();

        //Listener for update properties table inside a Plan
        this.getSelectedPlanElements().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ViewModelElement viewModelElement = (ViewModelElement) newValue.get(0).getKey();
                if(viewModelElement instanceof TransitionViewModel){
                    viewModelElement.setParentId(this.getSerializableViewModel().getId());
                }
                this.propertiesConditionsVariablesPane.setViewModelElement(viewModelElement);

                //Listener if AbstractPlan from State is deleted, update properties table
                if(viewModelElement instanceof BehaviourViewModel ||
                        viewModelElement instanceof PlanTypeViewModel ||
                        viewModelElement instanceof PlanViewModel) {
                    if(oldValue.get(0).getKey() instanceof StateViewModel) {
                        StateViewModel stateViewModel = (StateViewModel) oldValue.get(0).getKey();
                        stateViewModel.getPlanElements().addListener((ListChangeListener<? super PlanElementViewModel>) c -> {
                            this.propertiesConditionsVariablesPane.setViewModelElement(this.getSerializableViewModel());
                        });
                    }
                }
            }
        });
    }

    private void draw() {
        planEditorGroup = new PlanEditorGroup((PlanViewModel) serializableViewModel, this);
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

        HBox scrollPaneAndToolBarHBox = new HBox(scrollPane, editorToolBar);
        scrollPaneAndToolBarHBox.setBackground(new Background(new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
        VBox.setVgrow(scrollPaneAndToolBarHBox,Priority.ALWAYS);

        splitPane.getItems().add(0, scrollPaneAndToolBarHBox);
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
        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CHANGE_POSITION, type, planElementContainer.getViewModelElement().getName());
        event.setElementId(planElementContainer.getViewModelElement().getId());
        event.setParentId(serializableViewModel.getId());
        event.setX((int) newX);
        event.setY((int) newY);
        guiModificationHandler.handle(event);
    }

    public void fireModificationEvent(GuiEventType eventType, String elementType, String name, HashMap<String, Long> relatedElements) {
        GuiModificationEvent event = new GuiModificationEvent(eventType, elementType, name);
        event.setParentId(getSerializableViewModel().getId());
        event.setRelatedObjects(relatedElements);
        guiModificationHandler.handle(event);
    }

    public EditorToolBar getEditorToolBar() {
        return editorToolBar;
    }

    public GuiModificationEvent handleDelete() {
        return null;
    }

    public void save() {
        save(Types.PLAN);
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
