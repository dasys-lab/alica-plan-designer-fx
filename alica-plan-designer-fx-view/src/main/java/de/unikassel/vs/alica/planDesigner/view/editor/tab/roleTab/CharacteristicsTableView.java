package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.RoleViewModel;
import de.unikassel.vs.alica.planDesigner.view.model.TaskViewModel;
import de.unikassel.vs.alica.planDesigner.view.properties.PropertiesTable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeListener;

public class CharacteristicsTableView extends PropertiesTable<CharacteristicsTableElement> {

    private RoleViewModel currentRole;
    private float defaultPriority;
    private PropertyChangeListener eventListener;

    public CharacteristicsTableView(float defaultPriority) {
        super();
        this.defaultPriority = defaultPriority;
    }

    public void updateSelectedRole(RoleViewModel item) {
        this.setCurrentRole(item);
        this.updateCells();
    }

    public void addCharacteristics(ObservableList<CharacteristicViewModel> characteristicViewModels) {
        ObservableList<CharacteristicsTableElement> characteristics = FXCollections.observableArrayList();

        characteristicViewModels.forEach(characteristicViewModel -> {
            CharacteristicsTableElement element = new CharacteristicsTableElement(this,
                                                    characteristicViewModel, String.valueOf(defaultPriority));
            element.addListener(this.eventListener);
            characteristics.add(element);
        });
        this.setItems(characteristics);
    }

    private void updateCells() {

        for (Object item : this.getItems()) {
            Float priority = currentRole.getTaskPriority(((CharacteristicsTableElement) item).getCharacteristic().getId());
            priority = priority == null ? defaultPriority : priority;
            ((CharacteristicsTableElement) item).removeListener();
            ((CharacteristicsTableElement) item).setPriority(String.valueOf(priority));
        }
    }

    public void addListener(PropertyChangeListener eventListener) {
        this.eventListener = eventListener;
    }

    public float getDefaultPriority() {
        return defaultPriority;
    }
    public void setDefaultPriority(float defaultPriority) {
        this.defaultPriority = defaultPriority;
    }
    public RoleViewModel getCurrentRole() {
        return currentRole;
    }
    public void setCurrentRole(RoleViewModel currentRole) {
        this.currentRole = currentRole;
    }

}
