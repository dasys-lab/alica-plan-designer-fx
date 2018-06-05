package de.uni_kassel.vs.cn.planDesigner.view.filebrowser;

import de.uni_kassel.vs.cn.planDesigner.view.repo.ViewModelElement;

public class PLDViewModelElement extends ViewModelElement {

    protected String destinationPath;

    public PLDViewModelElement(long id, String name, String type, String path) {
        super(id, name, type);
        this.destinationPath = path;
    }

    public String getDestinationPath() {
        return destinationPath;
    }
}
