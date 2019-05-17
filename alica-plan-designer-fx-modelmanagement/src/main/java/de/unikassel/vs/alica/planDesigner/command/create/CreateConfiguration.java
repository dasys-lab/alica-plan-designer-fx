package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Behaviour;
import de.unikassel.vs.alica.planDesigner.alicamodel.Configuration;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateConfiguration extends Command {

    private Configuration configuration;

    public CreateConfiguration(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.configuration = createConfiguration();
    }

    private Configuration createConfiguration() {
        Configuration configuration = new Configuration();
        configuration.setName(mmq.getName());
        configuration.setComment(mmq.getComment());

        Behaviour behaviour = (Behaviour) modelManager.getPlanElement(mmq.getParentId());
        configuration.setBehaviour(behaviour);
        configuration.setRelativeDirectory(behaviour.getRelativeDirectory());

        return configuration;
    }

    @Override
    public void doCommand() {
        modelManager.storePlanElement(Types.CONFIGURATION, this.configuration, false);
        fireEvent(ModelEventType.ELEMENT_CREATED, this.configuration);
    }

    @Override
    public void undoCommand() {
        modelManager.dropPlanElement(Types.CONFIGURATION, this.configuration, false);
        fireEvent(ModelEventType.ELEMENT_DELETED, this.configuration);
    }
}
