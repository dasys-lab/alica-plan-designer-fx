package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.Types;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.RoleListLabel;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.RoleListView;
import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleSetViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CharacteristicsTableView extends PropertiesTable<CharacteristicsTableElement> {

    private ObservableList<CharacteristicsTableElement> characteristicsTableElements;

    private RoleListView        roleListView;
    private RoleSetViewModel    roleSetViewModel;
    private RoleViewModel       currentRoleViewModel;

    private IGuiModificationHandler guiModificationHandler;
    private PropertyChangeListener  eventListener;

    private String defaultValue;


    public CharacteristicsTableView(RoleSetViewModel roleSetViewModel, RoleListView roleListView) {
        super();
        this.roleSetViewModel = roleSetViewModel;
        this.roleListView = roleListView;
        this.defaultValue = "";
        characteristicsTableElements = FXCollections.observableArrayList();

        this.setItems(characteristicsTableElements);
        this.roleSetViewModel.getRoleViewModels().addListener(new ListChangeListener<RoleViewModel>() {
            @Override
            public void onChanged(Change<? extends RoleViewModel> c) {
                System.out.println("CTV: role set changed: " + ((RoleViewModel)c.getList().get(c.getList().size()-1)).getName());
            }
        });
        this.roleListView.addSelectionListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                System.out.println("CTV: selected role changed: " + ((RoleListLabel)evt.getNewValue()).getViewModelElement().getName());
            }
        });
    }

    public void updateSelectedRole(RoleViewModel roleViewModel) {
        this.currentRoleViewModel = roleViewModel;
        this.currentRoleViewModel.addChangeCharacteristicsListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                if (!c.next())
                    return;
                List addedSubList = c.getAddedSubList();
                System.out.println("CTV: change characteristic " + addedSubList.get(0) + " " + addedSubList.size());
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
        CharacteristicViewModel characteristicViewModel = new CharacteristicViewModel(0,"", Types.ROLE_CHARCTERISTIC, roleListView );
            CharacteristicsTablePlaceholder element = new CharacteristicsTablePlaceholder(this,
                    characteristicViewModel, "", "");
            characteristicsTableElements.add(element);
    }

    private void updateCells() {
        RoleViewModel currentRole = getCurrentRole();
        ObservableList<CharacteristicViewModel> roleCharacteristics = currentRole.getCharacteristicViewModels();
        this.characteristicsTableElements.clear();

        for (CharacteristicViewModel characteristicViewModel : roleCharacteristics) {
            System.out.println("CTV: update Cells " + characteristicViewModel.getName());
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
        return (RoleViewModel) roleListView.getSelectedItem();
    }

    public void initTable(RoleListView roleListView, RoleSetViewModel roleSetViewModel) {
        this.roleListView = roleListView;
        this.roleSetViewModel = roleSetViewModel;
        this.roleListView.getItems().addListener(new ListChangeListener<RoleListLabel>() {
            @Override
            public void onChanged(Change<? extends RoleListLabel> c) {
                ObservableList<? extends RoleListLabel> list = c.getList();
                System.out.println("CTV: init role list table: " + ((RoleListLabel)list.get(list.size()-1)).getViewModelElement().getName());
            }
        });

        this.roleListView.addSelectionListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                RoleListLabel label = (RoleListLabel) evt.getNewValue();
                long modelID = label.getViewModelElement().getId();
                System.out.println("CTV: role selected: " + currentRoleViewModel.getId() + " " + modelID );
            }
        });
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
                System.out.println("CTV: new characteristic element: " + element.getClass().getSimpleName());
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
        String name = null;

        if(characteristicsTableElements.size() > 0) {
            CharacteristicViewModel characteristic = characteristicsTableElements.get(characteristicsTableElements.size() - 1).getViewModel();
            name = characteristic.getName();
        }

        if(characteristicsTableElements.size() > 0 && characteristicsTableElements.get(characteristicsTableElements.size() - 1).getViewModel().getName().equals(""))
            return;
        addPlaceholder();
    }

//    public void setCurrentRole(RoleViewModel currentRole) {
//        this.currentRole = currentRole;

//    }

    public IGuiModificationHandler getGuiModificationHandler() {
        return guiModificationHandler;
    }
    public void setGuiModificationHandler(IGuiModificationHandler guiModificationHandler) {
        this.guiModificationHandler = guiModificationHandler;
    }
}
