package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

/**
 * Created by marci on 02.12.16.
 */
public class StateContainer extends HBox {

    private State state;
    private PmlUiExtension pmlUiExtension;
    private List<TransitionContainer> outGoingTransitionContainers;
    private List<TransitionContainer> inComingTransiotionContainers;

    public StateContainer(PmlUiExtension pmlUiExtension, State state) {
        this.state = state;
        this.pmlUiExtension = pmlUiExtension;
        getChildren().add(new Text(state.getName()));
        Circle e = new Circle(20);
        getChildren().add(e);
        setLayoutX(pmlUiExtension.getXPos() * 1);
        setLayoutY(pmlUiExtension.getYPos() * 1);
    }

    public List<TransitionContainer> getOutGoingTransitionContainers() {
        return outGoingTransitionContainers;
    }

    public void setOutGoingTransitionContainers(List<TransitionContainer> outGoingTransitionContainers) {
        this.outGoingTransitionContainers = outGoingTransitionContainers;
    }

    public State getState() {
        return state;
    }
}
