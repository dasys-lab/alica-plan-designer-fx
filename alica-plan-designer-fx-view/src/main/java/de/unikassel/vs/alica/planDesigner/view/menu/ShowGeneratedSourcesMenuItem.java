package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IShowGeneratedSourcesEventHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import javafx.scene.control.MenuItem;

public class ShowGeneratedSourcesMenuItem extends MenuItem {
    private long modelElementId;
    private IShowGeneratedSourcesEventHandler handler;

    public ShowGeneratedSourcesMenuItem(long modelElementId, IShowGeneratedSourcesEventHandler handler) {
        super(I18NRepo.getInstance().getString("label.menu.sources"));
        this.modelElementId = modelElementId;
        this.handler = handler;
        setOnAction(e -> this.handler.handle(this.modelElementId));
    }
}
