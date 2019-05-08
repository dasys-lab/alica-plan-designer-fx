package de.unikassel.vs.alica.planDesigner.view.editor.tools;

import de.unikassel.vs.alica.planDesigner.controller.EntryPointCreatorDialogController;
import de.unikassel.vs.alica.planDesigner.controller.ErrorWindowController;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaCursor;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.util.HashMap;


public class EntryPointTool extends AbstractTool {

    public EntryPointTool(TabPane workbench, PlanTab planTab, ToggleGroup group) {
        super(workbench, planTab, group);
    }

    @Override
    public ToolButton createToolUI() {
        ToolButton toolButton = new ToolButton();
        Tooltip tooltip = new Tooltip(Types.ENTRYPOINT);
        toolButton.setTooltip(tooltip);
        toolButton.setIcon(Types.ENTRYPOINT);
        setToolButton(toolButton);
        imageCursor = new AlicaCursor(AlicaCursor.Type.entrypoint, 8, 8);
        forbiddenCursor = new AlicaCursor(AlicaCursor.Type.forbidden_entrypoint, 8, 8);
        addCursor = new AlicaCursor(AlicaCursor.Type.add_entrypoint, 8, 8);
        return toolButton;
    }

    @Override
    protected void initHandlerMap() {

        if(customHandlerMap.isEmpty()){

            customHandlerMap.put(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof StackPane) {
                        setCursor(addCursor);
                    } else {
                        setCursor(forbiddenCursor);
                    }
                }
            });

            customHandlerMap.put(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    Node target = (Node) event.getTarget();
                    Parent parent = target.getParent();
                    if (parent instanceof ToggleButton) {
                        endTool();
                    }

                    I18NRepo i18NRepo = I18NRepo.getInstance();

                    if(handleNonPrimaryButtonEvent(event)){
                        return;
                    }
                    Point2D localCoordinates = getLocalCoordinatesFromEvent(event);
                    if (localCoordinates == null){
                        event.consume();
                        return;
                    }

                    TaskViewModel task = EntryPointCreatorDialogController.createEntryPointCreatorDialog();
                    if(task == null){
                        ErrorWindowController.createErrorWindow(i18NRepo.getString("label.error.entryPoint.noTask"), null);
                        endTool();
                        return;
                    }

                    HashMap<String, Long> related = new HashMap<>();
                    related.put(Types.TASK, task.getId());
                    planTab.fireModificationEvent(GuiEventType.ADD_ELEMENT, Types.ENTRYPOINT, null, related, (int) localCoordinates.getX(), (int) localCoordinates.getY());
                }
            });
        }
    }
}
