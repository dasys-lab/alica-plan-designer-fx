package de.uni_kassel.vs.cn.planDesigner.controller;

import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.view.repo.RepositoryViewModel;

/**
 * Central class that synchronizes model and view.
 * It is THE CONTROLLER regarding the Model-View-Controller pattern,
 * implemented in the Plan Designer.
 */
public final class Controller {

    private RepositoryViewModel repoViewModel;
    private ModelManager modelManager;
    private ConfigurationManager configurationManager;

    public Controller () {
        configurationManager = ConfigurationManager.getInstance();
        modelManager = new ModelManager();

        repoViewModel.setPlans(modelManager.getPlansForUI());
    }
}
