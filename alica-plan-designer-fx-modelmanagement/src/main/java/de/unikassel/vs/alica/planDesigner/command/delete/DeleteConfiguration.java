package de.unikassel.vs.alica.planDesigner.command.delete;

import de.unikassel.vs.alica.planDesigner.alicamodel.Configuration;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class DeleteConfiguration extends Command {

    private Configuration configuration;

    public DeleteConfiguration(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.configuration = (Configuration) modelManager.getPlanElement(mmq.getElementId());
    }

    @Override
    public void doCommand() {
        if(this.configuration == null) {
            return;
        }
        modelManager.dropPlanElement(Types.CONFIGURATION, this.configuration, false);
        fireEvent(ModelEventType.ELEMENT_DELETED, this.configuration);
    }

    @Override
    public void undoCommand() {
        if(this.configuration == null) {
            return;
        }
        modelManager.storePlanElement(Types.CONFIGURATION, this.configuration, false);
        fireEvent(ModelEventType.ELEMENT_CREATED, this.configuration);
    }
}
