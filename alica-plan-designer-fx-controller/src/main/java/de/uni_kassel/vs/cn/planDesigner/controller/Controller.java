package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.command.CommandStack;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller {

    // Common Objects
    private ConfigurationManager configurationManager;

    // Model Objects
    private ModelManager modelManager;
    private CommandStack commandStack;

    // View Objects
    private RepositoryViewModel repoViewModel;

    public Controller () {
        configurationManager = ConfigurationManager.getInstance();
        modelManager = new ModelManager();

        repoViewModel.setPlans(modelManager.getPlansForUI());
    }
}
