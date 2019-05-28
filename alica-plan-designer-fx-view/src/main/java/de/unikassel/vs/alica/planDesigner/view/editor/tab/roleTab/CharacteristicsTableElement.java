package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CharacteristicsTableElement {
    private CharacteristicsTableView tableView;
    private CharacteristicViewModel characteristic;
    private SimpleStringProperty name;
    private SimpleStringProperty priority;

    private InvalidationListener listener;

    public CharacteristicsTableElement(CharacteristicsTableView tableView,
                                       CharacteristicViewModel characteristic, String priority) {
        this.tableView = tableView;
        this.characteristic = characteristic;
        this.name =  new SimpleStringProperty(characteristic.getName());
        this.priority = new SimpleStringProperty(priority);;
    }

    public void addListener(PropertyChangeListener listener) {
        this.listener = observable -> listener.propertyChange(
                new PropertyChangeEvent(tableView.getCurrentRole(), "characteristic", characteristic.getId(),
                ((StringProperty)observable).getValue()));
    }

    public void removeListener() {
        this.priority.removeListener(this.listener);
    }

    public CharacteristicViewModel getCharacteristic() {
        return characteristic;
    }

    public SimpleStringProperty priorityProperty() {
        return priority;
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setPriority(String priority) {
        this.priority.set(priority);
        this.priority.addListener(this.listener);
    }
}
