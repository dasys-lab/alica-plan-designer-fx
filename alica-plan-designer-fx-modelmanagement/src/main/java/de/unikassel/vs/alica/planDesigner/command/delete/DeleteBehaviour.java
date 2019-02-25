package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteBehaviour extends Command {

    protected Behaviour behaviour;

    public DeleteBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        behaviour = (Behaviour) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if (behaviour == null) {
            return;
        }
        modelManager.dropPlanElement(Types.BEHAVIOUR, behaviour, true);
    }

    @Override
    public void undoCommand() {
        if (behaviour == null) {
            return;
        }
        modelManager.storePlanElement(Types.BEHAVIOUR, behaviour,true);
    }
}
