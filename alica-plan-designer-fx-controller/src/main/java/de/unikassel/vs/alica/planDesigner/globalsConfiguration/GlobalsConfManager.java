package de.unikassel.vs.alica.planDesigner.globalsConfiguration;

import de.unikassel.vs.alica.planDesigner.controller.Controller;

public class GlobalsConfManager {
    private Controller controller;
    private static volatile GlobalsConfManager instance;

    public static GlobalsConfManager getInstance() {
        if (instance == null) {
            synchronized (GlobalsConfManager.class) {
                if (instance == null) {
                    instance = new GlobalsConfManager();
                }
            }
        }
        return instance;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
