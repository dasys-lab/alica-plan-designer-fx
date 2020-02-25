package de.unikassel.vs.alica.planDesigner.alicaConfiguration;

import de.unikassel.vs.alica.planDesigner.configuration.ConfigurationManager;
import de.unikassel.vs.alica.planDesigner.controller.Controller;

public class AlicaConfigurationManager {
    private Controller controller;
    private static volatile AlicaConfigurationManager instance;

    public static AlicaConfigurationManager getInstance() {
        if (instance == null) {
            synchronized (AlicaConfigurationManager.class) {
                if (instance == null) {
                    instance = new AlicaConfigurationManager();
                }
            }
        }
        return instance;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }
}
