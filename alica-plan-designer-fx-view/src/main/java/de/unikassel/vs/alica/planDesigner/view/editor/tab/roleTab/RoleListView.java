package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.application.Platform;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;

import java.util.Iterator;
import java.util.List;

public class RoleListView extends ListView<RoleListLabel> {

    private IGuiModificationHandler guiModificationHandler;

    public RoleListView() {
        super();
        this.setCellFactory(param -> new RoleCell());
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

    public ViewModelElement getSelectedItem() {
        MultipleSelectionModel<RoleListLabel> selectionModel = this.getSelectionModel();
        return selectionModel != null ? selectionModel.getSelectedItem().getViewModelElement() : null;
    }

    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
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
