package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.planTab.PlanTab;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

public class ConditionContainer extends AbstractPlanElementContainer {

    /**
     * @param containedElement
     */
    public ConditionContainer(ViewModelElement containedElement, PlanTab planTab) {
        super(containedElement, null, planTab);
        setupContainer();
    }

    protected EventHandler<MouseEvent> getMouseClickedEventHandler(ViewModelElement containedElement) {
        ArrayList selectedCondition = new ArrayList<>();
        selectedCondition.add(new Pair<>(containedElement, this));
        return event -> ((ConditionHBox) getParent()).selectedPlanElementProperty().setValue(selectedCondition);
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        getChildren().add(new ImageView(new AlicaIcon("precondition")));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLACK;
    }
}
