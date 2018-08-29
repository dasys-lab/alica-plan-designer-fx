package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.events.GuiEventType;
import de.uni_kassel.vs.cn.planDesigner.events.GuiModificationEvent;
import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.Types;
import de.uni_kassel.vs.cn.planDesigner.view.model.VariableViewModel;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesButtonTable;
import javafx.scene.control.Tab;
import javafx.util.converter.DefaultStringConverter;

public class VariablesTab extends Tab {

    PropertiesButtonTable<VariableViewModel> buttonTableVBox;
    I18NRepo i18NRepo;
    ViewModelElement parentElement;

    public VariablesTab(ViewModelElement parentElement) {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.caption.variables"));

        this.parentElement = parentElement;

        buttonTableVBox = new PropertiesButtonTable<VariableViewModel>() {
            @Override
            protected void onAddElement() {
                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.CREATE_ELEMENT, Types.VARIABLE, "NEW_VARIABLE");
                event.setParentId(parentElement.getId());
                // TODO event senden
            }

            @Override
            protected void onRemoveElement() {
                VariableViewModel selectedVariable = buttonTableVBox.getSelectedItem();
                if (selectedVariable == null) {
                    return;
                }

                GuiModificationEvent event = new GuiModificationEvent(GuiEventType.DELETE_ELEMENT, Types.VARIABLE, selectedVariable.getName());
                event.setParentId(parentElement.getId());
                event.setElementId(selectedVariable.getId());
                // TODO event senden
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
