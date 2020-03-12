package de.unikassel.vs.alica.planDesigner.view.menu;

import com.sun.javafx.PlatformUtil;
import de.unikassel.vs.alica.planDesigner.controller.MainWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.I18NRepo;
import javafx.scene.control.MenuItem;

import java.awt.*;
import java.io.File;
import java.io.IOException;

public class ShowGeneratedSourcesMenuItem extends MenuItem {
    private long modelElementId;
    private IGuiModificationHandler handler;

    public ShowGeneratedSourcesMenuItem(long modelElementId) {
        super(I18NRepo.getInstance().getString("label.menu.sources"));
        this.modelElementId = modelElementId;
        MainWindowController mainWindowController = MainWindowController.getInstance();
        this.handler = mainWindowController.getGuiModificationHandler();
        setOnAction(e -> show());
    }

    private void show() {
        File generatedFile = handler.showGeneratedSourceHandler(modelElementId);
        System.out.println(generatedFile);
        //Open text editors (don't tested for Windows and Mac
        try {
            if(PlatformUtil.isLinux()) {
                Runtime obj = Runtime.getRuntime();
                obj.exec("gedit " + generatedFile);
            } else if(PlatformUtil.isWindows()) {
                Desktop.getDesktop().open(generatedFile);
                //Alternative
                //java.awt.Desktop.getDesktop().edit(generatedFile);
            } else if(PlatformUtil.isMac()) {
                Process p = new ProcessBuilder("open", generatedFile.toString()).start();
            } else {
                System.out.println("Your operating system is not implement yet.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
