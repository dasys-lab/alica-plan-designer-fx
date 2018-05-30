package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.alica.Condition;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.tab.ConditionHBox;
import de.uni_kassel.vs.cn.planDesigner.ui.img.AlicaIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.ArrayList;

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
        ArrayList selectedCondition = new ArrayList<>();
        selectedCondition.add(new Pair<>(containedElement, this));
        return event -> ((ConditionHBox) getParent()).selectedPlanElementProperty().setValue(selectedCondition);
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        getChildren().add(new ImageView(new AlicaIcon(getContainedElement().getClass().getSimpleName())));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLACK;
    }
}
