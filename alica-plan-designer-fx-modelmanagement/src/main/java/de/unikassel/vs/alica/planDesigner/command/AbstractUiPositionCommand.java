package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;

public abstract class AbstractUiPositionCommand extends AbstractCommand {

    protected int x;
    protected int y;

    public AbstractUiPositionCommand(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        x = mmq.getX();
        y = mmq.getY();
    }
}

