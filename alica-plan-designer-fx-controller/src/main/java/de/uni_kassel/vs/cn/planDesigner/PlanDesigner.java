package de.uni_kassel.vs.cn.planDesigner;

import de.uni_kassel.vs.cn.planDesigner.controller.Controller;

import java.io.IOException;
import java.net.URISyntaxException;

public class PlanDesigner {
    public static void main(String[] args) throws IOException, URISyntaxException {
        Controller controller = new Controller();
        PlanDesignerApplication application = new PlanDesignerApplication();
        application.launch(args);
    }
}
