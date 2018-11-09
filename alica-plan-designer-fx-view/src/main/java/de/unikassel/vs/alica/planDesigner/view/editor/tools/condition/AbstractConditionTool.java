package de.unikassel.vs.alica.planDesigner.view.editor.tools.condition;

import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.ConditionHBox;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tools.AbstractTool;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;


public abstract class AbstractConditionTool extends AbstractTool {

    protected Node visualRepresentation;

    public AbstractConditionTool(TabPane workbench, PlanTab planTab) {
        super(workbench, planTab);
    }

    public void draw() {
        ((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getConditionHBox().setupConditionVisualisation();
    }

    @Override
    protected Node getPlanEditorTabPane() {
        return ((PlanTab)(planEditorTabPane.getSelectionModel().getSelectedItem())).getConditionHBox();
    }

    @Override
    protected void initHandlerMap() {
        if (customHandlerMap.isEmpty()) {
            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_ENTERED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof ConditionHBox && visualRepresentation == null) {
                        //TODO change later to fitting type
                        visualRepresentation = new ImageView(new AlicaIcon(Types.PRECONDITION, AlicaIcon.Size.SMALL));
                        ((ConditionHBox)event.getTarget()).getChildren().add(visualRepresentation);
                    }
                    event.consume();
                }
            });

            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_EXITED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (visualRepresentation != null) {
                        ((ConditionHBox)event.getSource()).getChildren().remove(visualRepresentation);
                        visualRepresentation = null;
                    }
                }
            });


            customHandlerMap.put(MouseDragEvent.MOUSE_DRAG_RELEASED, new EventHandler<MouseDragEvent>() {
                @Override
                public void handle(MouseDragEvent event) {
                    if (event.getTarget() instanceof ConditionHBox) {
                        ((ConditionHBox)event.getTarget()).getChildren().remove(visualRepresentation);
//                        Condition newCondition = createNewObject();
//                        newCondition.setPluginName(PluginManager.getInstance().getDefaultPlugin().getName());
//                        if (newCondition instanceof PostCondition == false) {
//                            AddConditionToPlan command = new AddConditionToPlan(((PlanTab) planEditorTabPane.getSelectionModel().getSelectedItem()).getEditable(),
//                                    newCondition);
//                            MainWindowController.getInstance()
//                                    .getCommandStack()
//                                    .storeAndExecute(command);
//                        }
                    }
                    endPhase();
                }
            });
        }
    }
}
