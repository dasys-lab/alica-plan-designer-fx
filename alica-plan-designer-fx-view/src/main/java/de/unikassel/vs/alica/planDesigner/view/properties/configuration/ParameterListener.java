package de.unikassel.vs.alica.planDesigner.view.properties.configuration;

import de.unikassel.vs.alica.planDesigner.view.model.BehaviourViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.MapChangeListener;
import javafx.scene.control.TableView;

import java.util.Map;

public class ParameterListener<T> implements ChangeListener<T>, MapChangeListener {

    // height of a row in px
    public static final int CELL_SIZE = 28;
    private TableView<Map.Entry<String, String>> parametersTableView;

    ParameterListener(TableView<Map.Entry<String, String>> parametersTableView) {
        this.parametersTableView = parametersTableView;
    }

    @Override
    public void onChanged(Change change) {
        this.updateTable(change.getMap());
    }

    @Override
    public void changed(ObservableValue<? extends T> observable, T oldValue, T newValue) {
        this.updateTable(((BehaviourViewModel) newValue).getParameters());
    }

    private void updateTable(Map<String, String> newParameterMap) {
        this.parametersTableView.getItems().clear();
        this.parametersTableView.getItems().addAll(newParameterMap.entrySet());
        this.resizeTableView(newParameterMap);
    }

    private void resizeTableView(Map<String, String> newParameterMap) {
        int itemsAndHeaderSize = 2;
        if (newParameterMap != null) {
            itemsAndHeaderSize = newParameterMap.size() + 1;
        }

        parametersTableView.setPrefHeight(itemsAndHeaderSize * CELL_SIZE);
        parametersTableView.setMinHeight(itemsAndHeaderSize * CELL_SIZE);
        parametersTableView.setMaxHeight(itemsAndHeaderSize * CELL_SIZE);
        parametersTableView.refresh();
    }
}
