package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.ListChangeListener;
import javafx.event.EventHandler;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;

public class RoleListView extends ListView<RoleListLabel> {

    private IGuiModificationHandler guiModificationHandler;
    private ArrayList<PropertyChangeListener> propertyChangeListeners;

    public RoleListView() {
        super();
        this.propertyChangeListeners = new ArrayList<>();
        this.setCellFactory(param -> new RoleCell());
        this.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (PropertyChangeListener listener:propertyChangeListeners) {
                listener.propertyChange(new PropertyChangeEvent(RoleListView.this, "selected_item", oldValue, newValue));
            }
        });
        this.setOnContextMenuRequested(event -> ((RoleListView) event.getSource()).getSelectionModel().getSelectedItem().fireEvent(event));
    }

    public void setFocus() {
         Platform.runLater(()->  {
        this.getSelectionModel().select(0);
        this.getFocusModel().focus(0);
        this.requestFocus();
    });
}

    public void removeElement(ViewModelElement viewModel) {
        Iterator<RoleListLabel> iter = getItems().iterator();

        while (iter.hasNext()) {
            RoleListLabel roleLabel = iter.next();

            if (roleLabel.getViewModelId() == viewModel.getId()) {
                iter.remove();
                return;
            }
        }
    }

    public void addElement(ViewModelElement viewModelElement) {
        Platform.runLater(() -> getItems().add(new RoleListLabel(this, viewModelElement, guiModificationHandler)));
    }

    public void addElements(List<? extends ViewModelElement> viewModelElements) {

        for (ViewModelElement viewModelElement : viewModelElements) {
            addElement(viewModelElement);
        }
    }

    public RoleViewModel getSelectedItem() {
        MultipleSelectionModel<RoleListLabel> selectionModel = this.getSelectionModel();
        return selectionModel != null && selectionModel.getSelectedItem() != null ? (RoleViewModel) selectionModel.getSelectedItem().getViewModelElement() : null;
    }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }

    public IGuiModificationHandler getGuiModificationHandler() {
        return guiModificationHandler;
    }

    public void addSelectionListener(PropertyChangeListener listener) {
        this.propertyChangeListeners.add(listener);
    }

    private static class RoleCell extends ListCell<RoleListLabel> {

        @Override
        protected void updateItem(RoleListLabel item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null || item.getViewModelName() == null) {
                setText(null);
            } else {
                setText(item.getViewModelName());
            }
        }
    }
}
