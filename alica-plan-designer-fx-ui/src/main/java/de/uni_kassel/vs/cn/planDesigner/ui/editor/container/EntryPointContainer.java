package de.uni_kassel.vs.cn.planDesigner.ui.editor.container;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.EntryPoint;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.EditorConstants;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 *
 */
public class EntryPointContainer extends PlanElementContainer<EntryPoint> {

    private PmlUiExtension pmlUiExtensionOfReferencedState;

    public EntryPointContainer(EntryPoint containedElement, PmlUiExtension pmlUiExtension,
                               PmlUiExtension pmlUiExtensionOfReferencedState, CommandStack commandStack) {
        super(containedElement, pmlUiExtension, commandStack);
        this.pmlUiExtensionOfReferencedState = pmlUiExtensionOfReferencedState;
        draw();
    }

    @Override
    public void draw() {
        getChildren().clear();
        visualRepresentation = new Circle(EditorConstants.PLAN_SHIFTING_PARAMETER, getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER, 20, Color.BLUE);
        Line line = new Line(EditorConstants.PLAN_SHIFTING_PARAMETER,
                getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                pmlUiExtensionOfReferencedState.getXPos() + EditorConstants.PLAN_SHIFTING_PARAMETER + EditorConstants.SECTION_MARGIN,
                pmlUiExtensionOfReferencedState.getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER + EditorConstants.SECTION_MARGIN);
        line.getStrokeDashArray().addAll(2d, 10d);
        getChildren().add(line);
        getChildren().add(visualRepresentation);
        getChildren().add(new Text(EditorConstants.PLAN_SHIFTING_PARAMETER, getPmlUiExtension().getYPos() + EditorConstants.PLAN_SHIFTING_PARAMETER,
                getContainedElement().getTask().getName()));
    }
}
