package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.AbstractPlan;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.ConditionContainer;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

/**
 * Created by marci on 28.02.17.
 */
public class ConditionHBox extends HBox {

    private final SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedPlanElement;
    private final CommandStack commandStack;
    private final Plan plan;

    public ConditionHBox(Plan abstractPlan, SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedPlanElement, CommandStack commandStack) {
        this.plan = abstractPlan;
        this.selectedPlanElement = selectedPlanElement;
        this.commandStack = commandStack;
        setupConditionVisualisation();
        // TODO a more dynamic size would be nice
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
        return selectedPlanElement.get();
    }

    public SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedPlanElementProperty() {
        return selectedPlanElement;
    }
}
