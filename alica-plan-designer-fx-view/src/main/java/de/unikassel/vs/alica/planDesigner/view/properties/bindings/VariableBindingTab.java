package de.unikassel.vs.alica.planDesigner.view.properties.bindings;

import de.unikassel.vs.alica.planDesigner.PlanDesignerApplication;
import de.unikassel.vs.alica.planDesigner.events.GuiEventType;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.beans.InvalidationListener;
import javafx.collections.ListChangeListener;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashMap;

public class VariableBindingTab extends Tab {

    private I18NRepo i18NRepo;
    private final IGuiModificationHandler guiModificationHandler;
    private VariableBindingTable variableBindingTable;

    private HasVariableBinding parentViewModel;
    private ListChangeListener<VariableBindingViewModel> bindingListener;

    public VariableBindingTab(IGuiModificationHandler handler) {
        this.guiModificationHandler = handler;
        this.i18NRepo = I18NRepo.getInstance();
        setText(this.i18NRepo.getString("label.caption.variableBindings"));

        // Table
        this.variableBindingTable = new VariableBindingTable() {
            @Override
            protected void onAddElement() {
                VariableBindingTab.this.fireEvent(GuiEventType.CREATE_ELEMENT);
            }

            @Override
            protected void onRemoveElement() {
                VariableBindingTab.this.fireEvent(GuiEventType.DELETE_ELEMENT);
            }
        };
        this.variableBindingTable.addColumn(this.i18NRepo.getString("label.caption.variable"), "variable", new VariableStringConverter(),false);
        this.variableBindingTable.addColumn(this.i18NRepo.getString("label.column.subplan"), "subPlan", new HasVariablesViewStringConverter(),false);
        this.variableBindingTable.addColumn(this.i18NRepo.getString("label.column.subvariable"), "subVariable", new VariableStringConverter(),false);
        this.setContent(this.variableBindingTable);

        // Listener for new bindings (result of add and remove button)
        this.bindingListener = new ListChangeListener<VariableBindingViewModel>() {
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

    public void setParentViewModel(HasVariableBinding parentViewModel) {
        clear();
        this.parentViewModel = parentViewModel;
        this.parentViewModel.getVariableBindings().addListener(bindingListener);

        // fill variables dropdown and sub plans dropdown of table
        ArrayList<AbstractPlanViewModel> hasVariablesViewArrayList = new ArrayList<>();
        if (parentViewModel.getType() == Types.STATE) {
            variableBindingTable.setVariablesDropDownContent(((PlanViewModel) guiModificationHandler.getViewModelElement(this.parentViewModel.getParentId())).getVariables());
            for (PlanElementViewModel abstractPlanViewModel : ((StateViewModel) this.parentViewModel).getAbstractPlans()) {
                hasVariablesViewArrayList.add((AbstractPlanViewModel) abstractPlanViewModel);
            }
            variableBindingTable.setSubPlanDropDownContent(hasVariablesViewArrayList);
        } else if (parentViewModel.getType() == Types.PLANTYPE) {
            variableBindingTable.setVariablesDropDownContent(((PlanTypeViewModel) this.parentViewModel).getVariables());

            //  Listener: Update Variable in Variable BindingsTab
            ((PlanTypeViewModel) this.parentViewModel).getVariables().addListener((InvalidationListener) change -> {
                variableBindingTable.setVariablesDropDownContent(((PlanTypeViewModel) this.parentViewModel).getVariables());
                // Update Variable BindingsTab if Name change
                for (VariableViewModel variableViewModel: ((PlanTypeViewModel) this.parentViewModel).getVariables()) {
                    variableViewModel.nameProperty().addListener(observable -> {
                        variableBindingTable.setVariablesDropDownContent(((PlanTypeViewModel) this.parentViewModel).getVariables());
                    });
                }
            });

            // Listener: Update SubPlan in Variable BindingsTab
            ((PlanTypeViewModel) this.parentViewModel).getPlansInPlanType().addListener((InvalidationListener) change -> {
                hasVariablesViewArrayList.clear();
                for (AnnotatedPlanView annotatedPlanView : ((PlanTypeViewModel) this.parentViewModel).getPlansInPlanType()) {
                    hasVariablesViewArrayList.add((AbstractPlanViewModel) guiModificationHandler.getViewModelElement(annotatedPlanView.getPlanId()));
                }
                variableBindingTable.setSubPlanDropDownContent(hasVariablesViewArrayList);
            });
        } else {
            return;
        }

        // fill binding table
        for (VariableBindingViewModel binding : this.parentViewModel.getVariableBindings()) {
            variableBindingTable.addItem(binding);
        }
    }

    private void clear() {
        if (parentViewModel == null) {
            return;
        }

        this.parentViewModel.getVariableBindings().removeListener(bindingListener);

        this.variableBindingTable.clear();
        this.parentViewModel = null;
    }

    private void createAlert() {
        I18NRepo i18NRepo = I18NRepo.getInstance();

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Invalid Variable Binding!");
        alert.setContentText(i18NRepo.getString("label.error.invalidVariableBinding"));

        ButtonType closeBtn = new ButtonType(i18NRepo.getString("action.close"));

        alert.getButtonTypes().setAll(closeBtn);

        alert.initOwner(PlanDesignerApplication.getPrimaryStage());
        alert.showAndWait();
    }

    private boolean isBindingValid() {
        VariableViewModel selectedVar = this.variableBindingTable.getSelectedVariable();
        VariableViewModel selectedSubVar = this.variableBindingTable.getSelectedSubVariable();
        AbstractPlanViewModel selectedSubPlan = this.variableBindingTable.getSelectedSubPlan();
        if (selectedVar == null || selectedSubPlan == null || selectedSubVar == null) {
            return false;
        }

        for (VariableBindingViewModel variableBindingViewModel : parentViewModel.getVariableBindings()) {
            if (variableBindingViewModel.getSubPlan().getId() == selectedSubPlan.getId()
                    && variableBindingViewModel.getSubVariable().getId() == selectedSubVar.getId()
                    && variableBindingViewModel.getVariable().getId() == selectedVar.getId()) {
                return false;
            }
        }
        return true;
    }

    void fireEvent(GuiEventType eventType) {
        GuiModificationEvent event = new GuiModificationEvent(eventType, Types.VARIABLEBINDING, "Variable Binding");
        event.setParentId(parentViewModel.getId());
        if (eventType == GuiEventType.CREATE_ELEMENT) {
            if (!isBindingValid()) {
                createAlert();
                return;
            }
            HashMap<String, Long> relatedObjects = new HashMap<>();
            relatedObjects.put(Types.VARIABLE, this.variableBindingTable.getSelectedVariable().getId());
            relatedObjects.put(Types.VARIABLEBINDING, this.variableBindingTable.getSelectedSubVariable().getId());
            relatedObjects.put(Types.PLAN, this.variableBindingTable.getSelectedSubPlan().getId());
            event.setRelatedObjects(relatedObjects);
        } else {
            VariableBindingViewModel binding = this.variableBindingTable.getSelectedItem();
            if (binding != null) {
                event.setElementId(binding.getId());
            } else {
                return;
            }
        }
        guiModificationHandler.handle(event);
    }
}
