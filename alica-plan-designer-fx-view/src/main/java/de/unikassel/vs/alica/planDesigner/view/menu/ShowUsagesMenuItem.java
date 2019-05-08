package de.unikassel.vs.alica.planDesigner.view.menu;

import de.unikassel.vs.alica.planDesigner.controller.UsagesWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import de.unikassel.vs.alica.planDesigner.view.model.ViewModelElement;
import javafx.scene.control.MenuItem;

public class ShowUsagesMenuItem extends MenuItem {

    private I18NRepo i18NRepo;

    public ShowUsagesMenuItem(ViewModelElement viewModelElement, IGuiModificationHandler guiModificationHandler) {
        i18NRepo = I18NRepo.getInstance();
        setText(i18NRepo.getString("label.menu.usage"));
        setOnAction( e ->
                UsagesWindowController.createUsagesWindow(viewModelElement
                        , i18NRepo.getString("label.usage.info"), guiModificationHandler));
    }


}
