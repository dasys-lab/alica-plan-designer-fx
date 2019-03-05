package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanEditorGroup;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class AbstractPlanHBox extends HBox {
    private ViewModelElement abstractPlan;
    private boolean selected;

    public AbstractPlanHBox(ViewModelElement p, StateContainer stateContainer) {
        super();
        this.abstractPlan = p;
        ImageView imageView = new ImageView(new AlicaIcon(p.getType(), AlicaIcon.Size.SMALL));
        Text text = new Text(p.getName());
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(imageView, text);
        setLayoutX(-(text.getLayoutBounds().getWidth()/2));
        // TODO onAddElement constants and make image size a constant across the application.
        // 19px per abstract plan because every line is 16px high and the additional 3px are for spacing between elements
        setLayoutY(StateContainer.STATE_RADIUS +
                (stateContainer.getState().getPlanElements().indexOf(abstractPlan)) * 19 + 3); // 3px offset to not touch state circle with value-box
        setPickOnBounds(false);
        ObservableList<Pair<ViewModelElement, AbstractPlanElementContainer>> selected = FXCollections.observableArrayList();
        selected.add(new Pair<>(abstractPlan, stateContainer));
        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> ((PlanEditorGroup) stateContainer.getParent())
                .getPlanEditorTab().getSelectedPlanElements().setValue(selected));
    }

    public ViewModelElement getAbstractPlan() {
        return abstractPlan;
    }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }
}
