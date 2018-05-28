package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.view.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.geometry.Insets;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class AbstractPlanHBox extends HBox {
    private PlanElement abstractPlan;

    public AbstractPlanHBox(PlanElement p, StateContainer stateContainer) {
        super();
        this.abstractPlan = p;
        ImageView imageView = new ImageView(new AlicaIcon(p.getClass().getSimpleName()));
        Text text = new Text(p.getName());
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(imageView, text);
        setLayoutX(-(text.getLayoutBounds().getWidth()/2));
        // TODO add constants and make image size a constant across the application.
        // 19px per abstract plan because every line is 16px high and the additional 3px are for spacing between elements
        setLayoutY(StateContainer.STATE_RADIUS +
                (stateContainer.getContainedElement().getPlans().indexOf(abstractPlan)) * 19 + 3); // 3px offset to not touch state circle with text-box
        setPickOnBounds(false);
        List<Pair<PlanElement, AbstractPlanElementContainer>> selected = new ArrayList<>();
        selected.add(new Pair<>(abstractPlan, stateContainer));
        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> ((PlanEditorGroup) getParent().getParent())
                .getPlanEditorTab().getSelectedPlanElement().setValue(selected));
    }

    public PlanElement getAbstractPlan() {
        return abstractPlan;
    }
}
