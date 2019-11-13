package de.unikassel.vs.alica.planDesigner.view.properties.variables;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Tab;
import javafx.util.converter.DefaultStringConverter;

import java.util.Optional;

public class VariablesTab extends Tab {

    VariablesTable<VariableViewModel> variablesTable;
    I18NRepo i18NRepo;
    ViewModelElement parentViewModel;
    IGuiModificationHandler guiModificationHandler;
    ListChangeListener<VariableViewModel> varListener;

    public VariablesTab(IGuiModificationHandler handler) {
        this.guiModificationHandler = handler;
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.variables"));

        variablesTable = new VariablesTable<VariableViewModel>() {
            @Override
            protected void onAddElement() {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.VARIABLE, "NEW_VARIABLE");
                event.setParentId(parentViewModel.getId());
                guiModificationHandler.handle(event);
            }

            @Override
            protected void onRemoveElement() {
                VariableViewModel selectedVariable = variablesTable.getSelectedItem();
                if (selectedVariable == null) {
                    return;
                }

                boolean used = false;
                long variableBindingsID = 0;
                // If the Variable is used in VariableBindings
                if (parentViewModel instanceof PlanTypeViewModel) {
                    for (VariableBindingViewModel variableBindingViewModel :  ((PlanTypeViewModel) parentViewModel).getVariableBindings()) {
                        if (variableBindingViewModel.getVariable().getId() == selectedVariable.getId()) {
                            used = true;
                            variableBindingsID = variableBindingViewModel.getId();
                        }
                    }
                }
                if(parentViewModel instanceof PlanViewModel){
                    ((PlanViewModel) parentViewModel).getStates();
                    for (StateViewModel stateViewModel:((PlanViewModel) parentViewModel).getStates()) {
                        for (Object object:stateViewModel.getVariableBindings()) {
                            VariableBindingViewModel variableBindingViewModel = (VariableBindingViewModel) object;
                            if(variableBindingViewModel.getVariable().getId() == selectedVariable.getId()){
                                used = true;
                                variableBindingsID = variableBindingViewModel.getId();
                            }
                        }
                    }
                }

                if(used){
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("The Variable is still used in VariableBindings!");
                    alert.setHeaderText("If you delete the Variable, the VariableBinding deleted, too.");
                    alert.setContentText("Delete Variable?");

                    Optional<ButtonType> result = alert.showAndWait();
                    // Delete the VariableBindings Event
                    if (result.get() == ButtonType.OK){
                        GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.VARIABLEBINDING, "Variable Binding");
                        event.setParentId(parentViewModel.getId());
                        event.setElementId(variableBindingsID);
                        guiModificationHandler.handle(event);
                    } else {
                        return;
                    }
                }

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.VARIABLE, selectedVariable.getName());
                event.setParentId(parentViewModel.getId());
                event.setElementId(selectedVariable.getId());
                guiModificationHandler.handle(event);
            }
        };

        variablesTable.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        variablesTable.addColumn(i18NRepo.getString("label.column.elementType"), "variableType", new DefaultStringConverter(), true);
        variablesTable.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);

        varListener = new ListChangeListener<VariableViewModel>() {
            public void onChanged(Change<? extends VariableViewModel> c) {
                while (c.next()) {
                    for (VariableViewModel remitem : c.getRemoved()) {
                        variablesTable.removeItem(remitem);
                    }
                    for (VariableViewModel additem : c.getAddedSubList()) {
                        variablesTable.addItem(additem);
                    }
                }
            }
        };

        setContent(variablesTable);
    }

    public void setParentViewModel(ViewModelElement parentViewModel) {
        clear();
        this.parentViewModel = parentViewModel;

        // fill table and register listener
        for (VariableViewModel var : ((AbstractPlanViewModel) parentViewModel).getVariables()) {
            variablesTable.addItem(var);
        }

        ((AbstractPlanViewModel) parentViewModel).getVariables().addListener(varListener);
    }

    private void clear() {
        if (parentViewModel == null) {
            return;
        }

        ((AbstractPlanViewModel) this.parentViewModel).getVariables().removeListener(varListener);

        variablesTable.clear();
        this.parentViewModel = null;
    }
}
