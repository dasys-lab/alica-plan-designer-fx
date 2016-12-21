package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends PlanElementContainer<State> {

    public StateContainer(PmlUiExtension pmlUiExtension, State state) {
        super(state, pmlUiExtension);
        visualRepresentation = new Circle(20, Color.YELLOW);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(state.getName()));
        setLayoutX(pmlUiExtension.getXPos() * 1);
        setLayoutY(pmlUiExtension.getYPos() * 1);
    }
}
