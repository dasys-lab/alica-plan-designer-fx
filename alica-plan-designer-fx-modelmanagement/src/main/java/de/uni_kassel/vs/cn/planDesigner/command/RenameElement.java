package de.uni_kassel.vs.cn.planDesigner.command;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.PlanElement;

/**
 * Created by marci on 02.12.16.
 */
public class RenameElement extends AbstractCommand<PlanElement> {
    private String newName;
    private String oldName;

    public RenameElement(PlanElement element, String newName) {
        super(element, (PlanElement) element.eResource().getContents().get(0));
        this.newName = newName;
        oldName = getElementToEdit().getName();
    }

    @Override
    public void doCommand() {
        getElementToEdit().setName(newName);
    }

    @Override
    public void undoCommand() {
        getElementToEdit().setName(oldName);
    }

    @Override
    public String getCommandString() {
        return "Rename Element  from " + oldName + " to " + newName;
    }
}
