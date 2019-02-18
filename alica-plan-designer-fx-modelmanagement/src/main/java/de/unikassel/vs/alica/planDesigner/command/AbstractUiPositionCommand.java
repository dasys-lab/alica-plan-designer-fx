package de.unikassel.vs.alica.planDesigner.command;

import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.UiExtensionModelModificationQuery;

public abstract class AbstractUiPositionCommand extends AbstractCommand {

    protected int x;
    protected int y;

    public AbstractUiPositionCommand(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager);
        if(mmq instanceof UiExtensionModelModificationQuery) {
            UiExtensionModelModificationQuery uimmq = (UiExtensionModelModificationQuery)mmq;
            x = uimmq.getNewX();
            y = uimmq.getNewY();
        } else {
            x = 0;
            y = 0;
        }

    }
}

