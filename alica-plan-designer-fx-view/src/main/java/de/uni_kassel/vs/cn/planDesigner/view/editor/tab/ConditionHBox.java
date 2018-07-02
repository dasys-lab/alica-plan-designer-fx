package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.uni_kassel.vs.cn.planDesigner.common.ViewModelElement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.util.List;

public class ConditionHBox extends HBox {

    private final SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> selectedPlanElement;
    private final ViewModelElement plan;

    public ConditionHBox(ViewModelElement abstractPlan, SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> selectedPlanElement) {
        this.plan = abstractPlan;
        this.selectedPlanElement = selectedPlanElement;
        setupConditionVisualisation();
        setMinWidth(20);
        setMinHeight(20);
        setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void setupConditionVisualisation() {
        getChildren().clear();
//        if (plan.getPreCondition() != null) {
//            getChildren().add(new ConditionContainer(plan.getPreCondition(), commandStack));
//        }
//        if (plan.getRuntimeCondition() != null) {
//            getChildren().add(new ConditionContainer(plan.getRuntimeCondition(), commandStack));
//        }
    }

    public Pair<Long, AbstractPlanElementContainer> getSelectedPlanElement() {
        return selectedPlanElement.get().get(0);
    }

    public SimpleObjectProperty<List<Pair<Long, AbstractPlanElementContainer>>> selectedPlanElementProperty() {
        return selectedPlanElement;
    }
}
