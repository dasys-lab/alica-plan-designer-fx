package de.unikassel.vs.alica.generator;

import de.unikassel.vs.alica.planDesigner.modelmanagement.ModelManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * This Codegenerator console application (re)generates all plans/behaviours and terminates afterwards.
 */
public class StandaloneCodegenerator {
    private static final Logger LOG = LogManager.getLogger(StandaloneCodegenerator.class);

    private static String clangFormatPath;
    private static String sourceGenPath;
    private static String plansPath;
    private static String rolesPath;
    private static String tasksPath;


    private static void printUsage(){
        System.out.println("Usage: java -jar StandaloneCodegenerator <clangFormat> <sourceGenPath> <plansPath> <rolesPath> <tasksPaths>");
        System.exit(-1);
    }

    private static void readCmdLineArgs(String[] args) {
        if (args.length < 5)
        {
            printUsage();
        }

        clangFormatPath = args[0];
        sourceGenPath = args[1];
        plansPath = args[2];
        rolesPath = args[3];
        tasksPath = args[4];
    }

    public static void main(String[] args) throws IOException {
        readCmdLineArgs(args);

        ModelManager modelManager = new ModelManager();
        modelManager.setPlansPath(plansPath);
        modelManager.setTasksPath(tasksPath);
        modelManager.setRolesPath(rolesPath);
        modelManager.loadModelFromDisk();

        GeneratedSourcesManager generatedSourcesManager = new GeneratedSourcesManager();

        Codegenerator codegenerator = new Codegenerator(modelManager.getPlans(),
                modelManager.getBehaviours(),
                modelManager.getConditions(),
                clangFormatPath,
                sourceGenPath,
                generatedSourcesManager);
        codegenerator.generate();
    }
}
