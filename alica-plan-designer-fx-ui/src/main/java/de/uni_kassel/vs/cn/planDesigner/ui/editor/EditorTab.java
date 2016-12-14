package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.scene.control.Tab;

import java.io.File;
import java.nio.file.Path;

/**
 * Created by marci on 18.11.16.
 */
public abstract class EditorTab<T extends PlanElement> extends Tab {

    private T editable;
    private Path filePath;
    private PlanElement selectedPlanElement;

    public EditorTab(T editable, Path filePath) {
        super(filePath.getFileName().toString());
        this.editable = editable;
        this.filePath = filePath;
        selectedPlanElement = editable;
    }

    public Path getFilePath() {
        return filePath;
    }

    public abstract void save();

    public T getEditable() {
        return editable;
    }

    public PlanElement getSelectedPlanElement() {
        return selectedPlanElement;
    }
}
