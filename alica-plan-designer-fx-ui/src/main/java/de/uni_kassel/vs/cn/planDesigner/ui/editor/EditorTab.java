package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.scene.control.Tab;
import org.eclipse.emf.ecore.EObject;

import java.io.File;

/**
 * Created by marci on 18.11.16.
 */
public abstract class EditorTab<T extends PlanElement> extends Tab {

    private T editable;
    private File file;
    private PlanElement selectedPlanElement;

    public EditorTab(T editable, File file) {
        super(file.getName());
        this.editable = editable;
        this.file = file;
        selectedPlanElement = editable;
    }

    public File getFile() {
        return file;
    }

    public abstract void save();

    public T getEditable() {
        return editable;
    }

    public PlanElement getSelectedPlanElement() {
        return selectedPlanElement;
    }
}
