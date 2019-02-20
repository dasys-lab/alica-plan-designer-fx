package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.*;

public class CreateBehaviour extends AbstractCommand {
    Behaviour behaviour;

    public CreateBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.BEHAVIOUR)) {
            this.behaviour = new Behaviour();
            this.behaviour.setName(mmq.getName());
            this.behaviour.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), behaviour.getName()+ "." + Extensions.BEHAVIOUR));
        }
        else
        {
            System.err.println("CreateBehaviour: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.storePlanElement(Types.BEHAVIOUR, behaviour, null, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removedPlanElement(Types.BEHAVIOUR, behaviour, null, true);
    }
}
