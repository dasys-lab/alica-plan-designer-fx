package de.uni_kassel.vs.cn.planDesigner.ui.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.alica.xml.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.ui.editor.container.AbstractPlanElementContainer;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Tab;
import javafx.util.Pair;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by marci on 18.11.16.
 */
public abstract class AbstractEditorTab<T extends PlanElement> extends Tab {

    private T editable;
    private Path filePath;
    private CommandStack commandStack;
    protected SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> selectedPlanElement;

    public AbstractEditorTab(T editable, Path filePath, CommandStack commandStack) {
        super(filePath.getFileName().toString());
        this.editable = editable;
        this.filePath = filePath;
        selectedPlanElement = new SimpleObjectProperty<>(new Pair<>(editable, null));
        this.commandStack = commandStack;
        setClosable(true);
    }

    public Path getFilePath() {
        return filePath;
    }

    public void save() {
        try {
            EMFModelUtils.saveAlicaFile(getEditable());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public T getEditable() {
        return editable;
    }

    public SimpleObjectProperty<Pair<PlanElement, AbstractPlanElementContainer>> getSelectedPlanElement() {
        return selectedPlanElement;
    }

    public CommandStack getCommandStack() {
        return commandStack;
    }
}
