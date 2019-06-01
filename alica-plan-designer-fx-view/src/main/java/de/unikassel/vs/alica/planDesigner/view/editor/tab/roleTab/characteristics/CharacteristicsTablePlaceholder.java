package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab.characteristics;

import de.unikassel.vs.alica.planDesigner.view.model.CharacteristicViewModel;
import javafx.beans.property.StringProperty;

public class CharacteristicsTablePlaceholder extends CharacteristicsTableElement {

    public CharacteristicsTablePlaceholder(CharacteristicsTableView tableView,
                                           CharacteristicViewModel characteristic, String value, String weight) {
        super(tableView, characteristic, value, weight);
    }
}
