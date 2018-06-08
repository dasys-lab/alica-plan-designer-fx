package de.uni_kassel.vs.cn.planDesigner;

import de.uni_kassel.vs.cn.planDesigner.controller.Controller;
import org.apache.log4j.BasicConfigurator;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlanDesigner {
    public static void main(String[] args) throws IOException, URISyntaxException {
        // setup log4j correctly
        BasicConfigurator.configure();

        // initialisation before GUI is going to start
        Controller controller = new Controller();

        // start javafx application
        PlanDesignerApplication application = new PlanDesignerApplication();
        application.launch(PlanDesignerApplication.class, args);
    }
}
