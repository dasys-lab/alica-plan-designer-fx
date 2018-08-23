package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class DeleteBehaviour extends AbstractCommand {

    protected Behaviour behaviour;

    public DeleteBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        behaviour = (Behaviour) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (behaviour == null) {
            return;
        }
        modelManager.removePlanElement(Types.BEHAVIOUR, behaviour, null, true);
    }

    @Override
    public void undoCommand() {
        if (behaviour == null) {
            return;
        }
        modelManager.addPlanElement(Types.BEHAVIOUR, behaviour, null, true);
    }
}
