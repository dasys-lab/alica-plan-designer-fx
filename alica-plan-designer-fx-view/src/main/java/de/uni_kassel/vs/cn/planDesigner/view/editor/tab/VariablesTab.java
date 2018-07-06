package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesButtonTable;
import javafx.scene.control.Tab;
import javafx.util.converter.DefaultStringConverter;

public class VariablesTab extends Tab {

    PropertiesButtonTable<VariableViewModel> buttonTableVBox;
    I18NRepo i18NRepo;

    public VariablesTab() {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.variables"));

        buttonTableVBox = new PropertiesButtonTable<VariableViewModel>() {
            @Override
            protected void onAddElement() {
                System.out.println("VariablesTab: Sending a createElement event not implemented, yet!");
            }

            @Override
            protected void onRemoveElement() {
                System.out.println("VariablesTab: Sending a deleteElement event not implemented, yet!");
            }
        };

        buttonTableVBox.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        buttonTableVBox.addColumn(i18NRepo.getString("label.column.elementType"), "variableType", new DefaultStringConverter(), true);
        buttonTableVBox.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);

        setContent(buttonTableVBox);
    }

    public void addItem(VariableViewModel viewModel) {
        buttonTableVBox.addItem(viewModel);
    }

}
