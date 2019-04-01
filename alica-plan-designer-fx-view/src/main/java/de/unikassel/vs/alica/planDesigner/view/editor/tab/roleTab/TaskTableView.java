package de.unikassel.vs.alica.planDesigner.view.editor.tab.roleTab;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.repo.RepositoryLabel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.Comparator;

public class TaskTableView extends TableView {

    public TaskTableView(TableColumn... columns) {
        this.getColumns().addAll(columns);
    }
}
