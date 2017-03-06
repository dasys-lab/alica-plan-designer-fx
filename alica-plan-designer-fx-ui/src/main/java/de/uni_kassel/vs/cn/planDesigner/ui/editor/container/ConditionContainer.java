package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * Created by marci on 28.02.17.
 */
public class ConditionContainer extends AbstractPlanElementContainer<Condition> {

    /**
     * @param containedElement
     * @param commandStack
     */
    public ConditionContainer(Condition containedElement, CommandStack commandStack) {
        super(containedElement, null, commandStack);
        setupContainer();
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(Condition containedElement) {
        return event -> ((ConditionHBox) getParent()).selectedPlanElementProperty().setValue(new Pair<>(containedElement, this));
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        getChildren().add(new ImageView(new AlicaIcon(getContainedElement().getClass())));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLACK;
    }
}
