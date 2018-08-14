package de.uni_kassel.vs.cn.planDesigner.command.create;

import de.uni_kassel.vs.cn.planDesigner.alicamodel.Behaviour;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.FileSystemUtil;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelManager;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.ModelModificationQuery;
import de.uni_kassel.vs.cn.planDesigner.modelmanagement.Types;

public class CreateBehaviour extends AbstractCommand {
    Behaviour behaviour;

    public CreateBehaviour(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if (mmq.getElementType().equals(Types.BEHAVIOUR)) {
            this.behaviour = new Behaviour();
            this.behaviour.setName(mmq.getName());
            this.behaviour.setRelativeDirectory(modelManager.makeRelativePlansDirectory(mmq.getAbsoluteDirectory(), behaviour.getName()+ "." + FileSystemUtil.BEHAVIOUR_ENDING));
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
        modelManager.removeFromDisk(behaviour, FileSystemUtil.BEHAVIOUR_ENDING);
    }
}
