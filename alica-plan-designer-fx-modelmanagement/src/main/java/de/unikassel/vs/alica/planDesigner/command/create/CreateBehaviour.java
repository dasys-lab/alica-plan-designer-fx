package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.command.AbstractCommand;
import de.unikassel.vs.alica.planDesigner.modelmanagement.FileSystemUtil;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateBehaviour extends AbstractCommand {
    Behaviour behaviour;

    public CreateBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.BEHAVIOUR)) {
            this.behaviour = new Behaviour();
            this.behaviour.setName(mmq.getName());
            this.behaviour.setRelativeDirectory(modelManager.makeRelativeDirectory(mmq.getAbsoluteDirectory(), behaviour.getName()+ "." + FileSystemUtil.BEHAVIOUR_ENDING));
        }
        else
        {
            System.err.println("CreateBehaviour: Type does not match command!");
        }
    }

    @Override
    public void doCommand() {
        modelManager.serializeToDisk(behaviour, FileSystemUtil.BEHAVIOUR_ENDING, true);
    }

    @Override
    public void undoCommand() {
        modelManager.removeFromDisk(behaviour, FileSystemUtil.BEHAVIOUR_ENDING, true);
    }
}
