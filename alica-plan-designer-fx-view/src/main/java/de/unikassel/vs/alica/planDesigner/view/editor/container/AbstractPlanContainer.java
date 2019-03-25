package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.PlanElementViewModel;
import javafx.scene.control.Label;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import javax.swing.plaf.nimbus.State;

public class AbstractPlanContainer extends Container {

    StateContainer parentStateContainer;

    public AbstractPlanContainer(StateContainer parentStateContainer, PlanElementViewModel abstractPlanViewModel, PlanTab planTab) {
        super(abstractPlanViewModel, null, planTab);
        this.parentStateContainer = parentStateContainer;
        createNameListener();
        this.setupContainer();
    }

    public StateContainer getParentStateContainer () {
        return parentStateContainer;
    }

    @Override
    public void setupContainer() {
        Label label = new Label();

        label.setText(planElementViewModel.getName());
        this.planElementViewModel.nameProperty().addListener((observable, oldValue, newName) -> {
            label.setText(newName);
        });

        label.setGraphic(getGraphic(planElementViewModel.getType()));
        this.planElementViewModel.typeProperty().addListener((observable, oldValue, newType) -> {
            label.setGraphic(getGraphic(newType));
        });
        // need to be set, when the label is actually layouted (before getWidth is 0)
        label.widthProperty().addListener((observable, oldValue, newValue) -> {
            // 19px per abstract plan because every line is 16px high and the additional 3px are for spacing between elements
            // 3px offset to not touch state circle with value-box
            relocate(-label.getWidth()/2, StateContainer.STATE_RADIUS + parentStateContainer.getState().getAbstractPlans().indexOf(this.planElementViewModel)*19+3);
        });

        this.visualRepresentation = label;
        getChildren().add(visualRepresentation);
    }

    @Override
    public Color getVisualisationColor() {
        return null;
    }

    public ImageView getGraphic(String iconName) {
        return new ImageView(new AlicaIcon(iconName, AlicaIcon.Size.SMALL));
    }

    @Override
    public void setEffectToStandard() {
        this.setEffect(null);
    }
}



/*private PlanElementViewModel abstractPlan;
    private boolean selected;

    public AbstractPlanHBox(PlanElementViewModel abstractPlanViewModel, StateContainer stateContainer) {
        super();
        this.abstractPlan = abstractPlanViewModel;
        ImageView imageView = new ImageView(new AlicaIcon(abstractPlanViewModel.getType(), AlicaIcon.Size.SMALL));
        Text text = new Text(abstractPlanViewModel.getName());
        this.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        getChildren().addAll(imageView, text);

        setLayoutX(-(text.getLayoutBounds().getWidth()/2));
        // 19px per abstract plan because every line is 16px high and the additional 3px are for spacing between elements
        setLayoutY(StateContainer.STATE_RADIUS +
                (stateContainer.getState().getAbstractPlans().indexOf(abstractPlan)) * 19 + 3); // 3px offset to not touch state circle with value-box
        setPickOnBounds(false);

        addEventFilter(MouseEvent.MOUSE_CLICKED, event -> ((PlanEditorGroup) stateContainer.getParent())
                .getPlanEditorTab().setSelectedContainer(stateContainer));
    }

    public PlanElementViewModel getAbstractPlan() {
        return abstractPlan;
    }
    public boolean isSelected() { return selected; }
    public void setSelected(boolean selected) { this.selected = selected; }*/