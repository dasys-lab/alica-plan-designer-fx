package de.uni_kassel.vs.cn.planDesigner.ui.editor.tools.condition;

import de.uni_kassel.vs.cn.generator.plugin.PluginManager;
import de.uni_kassel.vs.cn.planDesigner.PlanDesigner;
import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.add.AddConditionToPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.alica.PostCondition;
import de.uni_kassel.vs.cn.planDesigner.controller.MainController;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TerminalStateContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;

import java.util.Map;

import static de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils.getAlicaFactory;

/**
 * Created by marci on 01.03.17.
 */
public class PostConditionTool extends AbstractConditionTool {

    private Cursor previousCursor;

    public PostConditionTool(TabPane workbench) {
        super(workbench);
    }

    @Override
    public Condition createNewObject() {
        return getAlicaFactory().createPostCondition();
    }

    @Override
    protected Map<EventType, EventHandler> toolRequiredHandlers() {
        if (eventHandlerMap.isEmpty()) {
            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof Node == false) {
                        event.consume();
                        return;
                    }
                    if (((Node)event.getTarget()).getParent() instanceof TerminalStateContainer && visualRepresentation == null) {
                        visualRepresentation = new ImageView(new AlicaIcon(createNewObject().getClass().getSimpleName()));
                        ((TerminalStateContainer)event.getTarget()).getChildren().add(visualRepresentation);
                    } else if (((Node)event.getTarget()).getParent() instanceof AbstractPlanElementContainer
                            || event.getTarget() instanceof ConditionHBox) {
                            Cursor cursor = ((Node) event.getTarget()).getScene().getCursor();
                        if (cursor.equals(PlanDesigner.FORBIDDEN_CURSOR) == false) {
                            previousCursor = cursor;
                            ((Node)event.getTarget()).getScene().setCursor(PlanDesigner.FORBIDDEN_CURSOR);
                        }
                    }
                    event.consume();
                }
            });

            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof Node == false) {
                        event.consume();
                        return;
                    }
                    if (((Node)event.getSource()).getParent() instanceof TerminalStateContainer == false) {
                        ((Node)event.getSource()).getScene().setCursor(previousCursor);
                    }
                    if (visualRepresentation != null) {
                        ((TerminalStateContainer)event.getSource()).getChildren().remove(visualRepresentation);
                        visualRepresentation = null;
                    }
                }
            });


            eventHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getSource() instanceof ConditionHBox) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                        Condition newCondition = createNewObject();
                        newCondition.setPluginName(PluginManager.getInstance().getActivePlugin().getPluginName());
                        if (newCondition instanceof PostCondition == false) {
                            AddConditionToPlan command = new AddConditionToPlan(((PlanTab)workbench.getSelectionModel().getSelectedItem()).getEditable(),
                                    newCondition);
                            MainController.getInstance()
                                    .getCommandStack()
                                    .storeAndExecute(command);
                        }
                    }
                    endPhase();
                }
            });

            eventHandlerMap.put(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    endPhase();
                }
            });
        }
        return eventHandlerMap;
    }
}
