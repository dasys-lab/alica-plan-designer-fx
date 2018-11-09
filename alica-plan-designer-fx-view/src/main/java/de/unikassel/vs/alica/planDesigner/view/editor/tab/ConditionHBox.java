package de.unikassel.vs.alica.planDesigner.view.editor.tab;

import de.unikassel.vs.alica.planDesigner.view.editor.container.AbstractPlanElementContainer;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
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

    private final SimpleObjectProperty<List<Pair<ViewModelElement, AbstractPlanElementContainer>>> selectedPlanElement;
    private final ViewModelElement plan;

    public ConditionHBox(ViewModelElement abstractPlan, SimpleObjectProperty<List<Pair<ViewModelElement, AbstractPlanElementContainer>>> selectedPlanElement) {
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
//            getChildren().onAddElement(new ConditionContainer(plan.getPreCondition(), commandStack));
//        }
//        if (plan.getRuntimeCondition() != null) {
//            getChildren().onAddElement(new ConditionContainer(plan.getRuntimeCondition(), commandStack));
//        }
    }

    public Pair<ViewModelElement, AbstractPlanElementContainer> getSelectedPlanElement() {
        return selectedPlanElement.get().get(0);
    }

    public SimpleObjectProperty<List<Pair<ViewModelElement, AbstractPlanElementContainer>>> selectedPlanElementProperty() {
        return selectedPlanElement;
    }
}
