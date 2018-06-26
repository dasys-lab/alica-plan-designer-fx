package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;

public class RenameElement extends AbstractCommand {
    private String newName;
    private String oldName;
    private PlanElement element;

    public RenameElement(ModelManager manager, PlanElement element, String newName) {
        super(manager);
        this.newName = newName;
        oldName = element.getName();
        this.element = element;
    }

    @Override
    public void doCommand() {
        element.setName(newName);
    }

    @Override
    public void undoCommand() {
        element.setName(oldName);
    }
}
