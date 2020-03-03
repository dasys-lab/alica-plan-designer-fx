package de.unikassel.vs.alica.planDesigner.globalsConfiguration;

import de.unikassel.vs.alica.generator.CodeGeneratorAlicaConf;
import de.unikassel.vs.alica.planDesigner.controller.GlobalsConfWindowController;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGlobalsConfEventHandler;
import de.unikassel.vs.alica.planDesigner.view.model.GlobalsConfViewModel;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.scene.control.ListView;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class GlobalsConfEventHandler implements IGlobalsConfEventHandler<ListView.EditEvent<String>> {

    private GlobalsConfWindowController globalsConfWindowController;
    private GlobalsConfManager globalsConfManager;
    private GlobalsConfiguration globalsConfiguration = new GlobalsConfiguration();

    public GlobalsConfEventHandler(GlobalsConfWindowController globalsConfWindowController, GlobalsConfManager globalsConfManager) {
        this.globalsConfManager = globalsConfManager;
        this.globalsConfWindowController = globalsConfWindowController;
    }

    @Override
    public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {

    }

    @Override
    public void handle(Event event) {

    }

    @Override
    public GlobalsConfViewModel setDefaultConfiguration(GlobalsConfViewModel globalsConfViewModel) {
        globalsConfiguration.setDefaultConfiguration();

        globalsConfViewModel.setDiameterRobotViewModel(globalsConfiguration.getDiameterRobot());
        globalsConfViewModel.setDefaultRoleViewModel(globalsConfiguration.getDefaultRole());
        globalsConfViewModel.setSpeedViewModel(globalsConfiguration.getSpeed());
        globalsConfViewModel.setAssistentViewModel(globalsConfiguration.getAssistent());
        globalsConfViewModel.setListOfTeamsViewModel(globalsConfiguration.getListOfTeams());

        return globalsConfViewModel;
    }

    @Override
    public String loadAlicaConf(String confPath) {
        CodeGeneratorAlicaConf conf = new CodeGeneratorAlicaConf();
        String yamlAlicaConf = null;
        try {
            yamlAlicaConf = conf.generateAlicaConf(confPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return yamlAlicaConf;
    }

    @Override
    public void updateModel(GlobalsConfViewModel globalsConfViewModel) {
        globalsConfiguration.setDiameterRobot(globalsConfViewModel.getDiameterRobotViewModel());
        globalsConfiguration.setDefaultRole(globalsConfViewModel.getDefaultRoleViewModel());
        globalsConfiguration.setSpeed(globalsConfViewModel.getSpeedViewModel());
        globalsConfiguration.setAssistent(globalsConfViewModel.getAssistentViewModel());
        globalsConfiguration.setListOfTeams(globalsConfViewModel.getListOfTeamsViewModel());
    }

    @Override
    public boolean save(GlobalsConfViewModel globalsConfViewModel) {
        updateModel(globalsConfViewModel);
        File globalsConfFile = new File(globalsConfViewModel.getActiveGlobalsViewModel());
        if(globalsConfFile.exists()) {
            int input = JOptionPane.showConfirmDialog(null,
                    "Do you want to overwrite it?", "File already exists!",JOptionPane.YES_NO_OPTION);
            if(input == 1) {
                return false;
            }
        }
        try {
            Files.deleteIfExists(globalsConfFile.toPath());
            globalsConfFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> output = new ArrayList<>();

        output.add("[Globals]");
        output.add("\r");
        output.add("\t[Dimensions]");
        output.add("\t\tDiameterRobot = " + globalsConfViewModel.getDiameterRobotViewModel());
        output.add("\t[!Dimensions]");
        output.add("\r");

        output.add("\t[Team]");
        for (GlobalsConfWindowController g: globalsConfViewModel.getListOfTeamsViewModel()) {
            output.add("\r");
            output.add("\t\t[" + g.getName() + "]");
            output.add("\t\tID = " + g.getId());
            output.add("\t\tDefaultRole = " + g.getDefaultRole());
            output.add("\t\tSpeed = " + g.getSpeed());
            output.add("\t\t[!" + g.getName() + "]");
        }
        output.add("\t[!Team]");
        output.add("\r");

        output.add("\t[TeamDefault]");
        output.add("\t\tDefaultRole = " + globalsConfViewModel.getDefaultRoleViewModel());
        output.add("\t\tSpeed = " + globalsConfViewModel.getSpeedViewModel());
        output.add("\t[!TeamDefault]");
        output.add("\r");

        output.add("\t[RolePriority]");
        output.add("\t\tAssistent = " + globalsConfViewModel.getAssistentViewModel());
        output.add("\t[!RolePriority]");

        output.add("[!Globals]");

        FileWriter writer;
        try {
            writer = new FileWriter(globalsConfViewModel.getActiveGlobalsViewModel());
            for(String str: output) {
                writer.write(str + System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }
}
