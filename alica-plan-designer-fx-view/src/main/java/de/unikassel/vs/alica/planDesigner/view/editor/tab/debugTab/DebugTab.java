package de.unikassel.vs.alica.planDesigner.view.editor.tab.debugTab;

import de.unikassel.vs.alica.planDesigner.controller.GlobalsConfWindowController;
import de.unikassel.vs.alica.planDesigner.events.GuiModificationEvent;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IAlicaHandler;
import de.unikassel.vs.alica.planDesigner.handlerinterfaces.IGuiModificationHandler;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTab;
import de.unikassel.vs.alica.planDesigner.view.editor.tab.EditorTabPane;
import de.unikassel.vs.alica.planDesigner.view.img.AlicaIcon;
import de.unikassel.vs.alica.planDesigner.view.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class DebugTab extends EditorTab {
    public static final int SPACING = 10;
    public static final String DEBUGVIEWNAME = "Debug";

    private IAlicaHandler alicaHandler;

    private AlicaConfigurationViewModel alicaConfigurationViewModel;
    private GlobalsConfViewModel globalsConfViewModel;

    // JavaFX
    private VBox allAgentsVBox;
    private TextArea debugTextArea;
    private Button runButton;
    private GridPane gridPane;

    // Process indicator
    private boolean isRunning;

    private Map<Agent, Boolean> availableAgents;

    public DebugTab(SerializableViewModel serializableViewModel, EditorTabPane tabPane,
                    IAlicaHandler alicaHandler) {
        super(serializableViewModel, tabPane.getGuiModificationHandler());
        this.alicaHandler = alicaHandler;

        DebugViewModel viewModel = (DebugViewModel) serializableViewModel;
        globalsConfViewModel = viewModel.getGlobalsConfViewModel();
        alicaConfigurationViewModel = viewModel.getAlicaConfigurationViewModel();

        availableAgents = new HashMap<>();

        for (GlobalsConfWindowController ctrl : globalsConfViewModel.getListOfTeamsViewModel()) {
            availableAgents.put(new Agent(ctrl.getId(), ctrl.getName(), ctrl.getDefaultRole(), ctrl.getSpeed()), false);
        }

        setOnClosed(this::onClosed);

        draw();
    }


    private void onClosed(Event event) {
        if (isRunning) {
            try {
                alicaHandler.stopAlica();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void draw() {
        debugTextArea = new TextArea();
        debugTextArea.setEditable(false);

        allAgentsVBox = new VBox(SPACING);

        // setup the GridPane where are agents are listed
        this.gridPane = new GridPane();
        this.gridPane.setVgap(SPACING);
        this.gridPane.setHgap(SPACING);
        Label labelRobot = new Label("Robot Name");
        Label labelMasterPlan = new Label("MasterPlan");
        Label labelRoleset = new Label("Roleset");

        // Iterate over parsed Agents and create a GridView, where the user can check or uncheck the
        // parsed agents
        List<Agent> sortedAgents = new ArrayList<>(availableAgents.keySet());
        sortedAgents.sort(Comparator.comparingInt(c -> c.id));

        // Get the RoleSets
        ObservableList<String> roleNames = FXCollections.observableArrayList();
        for (ViewModelElement role : guiModificationHandler.getRepoViewModel().getRoles()) {
            roleNames.add(role.getName());
        }

        int i = 1;
        for (Agent agent : sortedAgents) {
            CheckBox checkBox = new CheckBox(agent.name + "\t(id=" + agent.id + "; defaultRole=" + agent.defaultRole + "; speed=" + agent.speed + ")");
            checkBox.setSelected(availableAgents.get(agent));
            checkBox.selectedProperty().addListener((observableValue, oldValue, newValue) -> availableAgents.put(agent, newValue));

            // Get all the Masterplans
            ObservableList<String> plans = FXCollections.observableArrayList(guiModificationHandler.getRepoViewModel().getPlans().stream()
                    .filter(p -> p instanceof PlanViewModel)
                    .filter(p -> ((PlanViewModel) p).isMasterPlan())
                    .map(ViewModelElement::getName)
                    .collect(Collectors.toList()));
            ComboBox<String> comboBoxMasterPlans = new ComboBox<>(plans);
            comboBoxMasterPlans.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> agent.masterPlan = newValue);
            comboBoxMasterPlans.getSelectionModel().clearAndSelect(0);


            ComboBox<String> comboBoxRoleSets = new ComboBox<>(roleNames);
            comboBoxRoleSets.getSelectionModel().selectedItemProperty().addListener((o, oldValue, newValue) -> agent.roleset = newValue);
            comboBoxRoleSets.getSelectionModel().clearAndSelect(0);

            GridPane.setConstraints(checkBox, 0, i);
            GridPane.setConstraints(comboBoxMasterPlans, 1, i);
            GridPane.setConstraints(comboBoxRoleSets, 2, i);

            gridPane.getChildren().addAll(checkBox, comboBoxMasterPlans, comboBoxRoleSets);

            i -=- 1;
        }
        allAgentsVBox.getChildren().addAll(gridPane);

        GridPane.setConstraints(labelRobot, 0, 0, 1, 1, HPos.CENTER, VPos.BASELINE);
        GridPane.setConstraints(labelMasterPlan, 1, 0, 1, 1, HPos.CENTER, VPos.BASELINE);
        GridPane.setConstraints(labelRoleset, 2, 0, 1, 1, HPos.CENTER, VPos.BASELINE);

        runButton = new Button("Run");
        runButton.setGraphic(new ImageView(new AlicaIcon("run", AlicaIcon.Size.SMALL)));
        runButton.setMaxWidth(Double.MAX_VALUE);
        runButton.setOnAction(this::onRunClicked);

        VBox vbox = new VBox(SPACING);
        vbox.getChildren().addAll(allAgentsVBox, runButton);

        HBox hbox = new HBox(SPACING);
        hbox.getChildren().addAll(debugTextArea, vbox);
        HBox.setHgrow(vbox, Priority.ALWAYS);

        this.elementInformationPane.setContent(hbox);

    }

    private void onRunClicked(ActionEvent event) {
        if (!isRunning) {
            // No agent has started yet
            for (Agent agent : availableAgents.keySet()) {
                // check if an Agent was selected (its value in the map is true then)
                if (availableAgents.get(agent)) {
                    isRunning |= alicaHandler.runAlica(agent.name, agent.masterPlan, agent.roleset);
                }
            }

            if (isRunning) {
                // When at least one of the agents started running, change the TabName and disable everything other
                // than the stop button
                serializableViewModel.setName(DEBUGVIEWNAME + " [running...]");

                gridPane.setDisable(true);

                runButton.setText("Stop");
                runButton.setGraphic(new ImageView(new AlicaIcon("stop", AlicaIcon.Size.SMALL)));

                // We need to react to when runners crashed or were not at all started ??
//                if (checkForAPDR == null) {
//                    checkForAPDR = new Thread(() -> {
//
//                    });
//                }
            }
        } else {
            // There are agents running, stop them
            try {
                alicaHandler.stopAlica();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            isRunning = false;

            gridPane.setDisable(false);

            serializableViewModel.setName(DEBUGVIEWNAME);
            runButton.setText("Run");
            runButton.setGraphic(new ImageView(new AlicaIcon("run", AlicaIcon.Size.SMALL)));
        }
    }

    @Override
    public void save() {

    }

    @Override
    public GuiModificationEvent handleDelete() {
        return null;
    }

    private static class Agent {
        int id;
        String name, defaultRole, speed;
        String masterPlan, roleset;

        public Agent() {
        }

        public Agent(int id, String name, String defaultRole, String speed) {
            this.id = id;
            this.name = name;
            this.defaultRole = defaultRole;
            this.speed = speed;
        }

        @Override
        public String toString() {
            return "Agent{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", defaultRole='" + defaultRole + '\'' +
                    ", speed='" + speed + '\'' +
                    ", masterPlan='" + masterPlan + '\'' +
                    ", roleset='" + roleset + '\'' +
                    '}';
        }
    }
}
