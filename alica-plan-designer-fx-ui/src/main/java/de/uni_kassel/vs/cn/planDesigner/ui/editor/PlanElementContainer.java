package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Shape;

/**
 * Created by marci on 16.12.16.
 */
public abstract class PlanElementContainer<T extends PlanElement> extends Pane {

    private T containedElement;
    private PmlUiExtension pmlUiExtension;
    protected Shape visualRepresentation;

    public PlanElementContainer(T containedElement, PmlUiExtension pmlUiExtension) {
        this.containedElement = containedElement;
        this.pmlUiExtension = pmlUiExtension;
        setBackground(Background.EMPTY);
    }


    public Shape getVisualRepresentation() {
        return visualRepresentation;
    }

    public T getContainedElement() {
        return containedElement;
    }

    public PmlUiExtension getPmlUiExtension() {
        return pmlUiExtension;
    }
}
