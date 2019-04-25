package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import com.sun.javafx.scene.control.skin.LabeledText;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.menu.DeleteElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.RenameElementMenuItem;
import de.unikassel.vs.alica.planDesigner.view.menu.ShowUsagesMenuItem;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryLabel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MultipleSelectionModel;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.Iterator;
import java.util.List;

public class RoleListView extends ListView<RoleListLabel> {

    private IGuiModificationHandler guiModificationHandler;

    public RoleListView() {
        super();
        this.setCellFactory(param -> new RoleCell());

//        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//
//                if(event.getButton() == MouseButton.SECONDARY &&
//                        event.getSource() instanceof RoleListView && event.getTarget() instanceof LabeledText) {
//                    RoleListView roleListView = (RoleListView) event.getSource();
//                    System.out.println("RoleListView:Contructor " + roleListView.getSelectedItem().getName());
//                    MultipleSelectionModel<RoleListLabel> selectionModel = roleListView.getSelectionModel();
//                    RoleListLabel selectedItem = selectionModel.getSelectedItem();
//                    RenameElementMenuItem renameFileMenuItem = new RenameElementMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                    ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                    DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                    ContextMenu contextMenu = new ContextMenu(renameFileMenuItem, usageMenu, deleteMenu);
//                    contextMenu.show(RoleListView.this, event.getScreenX(), event.getScreenY());
//                    event.consume();
//                }
//            }
//        });
//        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
//            @Override
//            public void handle(ContextMenuEvent event) {
//                RoleListView roleListView = (RoleListView) event.getSource();
//                RenameElementMenuItem renameFileMenuItem = new RenameElementMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                ShowUsagesMenuItem usageMenu = new ShowUsagesMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                DeleteElementMenuItem deleteMenu = new DeleteElementMenuItem(roleListView.getSelectedItem(), guiModificationHandler);
//                ContextMenu contextMenu = new ContextMenu(renameFileMenuItem, usageMenu, deleteMenu);
//                contextMenu.show(RoleListView.this, event.getScreenX(), event.getScreenY());
//                event.consume();
//            }
//        });
        this.setOnContextMenuRequested(new EventHandler<ContextMenuEvent>() {
            @Override
            public void handle(ContextMenuEvent event) {
                System.out.println("RLV:setOnContextMenu ");
                RoleListView roleListView = (RoleListView) event.getSource();
                RoleListLabel selectedItem = roleListView.getSelectionModel().getSelectedItem();
                selectedItem.fireEvent(event);
            }
        });
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
//        Platform.runLater(() -> {

            for (ViewModelElement viewModelElement : viewModelElements) {
//                getItems().add(new RoleListLabel(viewModelElement, guiModificationHandler));
                addElement(viewModelElement);
            }
//        });
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
