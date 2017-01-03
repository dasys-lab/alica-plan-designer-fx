package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.PlanElementContainer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends PlanElementContainer<State> {

    public StateContainer(PmlUiExtension pmlUiExtension, State state, CommandStack commandStack) {
        super(state, pmlUiExtension, commandStack);
        visualRepresentation = new Circle(20, Color.YELLOW);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(state.getName()));
        setLayoutX(pmlUiExtension.getXPos() * 1);
        setLayoutY(pmlUiExtension.getYPos() * 1);
    }
}
