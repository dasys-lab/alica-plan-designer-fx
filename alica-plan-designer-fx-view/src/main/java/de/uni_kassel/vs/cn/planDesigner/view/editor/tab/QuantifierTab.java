package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Tab;

public class QuantifierTab extends Tab {
    I18NRepo i18NRepo;
    PropertiesTable<QuantifierViewModel> table;

    public QuantifierTab() {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.quantifier"));

        table = new PropertiesTable<>();
        // TODO: fill table with properties
//        table.addColumn();
    }

    public void addItem(QuantifierViewModel viewModel) {
        this.table.addItem(viewModel);
        table.setPrefHeight(24 * (table.getItems().size() + 1) + 2);
    }
}
