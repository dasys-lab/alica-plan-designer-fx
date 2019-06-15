package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics;

import com.sun.javafx.scene.control.skin.LabeledText;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.roles.RoleTableView;
import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleSetViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.*;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.controlsfx.control.PopOver;
import org.omg.CORBA.OBJ_ADAPTER;

import javax.swing.text.TableView;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

public class CharacteristicsTableView extends PropertiesTable<CharacteristicsTableElement> {

    private PopOver popOver;
    private ObservableList<CharacteristicsTableElement> characteristicsTableElements;
    private ContextMenu contextMenu;

    private RoleTableView        roleTableView;
    private RoleSetViewModel    roleSetViewModel;
    private RoleViewModel       currentRoleViewModel;

    private IGuiModificationHandler guiModificationHandler;
    private PropertyChangeListener  eventListener;

    private String defaultValue;

    public CharacteristicsTableView(RoleSetViewModel roleSetViewModel, RoleTableView roleTableView) {
        super();
        this.roleSetViewModel = roleSetViewModel;
        this.roleTableView = roleTableView;
        this.defaultValue = "";
        characteristicsTableElements = FXCollections.observableArrayList();

        this.setItems(characteristicsTableElements);
//        this.roleSetViewModel.getRoleViewModels().addListener(new ListChangeListener<RoleViewModel>() {
//            @Override
//            public void onChanged(Change<? extends RoleViewModel> c) {
//                System.out.println("CTV: role set changed: " + ((RoleViewModel)c.getList().get(c.getList().size()-1)).getName());
//            }
//        });
//        this.roleListView.addSelectionListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                System.out.println("CTV: selected role changed: " + ((RoleListLabel)evt.getNewValue()).getViewModelElement().getName());
//            }
//        });

        setOnMouseClicked(e -> {
//            System.out.println(e.getTarget());
            int index = getSelectionModel().selectedIndexProperty().get();

            if ( popOver != null && popOver.isShowing()) {
                popOver.hide();
                return;
            }
            PickResult pickResult = e.getPickResult();
            ObservableList<RoleViewModel> roleViewModels = roleSetViewModel.getRoleViewModels();

            ListView listView = new ListView();
            listView.getStyleClass().addAll("combo-box-popup");
            for (RoleViewModel roleViewModel:roleViewModels) {
                ObservableList<CharacteristicViewModel> characteristicViewModels = roleViewModel.getCharacteristicViewModels();

                for (CharacteristicViewModel characteristicViewModel :characteristicViewModels) {
                    if (!characteristicExists(characteristicViewModel.getName())) {
                        addToListView(characteristicViewModel.getName(), listView);
                    }
                }
            }
            popOver = new PopOver(listView);
            popOver.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
            popOver.setArrowSize(0.0);
            popOver.setAutoFix(true);
            popOver.setAutoHide(true);
            popOver.setHideOnEscape(true);
            popOver.setDetachable(true);
            popOver.show(pickResult.getIntersectedNode());
            listView.addEventHandler(EventType.ROOT, new EventHandler<Event>() {
//            listView.addEventHandler(MouseEvent.ANY, new EventHandler<Event>() {
                @Override
                public void handle(Event event) {
//                    System.out.println(event.getEventType().getName());
                    switch (event.getEventType().toString()) {
                        case "MOUSE_EXITED":
                            popOver.hide();
                            event.consume();
                            break;
                        case "MOUSE_CLICKED":
                            setCharacteristicName((String) listView.getSelectionModel().getSelectedItem());
                            event.consume();
                            popOver.hide();
                            break;
                    }
                }
            });

//            Bounds bounds = pickResult.getIntersectedNode().localToScreen(pickResult.getIntersectedNode().getBoundsInLocal());
//            ArrayList<MenuItem> selection = new ArrayList<>();
//            listView.cellFactoryProperty().bind(control.cellFactoryProperty());
//
//            for (RoleViewModel roleViewModel:roleViewModels) {
//                ObservableList<CharacteristicViewModel> characteristicViewModels = roleViewModel.getCharacteristicViewModels();
//
//                for (CharacteristicViewModel characteristicViewModel :characteristicViewModels) {
//                    MenuItem menuItem = new MenuItem(index + " " + characteristicViewModel.getName());
//                    menuItem.setStyle("-fx-pref-width: "+ (bounds.getWidth()-20) +";");
//                    selection.add(menuItem);
//                }
//            }
////            contextMenu = new ContextMenu(selection.toArray(new MenuItem[selection.size()]));
//            contextMenu.show(CharacteristicsTableView.this, bounds.getMinX(), bounds.getMaxY());
//            contextMenu.setOnAction(new EventHandler<ActionEvent>() {
//                @Override
//                public void handle(ActionEvent event) {
//                    String text = ((MenuItem) event.getTarget()).getText();
//                    System.out.println();
//                }
//            });
//            pickResult.getIntersectedNode().setOnMouseExited(new EventHandler<MouseEvent>() {
//                @Override
//                public void handle(MouseEvent event) {
//                    contextMenu.hide();
//                }
//            });
//
//            e.consume();
        });
    }

    private void addToListView(Object object, ListView listView) {

        for (Object item:listView.getItems()) {
            if (item.equals(object))
                return;
        }
        listView.getItems().add(object);
    }

    private void setCharacteristicName(String name) {

        if (characteristicExists(name))
            return;
        TableViewSelectionModel<CharacteristicsTableElement> selectionModel = getSelectionModel().getTableView().getSelectionModel();
        CharacteristicsTableElement selectedItem = selectionModel.getSelectedItem();
        selectedItem.nameProperty().setValue(name);
    }

    private boolean characteristicExists(String name) {
        ObservableList<CharacteristicViewModel> characteristicViewModels = getCurrentRole().getCharacteristicViewModels();

        for (CharacteristicViewModel model: characteristicViewModels) {

            if (model.nameProperty().getValue().equals(name))
                return true;
        }
        return false;
    }

    public void updateSelectedRole(RoleViewModel roleViewModel) {
        this.currentRoleViewModel = roleViewModel;
        this.currentRoleViewModel.addChangeCharacteristicsListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                if (!c.next())
                    return;
                List addedSubList = c.getAddedSubList();
//                System.out.println("CTV: update Cells (change characteristic " + addedSubList.get(0) + " " + addedSubList.size()+")");
                updateCells();
            }
        });
        this.updateCells();
    }

    public void addCharacteristics(ObservableList<CharacteristicViewModel> characteristicViewModels) {
        ObservableList<CharacteristicsTableElement> characteristics = FXCollections.observableArrayList();

        characteristicViewModels.forEach(characteristicViewModel -> {
            CharacteristicsTableElement element = new CharacteristicsTableElement(this, characteristicViewModel, defaultValue, "");
//            element.addListener(this.eventListener);
            characteristics.add(element);
        });
        this.setItems(characteristics);
    }

    public void addCharacteristic(CharacteristicViewModel characteristicViewModel) {
            CharacteristicsTableElement element = new CharacteristicsTableElement(this,
                    characteristicViewModel, defaultValue, "");
//            element.addListener(this.eventListener);
            characteristicsTableElements.add(element);
    }

    public void addPlaceholder() {
        CharacteristicViewModel characteristicViewModel = new CharacteristicViewModelCreatable(0,"", Types.ROLE_CHARCTERISTIC, roleTableView );
//            CharacteristicsTablePlaceholder element = new CharacteristicsTablePlaceholder(this,
//                    characteristicViewModel, "", "");
        CharacteristicsTableElement element = new CharacteristicsTableElement(this, characteristicViewModel, "", "");
            characteristicsTableElements.add(element);
    }

    private void updateCells() {
        RoleViewModel currentRole = getCurrentRole();
        ObservableList<CharacteristicViewModel> roleCharacteristics = currentRole.getCharacteristicViewModels();
        this.characteristicsTableElements.clear();

        for (CharacteristicViewModel characteristicViewModel : roleCharacteristics) {
//            System.out.println("CTV: update Cells " + characteristicViewModel.getName());
            addCharacteristic(characteristicViewModel);
        }
        addPlaceholder();

//        for (Object item : this.getItems()) {
//            long id = ((CharacteristicsTableElement) item).getViewModel().getId();
//            currentRole = getCurrentRole();
//            String value = currentRole.getRoleCharacteristicValue(((CharacteristicsTableElement) item).getViewModel().getId());
//            value = value == null ? defaultValue : value;
////            ((CharacteristicsTableElement) item).removeListener();
//            ((CharacteristicsTableElement) item).setValue(value);
//        }
    }

    protected RoleViewModel getCurrentRole() {
        return (RoleViewModel) roleTableView.getSelectedItem();
    }

    public void initTable(RoleTableView roleTableView, RoleSetViewModel roleSetViewModel) {
        this.roleTableView = roleTableView;
        this.roleSetViewModel = roleSetViewModel;
//        this.roleTableView.getItems().addListener(new ListChangeListener<RoleListLabel>() {
//            @Override
//            public void onChanged(Change<? extends RoleListLabel> c) {
//                ObservableList<? extends RoleListLabel> list = c.getList();
//                System.out.println("CTV: init role list table: " + ((RoleListLabel)list.get(list.size()-1)).getViewModelElement().getName());
//            }
//        });
//
//        this.roleTableView.addSelectionListener(new PropertyChangeListener() {
//            @Override
//            public void propertyChange(PropertyChangeEvent evt) {
//                RoleListLabel label = (RoleListLabel) evt.getNewValue();
//                long modelID = label.getViewModelElement().getId();
//                System.out.println("CTV: role selected: " + currentRoleViewModel.getId() + " " + modelID );
//            }
//        });
        updatePlaceholder();
    }

    public void addListener(PropertyChangeListener eventListener) {
        this.eventListener = eventListener;
        this.characteristicsTableElements.addListener(new ListChangeListener<CharacteristicsTableElement>() {
            @Override
            public void onChanged(Change<? extends CharacteristicsTableElement> c) {
                ObservableList<? extends CharacteristicsTableElement> list = c.getList();
                if(list.size() < 1)
                    return;
                CharacteristicsTableElement element = list.get(list.size() - 1);
//                System.out.println("CTV: new characteristic element: " + element.getClass().getSimpleName());
            }
        });
    }

    public String getDefaultValue() {
        return defaultValue;
    }
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public void updatePlaceholder() {
//        String name = null;
//
//        if(characteristicsTableElements.size() > 0) {
//            CharacteristicViewModel characteristic = characteristicsTableElements.get(characteristicsTableElements.size() - 1).getViewModel();
//            name = characteristic.getName();
//        }

        if(characteristicsTableElements.size() > 0 && characteristicsTableElements.get(characteristicsTableElements.size() - 1).getViewModel().getName().equals(""))
            return;
        addPlaceholder();
    }

    public IGuiModificationHandler getGuiModificationHandler() {
        return guiModificationHandler;
    }
    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }

    @Override
    public <T> void addColumn(String title, String propertyName, StringConverter<T> converter, boolean editable, EventHandler<TableColumn.CellEditEvent<CharacteristicsTableElement, T>> listener) {
        TableColumn<CharacteristicsTableElement, T> column = new TableColumn<CharacteristicsTableElement, T>(title);


//        column.setCellFactory(col -> {
//
//            TableCell<CharacteristicsTableElement, StringProperty> c = new TableCell<>();
//
//                    final ComboBox<String> comboBox = new ComboBox(characteristicsTableElements);
//
//                    c.itemProperty().addListener((observable, oldValue, newValue) -> {
//                        if (oldValue != null) {
//                            comboBox.valueProperty().unbindBidirectional(oldValue);
//                        }
//                        if (newValue != null) {
//                            comboBox.valueProperty().bindBidirectional(newValue);
//                        }
//                    });
//                    c.graphicProperty().bind(Bindings.when(c.emptyProperty()).then((Node) null).otherwise(comboBox));
//                    return (TableCell<CharacteristicsTableElement, T>) c;
//        });



        column.setCellValueFactory(new PropertyValueFactory<CharacteristicsTableElement, T>(propertyName));
        Callback<TableColumn<CharacteristicsTableElement, T>, TableCell<CharacteristicsTableElement, T>> defaultTextFieldCellFactory
                = TextFieldTableCell.<CharacteristicsTableElement, T>forTableColumn(converter);
        column.setCellFactory(col -> {
            TableCell<CharacteristicsTableElement, T> cell = defaultTextFieldCellFactory.call(col);
            cell.setEditable(editable);
            return cell;
        });
        getColumns().add(column);
        if (listener != null) {
            column.setOnEditCommit(listener);
        }
        resizeTable();
    }
}
