package de.unikassel.vs.alica.planDesigner.view.editor.container;

import de.unikassel.vs.alica.planDesigner.view.editor.tab.planTab.PlanTab;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.EntryPointViewModel;
import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import javafx.scene.effect.Effect;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

/**
 *
 */
public class EntryPointContainer extends Container {

    public static final double ENTRYPOINT_RADIUS = 10.0;
    private StateContainer stateContainer;
    private boolean dragged;
    private ImageView taskIcon;
    private EntryPointViewModel containedElement;

//    /**
//     * This constructor is for dummy containers. NEVER use in real UI
//     */
//    public EntryPointContainer() {
//        super(null, null, null);
//
//    }

    /**
     * @param containedElement
     * @param stateContainer
     */
    public EntryPointContainer(EntryPointViewModel containedElement, /*PmlUiExtension pmlUiExtension,*/
                               StateContainer stateContainer, PlanTab planTab) {
        super(containedElement, null, planTab);
        if (stateContainer != null) {
            setStateContainer(stateContainer);
        }
        this.containedElement = containedElement;
        taskIcon = new ImageView(new AlicaIcon(Task.class.getSimpleName(), AlicaIcon.Size.SMALL));

        makeDraggable(this);
        createTaskToEntryPointListeners(this, containedElement);
        createPositionListeners(this, containedElement);
        setupContainer();
    }

    public Polygon createArrowHead(double _toX, double _toY, double _fromX, double _fromY) {
        double vecX = _toX - _fromX;
        double vecY = _toY - _fromY;
        double vecLen = Math.sqrt(vecX*vecX + vecY*vecY);

        double triangleSpanVecX = vecY;
        double triangleSpanVecY = -vecX;
        double triangleSpanLen = Math.sqrt(triangleSpanVecY * triangleSpanVecY + triangleSpanVecX * triangleSpanVecX);
        Polygon polygon = new Polygon(_toX - 5 * (vecX / vecLen) + 5 * (triangleSpanVecX / triangleSpanLen),
                _toY - 5 * (vecY / vecLen) + 5 * triangleSpanVecY / triangleSpanLen,
                _toX,
                _toY,
                _toX - 5 * (vecX / vecLen) - 5 * (triangleSpanVecX / triangleSpanLen),
                _toY - 5 * (vecY / vecLen) - 5 * triangleSpanVecY / triangleSpanLen);
        polygon.setFill(getVisualisationColor());
        polygon.setStroke(getVisualisationColor());
        polygon.setStrokeWidth(1);
        polygon.setVisible(true);
        return polygon;
    }

    @Override
    public void setupContainer() {
        getChildren().clear();
        visualRepresentation = new Circle(EntryPointContainer.ENTRYPOINT_RADIUS,
                getVisualisationColor());
        visualRepresentation.setId("EntryPointContainerCircle");
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
            line.getStrokeDashArray().addAll(25d, 10d);
            line.setStroke(getVisualisationColor());
            getChildren().add(line);
            getChildren().add(createArrowHead(vec.getX(), vec.getY(), visualRepresentation.getLayoutX(),
                    visualRepresentation.getLayoutY()));
        }

        getChildren().add(visualRepresentation);
        Text taskName = new Text(containedElement.getTask().getName());
        HBox hBox = new HBox();
        hBox.getChildren().addAll(taskIcon, taskName);
        hBox.setLayoutX(visualRepresentation.getLayoutX() - taskName.getLayoutBounds().getWidth() / 2.0 - taskIcon.getFitWidth() / 2.0 - EntryPointContainer.ENTRYPOINT_RADIUS / 2.0);
        hBox.setLayoutY(visualRepresentation.getLayoutY() - EntryPointContainer.ENTRYPOINT_RADIUS * 1.2 - taskName.getFont().getSize());
        getChildren().add(hBox);
    }

    @Override
    public Color getVisualisationColor() {
        return Color.BLUE;
    }

    @Override
    public void redrawElement() {
        setupContainer();
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

    @Override
    public void setEffectToStandard() {
        this.visualRepresentation.setEffect(Container.standardEffect);
    }

    @Override
    public void setCustomEffect(Effect effect) {
        this.visualRepresentation.setEffect(effect);
    }
}
