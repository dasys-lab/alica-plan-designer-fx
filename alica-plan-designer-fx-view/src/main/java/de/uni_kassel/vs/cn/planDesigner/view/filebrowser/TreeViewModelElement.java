package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;

public class TreeViewModelElement extends ViewModelElement {

    protected String relativeDirectory;

    public TreeViewModelElement(long id, String name, String type, String relativeDirectory) {
        super(id, name, type);
        this.relativeDirectory = relativeDirectory;
    }

    public String getRelativeDirectory() {
        return relativeDirectory;
    }
}
