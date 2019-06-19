package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import javafx.scene.control.*;

import java.io.File;

public class RenameFileMenuItem extends MenuItem {
    private TreeCell<File> treeCell;
    public RenameFileMenuItem() {
        super(I18NRepo.getInstance().getString("label.menu.edit.rename"));
        setOnAction(e -> onRename());
    }

    public void onRename() { treeCell.startEdit(); }

    public void setTreeCell(TreeCell<File> treeCell) { this.treeCell = treeCell; }
}
