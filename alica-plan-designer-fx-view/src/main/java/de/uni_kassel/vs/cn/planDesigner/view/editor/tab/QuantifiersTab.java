package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.QuantifierViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesButtonTable;
import javafx.scene.control.Tab;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.LongStringConverter;

public class QuantifiersTab extends Tab {

    PropertiesButtonTable<QuantifierViewModel> buttonTableVBox;
    I18NRepo i18NRepo;

    public QuantifiersTab() {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.quantifiers"));

        buttonTableVBox = new PropertiesButtonTable<QuantifierViewModel>() {
            @Override
            protected void onAddElement() {
                System.out.println("QuantifiersTab: Sending a createElement event not implemented, yet!");
            }

            @Override
            protected void onRemoveElement() {
                System.out.println("QuantifiersTab: Sending a deleteElement event not implemented, yet!");
            }
        };

        buttonTableVBox.addColumn(i18NRepo.getString("label.column.elementType"), "quantifierType", new DefaultStringConverter(), true);
        buttonTableVBox.addColumn(i18NRepo.getString("label.column.scope"), "scope", new LongStringConverter(), true);
        buttonTableVBox.addColumn(i18NRepo.getString("label.column.sorts"), "sorts", new DefaultStringConverter(), true);
        buttonTableVBox.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);

        setContent(buttonTableVBox);
    }

    public void addItem(QuantifierViewModel viewModel) {
        buttonTableVBox.addItem(viewModel);
    }
}
