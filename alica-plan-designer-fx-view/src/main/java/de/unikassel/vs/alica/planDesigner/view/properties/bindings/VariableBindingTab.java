package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.util.HashMap;

public class VariableBindingTab extends Tab {

    I18NRepo i18NRepo;
    private final IGuiModificationHandler guiModificationHandler;
    VariableBindingTable variableBindingTable;

    private HasVariableBinding parentViewModel;
    ListChangeListener<VariableBindingViewModel> bindingListener;

    public VariableBindingTab(IGuiModificationHandler handler) {
        this.guiModificationHandler = handler;
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.variableBindings"));

        variableBindingTable = new VariableBindingTable() {
            @Override
            protected void onAddElement() {
                VariableBindingTab.this.fireEvent(GuiEventType.CREATE_ELEMENT);
            }

            @Override
            protected void onRemoveElement() {
                System.err.println("VariableBindingTab: Delete not implemented, yet!");
            }
        };

        bindingListener = new ListChangeListener<VariableBindingViewModel>() {
            public void onChanged(Change<? extends VariableBindingViewModel> c) {
                while (c.next()) {
                    for (VariableBindingViewModel remitem : c.getRemoved()) {
                        variableBindingTable.removeItem(remitem);
                    }
                    for (VariableBindingViewModel additem : c.getAddedSubList()) {
                        variableBindingTable.addItem(additem);
                    }
                }
            }
        };
    }

    private boolean isBindingValid() {
        VariableViewModel selectedVar = this.variableBindingTable.getSelectedVariable();
        VariableViewModel selectedSubVar = this.variableBindingTable.getSelectedSubVariable();
        HasVariablesView selectedSubPlan = this.variableBindingTable.getSelectedSubPlan();
        if (selectedVar == null || selectedSubPlan == null || selectedSubVar == null) {
            return false;
        }

        for (VariableBindingViewModel variableBindingViewModel : ((HasVariableBinding) parentViewModel).getVariableBindings()) {
            if (variableBindingViewModel.getSubPlan().getId() == selectedSubPlan.getId()
                    && variableBindingViewModel.getSubVariable().getId() == selectedSubVar.getId()
                    && variableBindingViewModel.getVariable().getId() == selectedVar.getId()) {
                return false;
            }
        }
        return true;
    }

    public void fireEvent(GuiEventType eventType) {
        if (!isBindingValid()) {
            return;
        }
        GuiModificationEvent event = new GuiModificationEvent(eventType, Types.VARIABLEBINDING, "Variable Binding");
        event.setParentId(parentViewModel.getId());
        if (eventType == GuiEventType.CREATE_ELEMENT) {
            HashMap<String, Long> relatedObjects = new HashMap<>();
            relatedObjects.put(Types.VARIABLE, this.variableBindingTable.getSelectedVariable().getId());
            relatedObjects.put(Types.VARIABLEBINDING, this.variableBindingTable.getSelectedSubVariable().getId());
            relatedObjects.put(Types.PLAN, this.variableBindingTable.getSelectedSubPlan().getId());
            event.setRelatedObjects(relatedObjects);
        } else {
            event.setElementId(this.variableBindingTable.getSelectedItem().getId());
        }
        guiModificationHandler.handle(event);
    }

    public void setParentViewModel(HasVariableBinding parentViewModel) {
        clear();
        this.parentViewModel = parentViewModel;

        // fill variables dropdown of table
        ObservableList<VariableViewModel> variables;
        if (parentViewModel.getType() == Types.STATE) {
            variableBindingTable.fillVariablesDropDown(((PlanViewModel) guiModificationHandler.getViewModelElement(this.parentViewModel.getParentId())).getVariables());
        } else if (parentViewModel.getType() == Types.PLANTYPE) {
            variableBindingTable.fillVariablesDropDown(((PlanTypeViewModel) this.parentViewModel).getVariables());
        } else {
            return;
        }

        // fill binding table
        for (VariableBindingViewModel binding : this.parentViewModel.getVariableBindings()) {
            variableBindingTable.addItem(binding);
        }

        this.parentViewModel.getVariableBindings().addListener(bindingListener);
    }

    private void clear() {
        if (parentViewModel == null) {
            return;
        }

        this.parentViewModel.getVariableBindings().removeListener(bindingListener);

        this.variableBindingTable.clear();
        this.parentViewModel = null;
    }
}
