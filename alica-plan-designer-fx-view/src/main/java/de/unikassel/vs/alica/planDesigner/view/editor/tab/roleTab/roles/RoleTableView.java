package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.model.RoleSetViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.MultipleSelectionModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class RoleTableView extends PropertiesTable<RoleTableElement> {

    private ObservableList<RoleTableElement> roleTableElements;

    private RoleSetViewModel    roleSetViewModel;

    private IGuiModificationHandler guiModificationHandler;
    private ArrayList<PropertyChangeListener> listeners;

    public RoleTableView(RoleSetViewModel roleSetViewModel) {
        super();
        setId("RoleTableView");
        this.roleSetViewModel        = roleSetViewModel;
        this.roleTableElements       = FXCollections.observableArrayList();
        this.listeners               = new ArrayList<>();
        this.setItems(roleTableElements);

//        this.roleTableElements.addListener(new ListChangeListener<RoleTableElement>() {
//            @Override
//            public void onChanged(Change<? extends RoleTableElement> c) {
//                ObservableList<? extends RoleTableElement> list = c.getList();
//                if(list.size() < 1)
//                    return;
//                RoleTableElement element = list.get(list.size() - 1);
//            }
//        });

        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            for (PropertyChangeListener listener : listeners) {
                listener.propertyChange(new PropertyChangeEvent(RoleTableView.this, "selected_item", oldValue, newValue));
            }
        });
    }

    public void addPlaceholder() {
        RoleViewModelCreatable creatable = new RoleViewModelCreatable(0, "", Types.ROLE );
        creatable.setRoleSetViewModel(this.roleSetViewModel);
        RoleTableElement element = new RoleTableElement(this, creatable);
        roleTableElements.add(element);
    }

    private void updateCells() {
        this.roleTableElements.clear();

        for (RoleViewModel roleViewModel : roleSetViewModel.getRoleViewModels()) {
            addElement(roleViewModel);
        }
        addPlaceholder();
    }

    public void initTable(RoleSetViewModel roleSetViewModel) {
        this.roleSetViewModel = roleSetViewModel;
        updatePlaceholder();
    }

    public void addSelectionListener(PropertyChangeListener listener) {
        this.listeners.add(listener);
    }

    public void updatePlaceholder() {
        if(roleTableElements.size() > 0 && roleTableElements.get(roleTableElements.size() - 1).getViewModel().getName().equals(""))
            return;
        updateCells();
    }

    public IGuiModificationHandler getGuiModificationHandler() {
        return guiModificationHandler;
    }
    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }

    public void setFocus() {
        Platform.runLater(() -> {
            this.getSelectionModel().select(0);
            this.getFocusModel().focus(0);
            this.requestFocus();
        });
    }

    public RoleViewModel getSelectedItem() {
        MultipleSelectionModel<RoleTableElement> selectionModel = this.getSelectionModel();
        return selectionModel != null && selectionModel.getSelectedItem() != null ? (RoleViewModel) selectionModel.getSelectedItem().getViewModelElement() : null;
    }

    public void removeElement(ViewModelElement viewModel) {
        Iterator<RoleTableElement> iter = getItems().iterator();

        while (iter.hasNext()) {
            RoleTableElement tableElement = iter.next();

            if (tableElement.getViewModelId() == viewModel.getId()) {
                iter.remove();
                return;
            }
        }
    }

    public void addElement(ViewModelElement viewModelElement) {
        RoleTableElement element = new RoleTableElement(this, viewModelElement, guiModificationHandler);
//        RoleTableElement element = new RoleTableElement(this, viewModelElement);
        roleTableElements.add(roleTableElements.size() > 0 ? roleTableElements.size()-1 : 0, element);
    }

    public void addElements(List<? extends ViewModelElement> viewModelElements) {

        for (ViewModelElement viewModelElement : viewModelElements) {
            addElement(viewModelElement);
        }
    }
}
