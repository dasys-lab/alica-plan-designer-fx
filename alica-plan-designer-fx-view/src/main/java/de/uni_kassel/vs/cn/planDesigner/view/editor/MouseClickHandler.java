package de.uni_kassel.vs.cn.planDesigner.view.editor;

import de.uni_kassel.vs.cn.planDesigner.view.editor.container.BendpointContainer;
import de.uni_kassel.vs.cn.planDesigner.view.editor.container.TransitionContainer;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polyline;

import java.util.List;

public class MouseClickHandler implements EventHandler<MouseEvent> {

    private List<TransitionContainer> transitionContainers;

    public MouseClickHandler(List<TransitionContainer> transitionContainers) {
        this.transitionContainers = transitionContainers;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getTarget() instanceof BendpointContainer) {
            return;
        }

        Polyline polyline = null;
        if (event.getTarget() instanceof Polyline) {
            polyline = (Polyline) event.getTarget();
        }

        final Polyline finalPolyline = polyline;
        transitionContainers
                .forEach(t -> {
                    if (t.getVisualRepresentation().equals(finalPolyline)) {
                        t.setBendpointContainerVisibility(true);
                    } else {
                        t.setBendpointContainerVisibility(false);
                    }
                });
    }
}
