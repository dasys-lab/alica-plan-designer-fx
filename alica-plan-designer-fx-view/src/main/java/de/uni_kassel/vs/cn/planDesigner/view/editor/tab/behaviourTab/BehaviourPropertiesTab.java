package de.uni_kassel.vs.cn.planDesigner.view.editor.tab.behaviourTab;

import de.uni_kassel.vs.cn.planDesigner.view.editor.tab.AbstractPropertiesTab;
import de.uni_kassel.vs.cn.planDesigner.view.model.BehaviourViewModel;
import javafx.util.converter.DefaultStringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

public class BehaviourPropertiesTab extends AbstractPropertiesTab<BehaviourViewModel> {

    public BehaviourPropertiesTab () {
        super();
    }

    @Override
    public void setupGUI() {
        setText(i18NRepo.getString("label.caption.properties"));

        table.addColumn(i18NRepo.getString("label.column.name"), "name", new DefaultStringConverter(), true);
        table.addColumn(i18NRepo.getString("label.column.id"), "id", new LongStringConverter(), false);
        table.addColumn(i18NRepo.getString("label.column.comment"), "comment", new DefaultStringConverter(), true);
        table.addColumn(i18NRepo.getString("label.column.relDir"), "relativeDirectory", new DefaultStringConverter(), false);
        table.addColumn(i18NRepo.getString("label.column.frequency"), "frequency", new IntegerStringConverter(), true);
        table.addColumn(i18NRepo.getString("label.column.deferring"), "deferring", new LongStringConverter(), true);

        setContent(table);
    }
}
