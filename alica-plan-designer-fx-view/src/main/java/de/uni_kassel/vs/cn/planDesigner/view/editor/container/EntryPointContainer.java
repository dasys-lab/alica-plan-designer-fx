package de.uni_kassel.vs.cn.planDesigner.view.editor.container;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.view.editor.PlanEditorGroup;
import de.uni_kassel.vs.cn.planDesigner.view.img.AlicaIcon;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Pair;

/**
 *
 */
public class EntryPointContainer extends AbstractPlanElementContainer<EntryPoint> {

    private StateContainer stateContainer;
    private boolean dragged;
    private ImageView taskIcon;

    /**
     * This constructor is for dummy containers. NEVER use in real UI
     */
    public EntryPointContainer() {
        super(null, null, null);

    }

    /**
     * @param containedElement
     * @param pmlUiExtension
     * @param stateContainer
     * @param commandStack
     */
    public EntryPointContainer(EntryPoint containedElement, PmlUiExtension pmlUiExtension,
                               StateContainer stateContainer, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        if (stateContainer != null) {
            setStateContainer(stateContainer);
        }
        makeDraggable(this);
        taskIcon = new ImageView(new AlicaIcon(Task.class.getSimpleName()));
        setupContainer();
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        setLayoutX(getPmlUiExtension().getXPos());
        setLayoutY(getPmlUiExtension().getYPos());
        visualRepresentation = new Circle(StateContainer.STATE_RADIUS,
                getVisualisationColor());
        setEffectToStandard();

        if (stateContainer != null) {
            double localX = stateContainer.getVisualRepresentation().getLayoutX();
            double localY = stateContainer.getVisualRepresentation().getLayoutY();
            Point2D planXY = stateContainer.localToParent(localX, localY);
            Point2D localXY = parentToLocal(planXY);

            Point2D vec = new Point2D(localXY.getX() - visualRepresentation.getLayoutX(), localXY.getY() - visualRepresentation.getLayoutY());
            double len = vec.magnitude() - StateContainer.STATE_RADIUS;
            vec = vec.normalize().multiply(len);

            Line line = new Line(visualRepresentation.getLayoutX(),
                    visualRepresentation.getLayoutY(),
                    vec.getX(),
                    vec.getY());
            line.getStrokeDashArray().addAll(2d, 10d);
            getChildren().add(line);
        }

        getChildren().add(visualRepresentation);
        Text taskName = new Text(getContainedElement().getTask().getName());
        HBox hBox = new HBox();
        hBox.getChildren().addAll(taskIcon, taskName);
        hBox.setLayoutX(visualRepresentation.getLayoutX() - taskName.getLayoutBounds().getWidth() / 2.0 - taskIcon.getFitWidth() / 2.0 - StateContainer.STATE_RADIUS / 2.0);
        hBox.setLayoutY(visualRepresentation.getLayoutY() - StateContainer.STATE_RADIUS * 1.2 - taskName.getFont().getSize());
        getChildren().add(hBox);
    }

    @Override
    public void setEffectToStandard() {
        visualRepresentation.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,
                new Color(0,0,0,0.8), 10, 0, 0, 0));
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLUE;
    }

    @Override
    protected EventHandler<MouseEvent> getMouseClickedEventHandler(EntryPoint containedElement) {
        ArrayList<Pair<PlanElement, AbstractPlanElementContainer>> selected = new ArrayList<>();
        selected.add(new Pair<>(containedElement, this));
        return event -> ((PlanEditorGroup) getParent()).getPlanEditorTab().getSelectedPlanElements().setValue(selected);
    }

    @Override
    public CommandStack getCommandStackForDrag() {
        return commandStack;
    }

    @Override
    public void redrawElement() {
        setupContainer();
    }

    @Override
    public AbstractCommand createMoveElementCommand() {
        return new ChangePosition(getPmlUiExtension(), getContainedElement(),
                (int) (getLayoutX()),
                (int) (getLayoutY()), getContainedElement().getPlan());
    }

    @Override
    public void setDragged(boolean dragged) {
        this.dragged = dragged;
    }

    @Override
    public boolean wasDragged() {
        return dragged;
    }

    public void setStateContainer(StateContainer stateContainer) {
        this.stateContainer = stateContainer;
        stateContainer.addListener(observable -> setupContainer());
    }
}
