package de.uni_kassel.vs.cn.planDesigner.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.uni_kassel.vs.cn.planDesigner.configuration.Configuration;
import de.uni_kassel.vs.cn.planDesigner.configuration.ConfigurationManager;
import de.uni_kassel.vs.cn.planDesigner.alica.Plan;
import de.uni_kassel.vs.cn.planDesigner.alica.PlanElement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.util.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ModelManager {
    private static final Logger LOG = LogManager.getLogger(ModelManager.class);

    Configuration activeConf;
    HashMap<Long, PlanElement> planElementMap;
    HashMap<Long, Plan> planMap;

    public ModelManager() {
        ConfigurationManager confManager = ConfigurationManager.getInstance();
        activeConf = confManager.getActiveConfiguration();
        planElementMap = new HashMap<>();
        planMap = new HashMap<>();

        loadPlansFromDisk();
    }

    /**
     * This method could be superfluous, as "loadPlanFromDisk" is maybe called by the file watcher.
     * Anyway, temporarily this is a nice method for testing and is therefore called in the constr.
      */
    private void loadPlansFromDisk() {
        if (activeConf == null) {
            return;
        }

        File plansDirectory = new File(activeConf.getPlansPath());
        if (!plansDirectory.exists()) {
            return;
        }

        File[] allPlanFiles = plansDirectory.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".pml");
            }
        });

        if (allPlanFiles.length == 0) {
            return;
        }

        for (File planFile : allPlanFiles) {
            loadPlanFromDisk(planFile);

        }
    }

    private void loadPlanFromDisk(File planFile) {
        Plan plan = new Plan();

        ObjectMapper mapper = new ObjectMapper();
        try {
            // TODO: this is untested
            plan = mapper.readValue(planFile, Plan.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (planElementMap.put(plan.getId(), plan) != null) {
            LOG.error("PlanElement ID duplication found!");
        } else {
            planMap.put(plan.getId(), plan);
        }
    }

    public ArrayList<Plan> getPlans()
    {
        return new ArrayList<>(planMap.values());
    }

    public ObservableList<Pair<Long, String>> getPlansForUI ()
    {
        ObservableList<Pair<Long, String>> plansUIList = FXCollections.observableArrayList();
        for (Map.Entry<Long, Plan> entry : planMap.entrySet()) {
            plansUIList.add(new Pair<Long, String>(entry.getKey(), entry.getValue().getName()));
        }
        return plansUIList;
    }


}
