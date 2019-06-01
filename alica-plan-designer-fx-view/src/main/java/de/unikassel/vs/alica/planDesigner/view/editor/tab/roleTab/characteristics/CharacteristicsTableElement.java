package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics;

import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import javafx.beans.property.StringProperty;

public class CharacteristicsTableElement {
    private CharacteristicsTableView tableView;
    private CharacteristicViewModel viewModel;

    public CharacteristicsTableElement(CharacteristicsTableView tableView,
                                       CharacteristicViewModel characteristicViewModel, String value, String weight) {
        this.tableView = tableView;
        this.viewModel = characteristicViewModel;
        this.viewModel.registerListener(tableView.getGuiModificationHandler());
    }

    public CharacteristicsTableView getTableView() {
        return tableView;
    }

    public CharacteristicViewModel getViewModel() {
        return viewModel;
    }

    public StringProperty valueProperty() {
        return viewModel.valueProperty();
    }
    public void setValue(String value) {
        this.viewModel.setValue(value);
//        this.value.addListener(this.listener);
    }

    public StringProperty nameProperty() {
        return viewModel.nameProperty();
    }
    public void setName(String name) {
        this.viewModel.setValue(name);
//        this.name.addListener(this.listener);
    }

    public StringProperty weightProperty() {
        return viewModel.weightProperty();
    }
    public void setWeight(String weight) {
        this.viewModel.setweight(weight);
//        this.weight.addListener(this.listener);
    }
}
