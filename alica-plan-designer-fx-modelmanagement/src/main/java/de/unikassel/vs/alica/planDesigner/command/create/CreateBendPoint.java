package de.unikassel.vs.alica.planDesigner.command.create;

import com.sun.javafx.geom.Line2D;
import com.sun.javafx.geom.Point2D;
import de.unikassel.vs.alica.planDesigner.alicamodel.Transition;

import de.unikassel.vs.alica.planDesigner.command.UiPositionCommand;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;
import de.unikassel.vs.alica.planDesigner.uiextensionmodel.BendPoint;

import java.util.HashMap;

public class CreateBendPoint extends UiPositionCommand {

    protected BendPoint bendPoint;

    public CreateBendPoint(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.bendPoint = createBendPoint();
        this.uiElement = modelManager.getPlanUIExtensionPair(mmq.getParentId()).getUiElement(this.bendPoint.getTransition().getId());
    }

    protected BendPoint createBendPoint() {
        BendPoint bendPoint = new BendPoint();
        bendPoint.setX(x);
        bendPoint.setY(y);
        bendPoint.setTransition((Transition) modelManager.getPlanElement(mmq.getRelatedObjects().get(Types.TRANSITION)));
        return bendPoint;
    }

    @Override
    public void doCommand() {
        Transition transition = bendPoint.getTransition();
//        transition.getInState().
        int index = (uiElement.getBendPoints().size());
        float min = 100;

        if(uiElement.getBendPoints().size() >= 2) {
            for (int i = 0; i < uiElement.getBendPoints().size() - 1; i++) {
                Point2D p1 = new Point2D(uiElement.getBendPoints().get(i).getX(), uiElement.getBendPoints().get(i).getY());
                Point2D p2 = new Point2D(uiElement.getBendPoints().get(i + 1).getX(), uiElement.getBendPoints().get(i + 1).getY());

                Point2D p3 = new Point2D(bendPoint.getX(), bendPoint.getY());

                Line2D line = new Line2D(p1, p2);

                float distance = line.ptLineDist(p3);

                if (distance < 1.5 && distance <= min) {
                    min = distance;
                    index = i+1;
//                    System.out.println(line.ptLineDist(p3));
//                    System.out.println("Zwischen " + i + " und " + (i + 1));
                }
            }
     //       System.out.println(min);
     //       System.out.println(index);
            this.uiElement.addBendpoint(index, bendPoint);

            if(!uiElement.getBendPoints().contains(bendPoint)){
                System.out.println("Sonderfall");
            }

        } else {
            this.uiElement.addBendpoint(this.bendPoint);
        }

        this.modelManager.storePlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.bendPoint.getTransition());
    }

    @Override
    public void undoCommand() {
        HashMap<String, Long> bendPointMap = new HashMap<>();
        bendPointMap.put(Types.BENDPOINT, bendPoint.getId());
        this.uiElement.removeBendpoint(this.bendPoint);
        this.modelManager.dropPlanElement(mmq.getElementType(), bendPoint, false);
        this.fireEvent(ModelEventType.ELEMENT_REMOVED, this.bendPoint.getTransition(), bendPointMap);
    }
}
