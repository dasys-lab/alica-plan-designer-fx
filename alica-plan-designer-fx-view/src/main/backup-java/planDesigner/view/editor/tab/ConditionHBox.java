package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Plan;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

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
        setSpacing(16);
        setPadding(new Insets(2, 5, 2, 5));
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
