package de.unikassel.vs.alica.planDesigner;

import de.unikassel.vs.alica.planDesigner.controller.Controller;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.DefaultConfiguration;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlanDesigner {
    public static void main(String[] args) throws IOException, URISyntaxException {
        init();

        // start javafx application
        PlanDesignerApplication application = new PlanDesignerApplication();
        application.launch(PlanDesignerApplication.class, args);
    }

    public static void init() {
        // setup log4j correctly
        Configurator.initialize(new DefaultConfiguration());
        Configurator.setRootLevel(Level.INFO);

        // initialisation before GUI is going to start
        Controller controller = new Controller();
    }
}
