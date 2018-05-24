package de.uni_kassel.vs.cn.planDesigner.command.delete;

import de.uni_kassel.vs.cn.planDesigner.aggregatedModel.PlanModelVisualisationObject;
import de.uni_kassel.vs.cn.planDesigner.command.AbstractCommand;
import de.uni_kassel.vs.cn.planDesigner.alica.State;
import de.uni_kassel.vs.cn.planDesigner.alica.Transition;
import de.uni_kassel.vs.cn.planDesigner.emfUtil.EMFModelUtils;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.PmlUiExtension;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.EObjectToPmlUiExtensionMapEntryImpl;
import de.uni_kassel.vs.cn.planDesigner.pmlextension.uiextensionmodel.impl.PmlUIExtensionModelFactoryImpl;
import org.eclipse.emf.ecore.EObject;

import java.util.Map;

/**
 * Created by marci on 01.12.16.
 */
public class DeleteTransitionInPlan extends AbstractCommand<Transition> {

    private final PlanModelVisualisationObject parentOfElement;
    private PmlUiExtension pmlUiExtension;
    private State inState;
    private State outState;

    public DeleteTransitionInPlan(Transition element, PlanModelVisualisationObject parentOfElement) {
        super(element, parentOfElement.getPlan());
        this.parentOfElement = parentOfElement;
    }

    private void saveForLaterRetrieval() {
        pmlUiExtension = parentOfElement.getPmlUiExtensionMap().getExtension().get(getElementToEdit());
        outState = getElementToEdit().getOutState();
        inState = getElementToEdit().getInState();
    }

    @Override
    public void doCommand() {
        saveForLaterRetrieval();

        parentOfElement.getPlan().getTransitions().remove(getElementToEdit());
        parentOfElement.getPmlUiExtensionMap().getExtension().remove(getElementToEdit());
        getElementToEdit().setInState(null);
        getElementToEdit().setOutState(null);
    }

    @Override
    public void undoCommand() {
        parentOfElement.getPlan().getTransitions().add(getElementToEdit());
        Map.Entry<EObject, PmlUiExtension> eObjectToPmlUiExtensionMapEntry = ((PmlUIExtensionModelFactoryImpl) EMFModelUtils.getPmlUiExtensionModelFactory()).createEObjectToPmlUiExtensionMapEntry();
        ((EObjectToPmlUiExtensionMapEntryImpl)eObjectToPmlUiExtensionMapEntry).setKey(getElementToEdit());
        eObjectToPmlUiExtensionMapEntry.setValue(pmlUiExtension);
        parentOfElement.getPmlUiExtensionMap().getExtension()
                .add(eObjectToPmlUiExtensionMapEntry);
        getElementToEdit().setInState(inState);
        getElementToEdit().setOutState(outState);
    }

    @Override
    public String getCommandString() {
        return "Delete Transition " + getElementToEdit().getComment() + " in Plan " + parentOfElement.getPlan().getName();
    }
}
