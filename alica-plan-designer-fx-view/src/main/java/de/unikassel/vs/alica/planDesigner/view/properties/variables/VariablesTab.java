package de.unikassel.vs.alica.planDesigner.view.properties.variables;

import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.ListChangeListener;
import javafx.scene.control.Tab;
import javafx.util.converter.DefaultStringConverter;

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
