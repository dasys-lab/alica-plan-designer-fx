package de.unikassel.vs.alica.planDesigner.command.create;

import de.unikassel.vs.alica.planDesigner.alicamodel.Condition;
import de.unikassel.vs.alica.planDesigner.alicamodel.Quantifier;
import de.unikassel.vs.alica.planDesigner.command.Command;
import de.unikassel.vs.alica.planDesigner.events.ModelEventType;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelModificationQuery;
import de.unikassel.vs.alica.planDesigner.modelmanagement.Types;

public class CreateQuantifier extends Command {

    private final Condition parentElement;
    private final Quantifier quantifier;

    public CreateQuantifier(ModelManager modelManager, ModelModificationQuery mmq) {
        super(modelManager, mmq);
        this.parentElement = (Condition) modelManager.getPlanElement(mmq.getParentId());
        this.quantifier = createQuantifier();
    }

    @Override
    public void doCommand() {
        parentElement.addQuantifier(quantifier);
        this.modelManager.storePlanElement(Types.QUANTIFIER, quantifier, false);
        this.fireEvent(ModelEventType.ELEMENT_CREATED, this.quantifier);
    }

    @Override
    public void undoCommand() {
        parentElement.removeQuantifier(quantifier);
        this.modelManager.dropPlanElement(Types.QUANTIFIER, this.quantifier, false);
        this.fireEvent(ModelEventType.ELEMENT_DELETED, this.quantifier);
    }

    private Quantifier createQuantifier() {
        Quantifier quantifier = new Quantifier();
        quantifier.setName(mmq.getName());
        quantifier.setComment(mmq.getComment());
        return quantifier;
    }
}
