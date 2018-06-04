package de.uni_kassel.vs.cn.planDesigner.view.menu;

import de.uni_kassel.vs.cn.planDesigner.view.I18NRepo;
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
