package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.BendpointContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.TransitionContainer;
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
            /*
            Polyline target = (Polyline) event.getTarget();
            double x = event.getX();
            double y = event.getY();
            for(int i=0;i+3 < target.getPoints().size(); i+=2) {
                double x1 = target.getPoints().get(i);
                double y1 = target.getPoints().get(i+1);

                double x2 = target.getPoints().get(i+2);
                double y2 = target.getPoints().get(i+3);

                if (x2+5 >= x && x >= x1+5 && y2+5 >= y && y+5 >= y1) {*/
        if(event.getTarget() instanceof BendpointContainer) {
            return;
        }

        Polyline polyline = null;
        if (event.getTarget() instanceof Polyline) {
            polyline = (Polyline) event.getTarget();
        } else if(event.getTarget() instanceof TransitionContainer) {
            // TODO weird behaviour after redrawing of transitions
            ((TransitionContainer)event.getTarget()).getDraggableNodes().forEach(d -> d.setVisible(true));
            transitionContainers
                    .stream()
                    .filter(t -> t.equals(event.getTarget()) == false)
                    .forEach(t -> t.getDraggableNodes().forEach(d -> d.setVisible(false)));
            return;
        }

        final Polyline finalPolyline = polyline;
        transitionContainers
                .forEach(t -> {
                    if (t.getVisualRepresentation().equals(finalPolyline)) {
                       t.getDraggableNodes().forEach(d -> d.setVisible(true));
                    } else {
                        t.getDraggableNodes().forEach(d -> d.setVisible(false));
                    }
                });
                    /*break;
                }
            }*/
    }
}
