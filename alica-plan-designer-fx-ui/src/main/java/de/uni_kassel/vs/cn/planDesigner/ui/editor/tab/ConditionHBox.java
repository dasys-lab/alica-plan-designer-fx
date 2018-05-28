package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.ConditionContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.List;

/**
 * Created by marci on 28.02.17.
 */
public class ConditionHBox extends HBox {

    private final SimpleObjectProperty<List<Pair<PlanElement, AbstractPlanElementContainer>>> selectedPlanElement;
    private final CommandStack commandStack;
    private final Plan plan;

    public ConditionHBox(Plan abstractPlan, SimpleObjectProperty<List<Pair<PlanElement, AbstractPlanElementContainer>>> selectedPlanElement, CommandStack commandStack) {
        this.plan = abstractPlan;
        this.selectedPlanElement = selectedPlanElement;
        this.commandStack = commandStack;
        setupConditionVisualisation();
        setMinWidth(20);
        setMinHeight(20);
        setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setupConditionVisualisation() {
        getChildren().clear();
        if (plan.getPreCondition() != null) {
            getChildren().add(new ConditionContainer(plan.getPreCondition(), commandStack));
        }
        if (plan.getRuntimeCondition() != null) {
            getChildren().add(new ConditionContainer(plan.getRuntimeCondition(), commandStack));
        }
    }

    public Pair<PlanElement, AbstractPlanElementContainer> getSelectedPlanElement() {
        return selectedPlanElement.get().get(0);
    }

    public SimpleObjectProperty<List<Pair<PlanElement, AbstractPlanElementContainer>>> selectedPlanElementProperty() {
        return selectedPlanElement;
    }
}
