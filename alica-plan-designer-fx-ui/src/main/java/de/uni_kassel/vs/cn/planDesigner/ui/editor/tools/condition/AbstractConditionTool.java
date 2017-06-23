package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddConditionToPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.AbstractTool;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by marci on 01.03.17.
 */
public abstract class AbstractConditionTool extends AbstractTool<Condition> {

    private Map<EventType, EventHandler> eventHandlerMap = new HashMap<>();
    private Node visualRepresentation;

    public AbstractConditionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public void draw() {
        ((PlanTab)workbench.getSelectionModel().getSelectedItem()).getConditionHBox().setupConditionVisualisation();
    }

    @Override
    protected Node getWorkbench() {
        return ((PlanTab)(workbench.getSelectionModel().getSelectedItem())).getConditionHBox();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof ConditionHBox && visualRepresentation == null) {
                        visualRepresentation = new ImageView(new AlicaIcon(createNewObject().getClass()));
                        ((ConditionHBox)event.getTarget()).getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (visualRepresentation != null) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                        visualRepresentation = null;
                    }
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getSource() instanceof ConditionHBox) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                        AddConditionToPlan command = new AddConditionToPlan(((PlanTab)workbench.getSelectionModel().getSelectedItem()).getEditable(),
                                createNewObject());
                        MainController.getInstance()
                                .getCommandStack()
                                .storeAndExecute(command);
                    }
                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }
}
