package de.uni_kassel.vs.cn.planDesigner.ui.editor;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;

import java.nio.file.Path;

/**
 * Created by marci on 18.11.16.
 */
public abstract class EditorTab<T extends PlanElement> extends Tab {

    private T editable;
    private Path filePath;
    private CommandStack commandStack;
    protected SimpleObjectProperty<PlanElement> selectedPlanElement;

    public EditorTab(T editable, Path filePath, CommandStack commandStack) {
        super(filePath.getFileName().toString());
        this.editable = editable;
        this.filePath = filePath;
        selectedPlanElement = new SimpleObjectProperty<>(editable);
        this.commandStack = commandStack;
    }

    public Path getFilePath() {
        return filePath;
    }

    public abstract void save();

    public T getEditable() {
        return editable;
    }

    public SimpleObjectProperty<PlanElement> getSelectedPlanElement() {
        return selectedPlanElement;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
