package de.uni_kassel.vs.cn.planDesigner.view.editor.tab;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
import de.uni_kassel.vs.cn.planDesigner.view.model.ViewModelElement;
import de.uni_kassel.vs.cn.planDesigner.view.properties.PropertiesTable;
import javafx.scene.control.Tab;

public abstract class AbstractPropertiesTab<S extends ViewModelElement> extends Tab {

    protected PropertiesTable<S> table;
    protected I18NRepo i18NRepo;

    public AbstractPropertiesTab() {
        i18NRepo = I18NRepo.getInstance();
        table = new PropertiesTable<>();
        table.setEditable(true);
        setupGUI();
    }

    public void addItem(S viewModel) {
        table.addItem(viewModel);
        table.setMaxHeight(24*(table.getItems().size()+1)+2);
    }

    /**
     * Gets called by the constructor and is a reminder for all inherited classes to take
     * care of the gui setup themselves.
     */
    public abstract void setupGUI();
}
